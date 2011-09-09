package constructor.objects.source.core;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import cloud.PropertiesCloud;
import constructor.dom.ObjectType;
import constructor.objects.AdapterException;
import constructor.objects.source.core.processor.NoopModificationProcessor;
import constructor.objects.strategies.StoreStrategy;
import constructor.objects.strategies.UpdateStrategy;
import constructor.objects.strategies.store.StoreDaysStrategy;
import constructor.objects.strategies.store.StoreNeverStrategy;
import constructor.objects.strategies.update.EveryTimeUpdateStrategy;
import dated.item.modification.stream.ModificationList;
import timeservice.TimeService;
import util.Assert;
import util.DateTools;

import java.util.Date;
import java.util.Properties;

/**
 * @author Igor Usenko
 *         Date: 07.07.2010
 */
public abstract class AbstractSourceAdapter implements SourceAdapter {

    protected final TimeService timeService;
    protected final Controller controller;
    protected final PropertiesCloud cloud;
    protected final ModificationListStorage storage;

    protected static final String LAST_UPDATE_TIME_KEY = "last.update.time";
    protected static final String LAST_UPDATE_TIME_AS_TEXT_KEY = "last.update.time.as.text";
    protected static final String EPOC_START = "0";

    public AbstractSourceAdapter(final TimeService _timeService, final Controller _controller, final PropertiesCloud _cloud, final ModificationListStorage _storage) {
        Assert.notNull(_timeService, "Time service is null.");
        timeService = _timeService;

        Assert.notNull(_controller, "Controller is null");
        controller = _controller;

        Assert.notNull(_cloud, "Cloud is null.");
        cloud = _cloud;

        Assert.notNull(_storage, "Storage is null.");
        storage = _storage;
    }

    public TimeService getTimeService() {
        return this.timeService;
    }

    public ModificationProcessor getProcessor() throws AdapterException {
        return new NoopModificationProcessor();
    }

    public UpdateStrategy getUpdateStrategy() throws AdapterException {
        return new EveryTimeUpdateStrategy();
    }

    public Date getLastUpdateTime() throws AdapterException {

        try {
            Properties properties = this.cloud.readProperties(getId(), ObjectType.SOURCE);
            String value = properties.getProperty(LAST_UPDATE_TIME_KEY, EPOC_START);

            return new Date(Long.valueOf(value));
        } catch (PropertiesCloud.PropertyCloudException e) {
            throw new AdapterException(e);
        } catch (RuntimeException e) {
            throw new AdapterException("Can`t get last update time for source [ " + getId() + " ].", e);
        }
    }

    public void setLastUpdateTimeToCurrent() throws AdapterException {

        try {
            Properties properties = this.cloud.readProperties(getId(), ObjectType.SOURCE);

            long time = this.timeService.getCurrentTime();
            properties.setProperty(LAST_UPDATE_TIME_KEY, Long.toString(time));
            properties.setProperty(LAST_UPDATE_TIME_AS_TEXT_KEY, DateTools.formatAsDigits(new Date(time)));

            this.cloud.storeProperties(getId(), ObjectType.SOURCE, properties);
        } catch (PropertiesCloud.PropertyCloudException e) {
            throw new AdapterException(e);
        }
    }

    public ModificationList getList() throws AdapterException {

        try {
            return this.storage.load(getId());
        } catch (ModificationListStorage.ModificationListStorageException e) {
            throw new AdapterException(e);
        }
    }

    public void storeList(ModificationList _list) throws AdapterException {
        Assert.notNull(_list, "Modification list is null.");

        try {
            this.storage.store(getId(), _list);
        } catch (ModificationListStorage.ModificationListStorageException e) {
            throw new AdapterException(e);
        }
    }

    protected StoreStrategy createStoreStrategy(final int _storeDays) {
        return _storeDays == 0 ? new StoreNeverStrategy() : new StoreDaysStrategy(_storeDays, this.timeService);
    }

    public void onStart() {
        this.controller.onStart();
    }

    public void onProgress(final SingleProcessInfo _info) {
        Assert.notNull(_info, "Process info is null");

        this.controller.onProgress(_info);
    }

    public void onComplete() {
        this.controller.onComplete();
    }

    public void onFault() {
        this.controller.onFault();
    }

    public void onCancel() {
        this.controller.onCancel();
    }

    public boolean isCancelled() {
        return this.controller.isCancelled();
    }
}
