package app.cli.blitz;

import app.cli.blitz.request.BlitzRequest;
import app.iui.flow.custom.SingleProcessInfo;
import app.workingarea.Settings;
import constructor.objects.AdapterException;
import constructor.objects.source.core.ModificationFetcher;
import constructor.objects.source.core.ModificationProcessor;
import constructor.objects.source.core.SourceAdapter;
import constructor.objects.source.core.fetcher.ModificationsListFetcher;
import constructor.objects.source.core.fetcher.RssFeedFetcher;
import constructor.objects.source.core.fetcher.UrlFetcher;
import constructor.objects.source.core.processor.NoopModificationProcessor;
import constructor.objects.strategies.StoreStrategy;
import constructor.objects.strategies.UpdateStrategy;
import constructor.objects.strategies.store.StoreMaxAgeStrategy;
import constructor.objects.strategies.store.StoreNeverStrategy;
import constructor.objects.strategies.update.EveryTimeUpdateStrategy;
import dated.item.modification.stream.ModificationList;
import http.BatchLoader;
import timeservice.TimeService;
import util.Assert;

import java.util.Date;

/**
 * ������� ����-��������� �����������
 *
 * @author Igor Usenko
 *         Date: 28.10.2009
 */
public class BlitzSourceAdapter implements SourceAdapter {

    private final BlitzRequest request;
    private final TimeService timeService;
    private final BatchLoader batchLoader;
    private final Settings settings;

    private ModificationList result;

    private static final String BLITZ_SOURCE_ADAPTER_ID = "blitz_source_adapter";

    public BlitzSourceAdapter(final BlitzRequest _request, final TimeService _timeService, final BatchLoader _batchLoader, final Settings _settings) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_timeService, "Time service is null");
        this.timeService = _timeService;

        Assert.notNull(_batchLoader, "Batch loader is null");
        this.batchLoader = _batchLoader;

        Assert.notNull(_settings, "Settings is null");
        this.settings = _settings;

        this.result = new ModificationList();
    }

    public String getId() throws AdapterException {
        return BLITZ_SOURCE_ADAPTER_ID;
    }

    public ModificationFetcher getFetcher() throws AdapterException {
        ModificationFetcher result = null;

        switch (this.request.getSourceType()) {
            case RSS: {
                result = new RssFeedFetcher(this.request.getAddresses().get(0), this.timeService, this.settings.getMaxTryCount(), this.settings.getErrorTimeout(), this.settings.getMinTimeout(), this.batchLoader);
                break;
            }
            case URLS: {
                result = new UrlFetcher(this.request.getAddresses(), this.timeService);
                break;
            }
            case MODIFICATIONS: {
                result = new ModificationsListFetcher(this.request.getModifications());
                break;
            }
        }

        return result;
    }

    public ModificationList getList() throws AdapterException {
        return this.result;
    }

    public void storeList(final ModificationList _list) throws AdapterException {
        Assert.notNull(_list, "Modification list is null");
        this.result = _list;
    }

    public ModificationProcessor getProcessor() throws AdapterException {
        return new NoopModificationProcessor();
    }

    public StoreStrategy getStoreStrategy() throws AdapterException {
        return this.request.isForced() ? new StoreNeverStrategy() : new StoreMaxAgeStrategy(this.request.getMaxAge(), this.timeService);
    }

    public UpdateStrategy getUpdateStrategy() throws AdapterException {
        return new EveryTimeUpdateStrategy();
    }

    public Date getLastUpdateTime() throws AdapterException {
        return this.timeService.getCurrentDate();
    }

    public void setLastUpdateTimeToCurrent() throws AdapterException {
        // empty
    }

    public TimeService getTimeService() {
        return this.timeService;
    }

    public ModificationList getResult() {
        return this.result;
    }

    public void onStart() {
        // empty
    }

    public void onProgress(final SingleProcessInfo _info) {
        Assert.notNull(_info, "Process info is null");
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

    public boolean isCancelled() {
        return false;
    }
}
