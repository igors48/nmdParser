package constructor.objects.channel.core;

import app.controller.Controller;
import app.controller.NullController;
import app.iui.flow.custom.SingleProcessInfo;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.analyser.StandardAnalyser;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.source.core.ModificationListStorage;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import http.BatchLoader;
import http.HttpRequestHandler;
import http.StandardBatchLoaderEx;
import timeservice.StandardTimeService;
import timeservice.StillTimeService;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 07.07.2010
 */
public abstract class AbstractChannelAdapter implements ChannelAdapter {
    protected final static long DAYS_TO_MILLIS = 24 * 60 * 60 * 1000;

    protected final long forcedAge;
    protected final ModificationListStorage modificationListStorage;
    protected final ChannelDataListStorage channelDataListStorage;
    protected final Controller controller;
    protected final TimeService timeService;
    protected final int precachedItemsCount;
    protected final long pauseBetweenRequests;
    protected final BatchLoader batchLoader;

    public AbstractChannelAdapter(final ChannelDataListStorage _channelDataListStorage, final HttpRequestHandler _httpRequestHandler, final ModificationListStorage _modificationListStorage, final int _forcedDays, final TimeService _timeService, final Controller _controller, final int _precachedItemsCount, final long _pauseBetweenRequests) {
        Assert.notNull(_channelDataListStorage, "Channel data list storage is null.");
        this.channelDataListStorage = _channelDataListStorage;

        Assert.notNull(_modificationListStorage, "Modification list storage is null.");
        this.modificationListStorage = _modificationListStorage;

        Assert.notNull(_timeService, "Time service is null.");
        this.timeService = _timeService;

        Assert.notNull(_controller, "Controller is null.");
        this.controller = _controller;

        Assert.greater(_precachedItemsCount, 0, "Precached items count < 1");
        this.precachedItemsCount = _precachedItemsCount;

        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");
        this.pauseBetweenRequests = _pauseBetweenRequests;

        this.batchLoader = new StandardBatchLoaderEx(_httpRequestHandler, new NullController());

        this.forcedAge = _forcedDays < 0 ? -1 : _forcedDays * DAYS_TO_MILLIS;
    }

    public ModificationList getModificationsList() throws AdapterException {

        try {
            return this.modificationListStorage.load(getSourceId());
        } catch (ModificationListStorage.ModificationListStorageException e) {
            throw new AdapterException(e);
        }
    }

    public ChannelDataList getChannelDataList() throws AdapterException {

        try {
            return this.channelDataListStorage.load(getId());
        } catch (ChannelDataListStorage.ChannelDataListStorageException e) {
            throw new AdapterException(e);
        }
    }

    public void removeChannelDataList() throws AdapterException {

        try {
            this.channelDataListStorage.remove(getId());
        } catch (ChannelDataListStorage.ChannelDataListStorageException e) {
            throw new AdapterException(e);
        }
    }

    public void storeChannelDataList(final ChannelDataList _datas) throws AdapterException {
        Assert.notNull(_datas, "Channel data list is null.");

        try {
            this.channelDataListStorage.store(getId(), _datas);
        } catch (ChannelDataListStorage.ChannelDataListStorageException e) {
            throw new AdapterException(e);
        }
    }

    public TimeService getSystemTimeService() throws AdapterException {
        return this.timeService;
    }

    public int getPrecachedItemsCount() {
        return this.precachedItemsCount;
    }

    public long getPauseBetweenRequests() {
        return this.pauseBetweenRequests;
    }

    public BatchLoader getPageLoader() throws AdapterException {
        return this.batchLoader;
    }

    public ChannelAnalyser getAnalyser() throws AdapterException {
        return new StandardAnalyser();
    }

    public List<String> getGenres() throws AdapterException {
        return ChannelDataTools.DEFAULT_GENRES;
    }

    public String getLang() throws AdapterException {
        return ChannelDataTools.DEFAULT_LANG;
    }

    protected abstract String getSourceId();

    /**
     * ���������� ������� ������ �������. ����� ��� ����������� ���������� ������������� ���
     * ����������� ���� "�����" "������ �����" � �.�.
     * <p/>
     * ��������� ����������
     * <p/>
     * ��� ������������� ���������� ������������ ����� ����������� ������ �������.
     * ��� ������� ���������� ��� StillTimeService � ����� ������������
     *
     * @param _modification �����������
     * @return ������� ������ �������
     * @throws constructor.objects.AdapterException
     *          ���� �� ����������
     */
    protected TimeService getRefTimeService(final Modification _modification) throws AdapterException {
        return this.forcedAge >= 0 ? new StandardTimeService() : new StillTimeService(_modification.getDate());
    }

    public boolean isCancelled() {
        return this.controller.isCancelled();
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

}
