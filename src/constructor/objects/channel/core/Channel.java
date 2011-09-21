package constructor.objects.channel.core;

import app.controller.NullController;
import app.iui.flow.custom.SingleProcessInfo;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.core.InterpreterEx;
import constructor.objects.interpreter.core.InterpreterException;
import constructor.objects.interpreter.core.data.InterpreterData;
import dated.DatedItem;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import http.BatchLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;
import util.fragment.ListFragmentIterator;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ����� ��������� �����������. ���������� - ��������� ����������� -
 * �������� ������ ������ ������ � ��������� ���
 *
 * @author Igor Usenko
 *         Date: 07.03.2009
 */
public class Channel {

    private final ChannelAdapter adapter;

    private final Log log;

    public Channel(final ChannelAdapter _adapter) {
        Assert.notNull(_adapter, "Channel adapter is null");
        this.adapter = _adapter;

        this.log = LogFactory.getLog(getClass());
    }

    public void process() throws ChannelException {

        try {
            int counter = 0;

            this.adapter.onStart();

            long forcedAge = this.adapter.getForcedAge();
            TimeService timeService = this.adapter.getSystemTimeService();

            ModificationList modifications = this.adapter.getModificationsList();
            ChannelDataList datas = this.adapter.getChannelDataList();

            List<Modification> candidates = newArrayList();

            ChannelDataList result = new ChannelDataList();

            for (int index = 0; index < modifications.size(); ++index) {

                if (this.adapter.isCancelled()) {
                    break;
                }

                Modification modification = modifications.get(index);

                ChannelData data = null;

                if (forcedAge != 0) {
                    data = find(modification, datas, timeService.getCurrentTime() - forcedAge);
                }

                if (data == null) {
                    candidates.add(modification);
                    ++counter;
                    this.log.info("Process modification [ " + (counter) + " ] of [ " + modifications.size() + " ] URL [ " + modification.getUrl() + " ]. " + "Add to candidates");
                } else {
                    ++counter;
                    this.log.info("Process modification [ " + (counter) + " ] of [ " + modifications.size() + " ] URL [ " + modification.getUrl() + " ]. " + "Use old");
                    result.add(data);
                }

            }

            ListFragmentIterator<Modification> iterator = new ListFragmentIterator<Modification>(candidates, this.adapter.getPrecachedItemsCount());

            int candidateIndex = 0;

            while (iterator.hasNext()) {

                if (this.adapter.isCancelled()) {
                    break;
                }

                List<Modification> currents = iterator.getNext();

                if (!this.adapter.isSimpleHandling()) {
                    implicitPreCaching(currents);
                }

                for (Modification current : currents) {
                    ++candidateIndex;

                    if (this.adapter.isCancelled()) {
                        break;
                    }

                    result.add(processModification(current));
                    this.log.info("Process candidate [ " + (candidateIndex) + " ] of [ " + candidates.size() + " ] URL [ " + current.getUrl() + " ]. " + "Read new");

                    this.adapter.onProgress(new SingleProcessInfo("process.modification", candidateIndex, candidates.size()));
                }
            }

            if (this.adapter.isCancelled()) {
                this.adapter.onCancel();
            } else {
                this.adapter.removeChannelDataList();
                this.adapter.storeChannelDataList(result);
                this.log.info("[ " + candidates.size() + " ] channel data items updated");
                this.adapter.onComplete();
            }

        } catch (AdapterException e) {  //fatal
            this.adapter.onFault();
            throw new ChannelException(e);
        } catch (ChannelAnalyser.ChannelAnalyserException e) {
            this.adapter.onFault();
            throw new ChannelException(e);
        }
    }

    private void implicitPreCaching(final List<Modification> _modifications) throws AdapterException {
        this.adapter.getPageLoader().loadUrls(getUrls(_modifications), this.adapter.getPauseBetweenRequests(), new NullController());
    }

    private List<String> getUrls(final List<Modification> _modifications) {
        List<String> result = new ArrayList<String>();

        for (Modification modification : _modifications) {
            result.add(modification.getUrl());
        }

        return result;
    }

    private ChannelData find(final Modification _new, final ChannelDataList _existents, final long _age) {
        ChannelData result = null;
        String url = _new.getUrl();

        for (int index = 0; index < _existents.size(); ++index) {
            ChannelData data = _existents.get(index);

            if (url.equalsIgnoreCase(data.getSourceUrl())) {

                if (_age == -1) {
                    result = data;
                    break;
                }

                DatedItem latestItem = ChannelDataTools.getLatestItem(data);

                if (latestItem == null) {
                    this.log.debug("ChannelDataTools.getLatestItem returns null");
                } else {
                    long age = latestItem.getDate().getTime();

                    if (age <= _age) {
                        result = data;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private ChannelData processModification(final Modification _modification) throws AdapterException, ChannelAnalyser.ChannelAnalyserException {
        List<InterpreterData> datas = newArrayList();
        ChannelDataHeader header = getChannelDataHeader(_modification);
        List<InterpreterEx> interpreters = getInterpreters(_modification);

        for (InterpreterEx current : interpreters) {
            InterpreterData data = getInterpreterData(current);
            datas.add(data);
        }

        return new ChannelData(header, datas);
    }

    private ChannelDataHeader getChannelDataHeader(final Modification _modification) throws AdapterException, ChannelAnalyser.ChannelAnalyserException {
        ChannelAnalyser analyser = this.adapter.getAnalyser();
        BatchLoader loader = this.adapter.getPageLoader();

        ChannelDataHeader result = analyser.getHeader(_modification, loader, this.adapter.getCoverUrl());

        setGenresOrRemainDefault(result);
        setLangOrRemainDefault(result);

        return result;
    }

    private void setLangOrRemainDefault(final ChannelDataHeader _result) throws AdapterException {
        String lang = this.adapter.getLang();

        if (!lang.isEmpty()) {
            _result.setLang(lang);
        }
    }

    private void setGenresOrRemainDefault(final ChannelDataHeader _result) throws AdapterException {
        List<String> genres = this.adapter.getGenres();

        if (!genres.isEmpty()) {
            _result.setGenres(genres);
        }
    }

    private InterpreterData getInterpreterData(final InterpreterEx _interpreter) {
        InterpreterData result;

        try {
            result = _interpreter.process();
        } catch (InterpreterException e) {
            result = new InterpreterData();
        }

        return result;
    }

    private List<InterpreterEx> getInterpreters(final Modification _modification) throws AdapterException {
        return this.adapter.getInterpreters(_modification);
    }

    public static class ChannelException extends Exception {

        public ChannelException() {
            super();
        }

        public ChannelException(final String _s) {
            super(_s);
        }

        public ChannelException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public ChannelException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
