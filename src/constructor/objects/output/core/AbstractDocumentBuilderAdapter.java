package constructor.objects.output.core;

import app.VersionInfo;
import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import cloud.PropertiesCloud;
import constructor.dom.ObjectType;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DateSectionMode;
import timeservice.TimeService;
import util.Assert;
import util.DateTools;

import java.util.Date;
import java.util.Properties;

/**
 * @author Igor Usenko
 *         Date: 07.07.2010
 */
public abstract class AbstractDocumentBuilderAdapter implements DocumentBuilderAdapter {

    protected final TimeService timeService;
    protected final PropertiesCloud cloud;
    protected final VersionInfo versionInfo;
    protected final Controller controller;
    protected final int forcedDays;
    protected final ChannelDataListStorage channelDataListStorage;

    protected static final String LATEST_ITEM_TIME_KEY = "latest.item.time";
    protected static final String LATEST_ITEM_TIME_AS_TEXT_KEY = "latest.item.time.as.text";
    private static final String EPOC_START = "0";

    public AbstractDocumentBuilderAdapter(final TimeService _timeService, final PropertiesCloud _cloud, final VersionInfo _versionInfo, final Controller _controller, final int _forcedDays, final ChannelDataListStorage _channelDataListStorage) {
        Assert.notNull(_timeService, "Time service is null");
        timeService = _timeService;

        Assert.notNull(_cloud, "Cloud is null");
        cloud = _cloud;

        Assert.notNull(_versionInfo, "Version information is null");
        versionInfo = _versionInfo;

        Assert.notNull(_controller, "Controller is null");
        controller = _controller;

        Assert.notNull(_channelDataListStorage, "Channel data list storage is null");
        channelDataListStorage = _channelDataListStorage;

        forcedDays = _forcedDays;
    }

    protected abstract String getChannelId();

    public TimeService getTimeService() throws AdapterException {
        return this.timeService;
    }

    public Composition getComposition() throws AdapterException {
        return Composition.MANY_TO_ONE;
    }

    public long getLatestItemTime() throws AdapterException {

        try {
            Properties properties = this.cloud.readProperties(getId(), ObjectType.OUTPUT);
            String value = properties.getProperty(LATEST_ITEM_TIME_KEY, EPOC_START);

            return Long.valueOf(value);
        } catch (PropertiesCloud.PropertyCloudException e) {
            throw new AdapterException(e);
        } catch (RuntimeException e) {
            throw new AdapterException("Can`t get last update time for source [ " + getId() + " ].", e);
        }
    }

    public void setLatestItemTime(final long _time) throws AdapterException {
        Assert.greater(_time, 0, "Update time <= 0.");

        try {
            Properties properties = this.cloud.readProperties(getId(), ObjectType.SOURCE);

            properties.setProperty(LATEST_ITEM_TIME_KEY, Long.toString(_time));
            properties.setProperty(LATEST_ITEM_TIME_AS_TEXT_KEY, DateTools.formatAsDigits(new Date(_time)));

            this.cloud.storeProperties(getId(), ObjectType.OUTPUT, properties);
        } catch (PropertiesCloud.PropertyCloudException e) {
            throw new AdapterException(e);
        }
    }

    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public boolean resolveImageLinks() {
        return false;
    }

    public DateSectionMode getDateSectionMode() {
        return DateSectionMode.AUTO;
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

    public boolean isForcedMode() throws AdapterException {
        return (this.forcedDays >= 0);
    }

    public ChannelDataList getChannelDatas() throws AdapterException {

        try {
            return this.channelDataListStorage.load(getChannelId());
        } catch (ChannelDataListStorage.ChannelDataListStorageException e) {
            throw new AdapterException(e);
        }
    }
}
