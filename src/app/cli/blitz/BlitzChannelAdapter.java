package app.cli.blitz;

import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.CriterionType;
import app.cli.blitz.request.RequestSourceType;
import app.iui.flow.custom.SingleProcessInfo;
import app.workingarea.ServiceManager;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.ChannelAdapter;
import constructor.objects.channel.core.ChannelAdapterTools;
import constructor.objects.channel.core.ChannelAnalyser;
import constructor.objects.channel.core.analyser.StandardAnalyser;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.adapter.SimpleInterpreterAdapter;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.interpreter.core.InterpreterAdapter;
import constructor.objects.interpreter.core.InterpreterEx;
import constructor.objects.interpreter.core.standard.SimpleInterpreter;
import constructor.objects.interpreter.core.standard.StandardInterpreter;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import http.BatchLoader;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ������� ����-������ ��������� �����������
 *
 * @author Igor Usenko
 *         Date: 29.10.2009
 */
public class BlitzChannelAdapter implements ChannelAdapter {

    private final BlitzRequest request;
    private final ModificationList modificationList;
    private final ServiceManager serviceManager;
    private final int precachedItemsCount;

    private ChannelDataList result;

    private static final String BLITZ_CHANNEL_ADAPTER_ID = "blitz_channel_adapter";
    private static final String BLITZ_CHAIN_PROCESSOR_ADAPTER_ID = "blitz_chain_processor_adapter";

    public BlitzChannelAdapter(final BlitzRequest _request, final ModificationList _modificationList, final ServiceManager _serviceManager, final int _precachedItemsCount) throws ServiceManager.ServiceManagerException {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_modificationList, "Modification list is null");
        this.modificationList = _modificationList;

        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;

        Assert.greater(_precachedItemsCount, 0, "Precached items count < 1");
        this.precachedItemsCount = _precachedItemsCount;

        this.result = new ChannelDataList();
    }

    public String getId() {
        return BLITZ_CHANNEL_ADAPTER_ID;
    }

    public ModificationList getModificationsList() throws AdapterException {
        return this.modificationList;
    }

    public ChannelDataList getChannelDataList() throws AdapterException {
        return this.result;
    }

    public void removeChannelDataList() throws AdapterException {
        // empty
    }

    public void storeChannelDataList(final ChannelDataList _datas) throws AdapterException {
        Assert.notNull(_datas, "Channel data list is null");
        this.result = _datas;
    }

    public ChannelAnalyser getAnalyser() throws AdapterException {
        return new StandardAnalyser();
    }

    public BatchLoader getPageLoader() throws AdapterException {
        return this.serviceManager.getBatchLoader();
    }

    public List<InterpreterEx> getInterpreters(final Modification _modification) throws AdapterException {
        Assert.notNull(_modification, "Modification is null");

        List<InterpreterEx> result = newArrayList();

        if (isSimpleHandling()) {
            result.add(new SimpleInterpreter(new SimpleInterpreterAdapter(_modification)));
        } else {
            result.add(new StandardInterpreter(createInterpreterAdapter(_modification)));
        }

        return result;
    }

    public long getForcedAge() throws AdapterException {
        return this.request.isForced() ? 0 : this.request.getMaxAge();
    }

    public List<String> getGenres() throws AdapterException {
        return this.request.getGenres();
    }

    public String getLang() throws AdapterException {
        return this.request.getLang();
    }

    public String getCoverUrl() throws AdapterException {
        return this.request.getCoverUrl();
    }

    public TimeService getSystemTimeService() throws AdapterException {

        try {
            return this.serviceManager.getTimeService();
        } catch (ServiceManager.ServiceManagerException e) {
            throw new AdapterException(e);
        }
    }

    public int getPrecachedItemsCount() {
        return this.precachedItemsCount;
    }

    public long getPauseBetweenRequests() {
        return 0;
    }

    public boolean isSimpleHandling() {
        boolean rssOrModificationsWithoutCriterionExpression = (this.request.getSourceType() == RequestSourceType.RSS || this.request.getSourceType() == RequestSourceType.MODIFICATIONS) && this.request.expressionRemainsDefault();
        boolean filterModeNotUsed = this.request.getCriterionType() != CriterionType.FILTER;

        return filterModeNotUsed && rssOrModificationsWithoutCriterionExpression;
    }

    public boolean isCancelled() {
        return false;
    }

    public ChannelDataList getResult() {
        return this.result;
    }

    public void onStart() {
        // empty
    }

    public void onProgress(final SingleProcessInfo _info) {
        // empty
    }

    public void onComplete() {
        // empty
    }

    public void onFault() {
        // empty
    }

    public void onCancel() {
        // empty
    }

    private InterpreterAdapter createInterpreterAdapter(final Modification _modification) {
        FragmentAnalyserConfiguration fragmentAnalyserConfiguration = this.request.getCriterionType() == CriterionType.FILTER ?
                ChannelAdapterTools.createContentFilterConfiguration(BLITZ_CHAIN_PROCESSOR_ADAPTER_ID, this.serviceManager.getDebugConsole()) :
                ChannelAdapterTools.createFragmentAnalyserConfiguration(this.request.getCriterionType(),
                        this.request.getCriterionExpression(),
                        BLITZ_CHAIN_PROCESSOR_ADAPTER_ID,
                        this.serviceManager.getDebugConsole());

        return new BlitzInterpreterAdapter(_modification,
                fragmentAnalyserConfiguration,
                this.serviceManager.getBatchLoader(),
                getPrecachedItemsCount(),
                getPauseBetweenRequests());
    }

}
