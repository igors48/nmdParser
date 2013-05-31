package constructor.objects.output.core;

import constructor.objects.AdapterException;
import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.core.processor.StandardChannelDataListProcessorContext;
import constructor.objects.output.core.processor.StandardChannelDataListProcessorEx;
import dated.DatedItem;
import flowtext.Document;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.DateTools;

import java.util.Date;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Класс из данных канала строит документ и отдает его на конвертацию
 * Разруливает режимы "one-to-one" и "many-to-one"
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public class DocumentBuilder {

    private final DocumentBuilderAdapter adapter;

    private final Log log;

    public DocumentBuilder(final DocumentBuilderAdapter _adapter) {
        Assert.notNull(_adapter, "Document builder adapter is null.");
        this.adapter = _adapter;

        this.log = LogFactory.getLog(getClass());
    }

    public List<String> process() throws DocumentBuilderException {
        List<String> result = newArrayList();

        this.adapter.onStart();

        try {
            ChannelDataList datas = this.adapter.getChannelDatas();

            if (datas.isEmpty()) {
                this.log.info("Channel data is empty. Nothing to do here.");
            } else {
                long latestStoredItemTime = this.adapter.getLatestItemTime();
                long latestChannelDataItemTime = 0;

                DatedItem latest = ChannelDataTools.getLatestItem(datas);

                boolean needsUpdate = false;

                if (latest != null) {
                    latestChannelDataItemTime = latest.getDate().getTime();
                    needsUpdate = (latestStoredItemTime < latestChannelDataItemTime) || this.adapter.isForcedMode();
                }

                if (needsUpdate) {
                    String name = this.adapter.getDocumentName();

                    List<ChannelDataList> workItems = null;

                    if (this.adapter.getComposition() == Composition.MANY_TO_ONE) {
                        workItems = newArrayList();
                        workItems.add(datas);
                    } else {
                        workItems = split(datas);
                    }

                    if (workItems != null) {
                        result.addAll(processItems(workItems, name));
                    }

                    this.adapter.setLatestItemTime(latestChannelDataItemTime);
                } else {
                    this.log.info("Channel data doesn`t changed. Latest update at [ " + DateTools.format(new Date(latestStoredItemTime)) + " ]. Nothing to do here.");
                }
            }

            if (this.adapter.isCancelled()) {
                this.adapter.onCancel();
            } else {
                this.adapter.onComplete();
            }

            return result;
        } catch (AdapterException e) {
            this.adapter.onFault();

            throw new DocumentBuilderException(e);
        }
    }

    private List<ChannelDataList> split(final ChannelDataList _datas) {
        List<ChannelDataList> result = newArrayList();

        for (int index = 0; index < _datas.size(); ++index) {
            ChannelData data = _datas.get(index);

            ChannelDataList list = new ChannelDataList();
            list.add(data);

            result.add(list);
        }

        return result;
    }

    private List<String> processItems(final List<ChannelDataList> _items, final String _name) throws AdapterException {
        List<String> result = newArrayList();

        for (ChannelDataList current : _items) {
            result.addAll(processItem(current, _name));

            if (this.adapter.isCancelled()) {
                break;
            }
        }

        return result;
    }

    private List<String> processItem(final ChannelDataList _datas, final String _name) throws AdapterException {
        ChannelDataListProcessor processor = new StandardChannelDataListProcessorEx(new StandardChannelDataListProcessorContext(this.adapter.resolveImageLinks(), this.adapter.getVersionInfo(), this.adapter.getDateSectionMode(), this.adapter.isFromNewToOld()));
        Document document = processor.process(_datas, _name, this.adapter.getTimeService());

        return this.adapter.postprocessDocument(document);
    }

    public static class DocumentBuilderException extends Exception {

        public DocumentBuilderException() {
            super();
        }

        public DocumentBuilderException(final String _s) {
            super(_s);
        }

        public DocumentBuilderException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public DocumentBuilderException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
