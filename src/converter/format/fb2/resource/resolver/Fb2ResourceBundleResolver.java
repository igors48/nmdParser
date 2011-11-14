package converter.format.fb2.resource.resolver;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import converter.format.fb2.resource.Fb2ResourceItem;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import downloader.*;
import downloader.batchloader.StandardBatchLoader;
import downloader.data.DataFile;
import html.HttpData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import resource.Converter;
import resource.ConverterFactory;
import resource.ResourceType;
import resource.ResourceUtil;
import util.Assert;
import util.IOTools;
import util.PathTools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 24.09.2008
 */
public class Fb2ResourceBundleResolver implements Controller {

    private final static String REMOTE_RESOURCE_SIGN = "http://";
    private static final String TEMP_FILE_PRF = "chd";
    private static final String TEMP_FILE_SFX = "chd";

    private final Downloader downloader;
    private final String dummy;
    private final Log log;
    private final ConverterFactory factory;
    private final ResourceCache cache;
    private final String tempDir;
    private final Controller controller;

    private Map<Fb2ResourceItem, RequestList> itemMap;

    //todo ���� ��� ������� ����� ��������� �� ����� �����, �� ��� ������������ ������� ����� �������� � �������������� � ������ ��������

    //todo ���������, ����� ������� ������������ ����� � �������� �� ��������

    //todo ��� ����� ������ ��������� ���� ����� � Fb2, ��� �� ��� ���������� - ��� Fb2ResourceItem - ���� ����� ��� ���������

    //todo ���� ������ ����� �������� �������� - ����� ����� ����������� ��������������, �� �������������� � �������������� ����
    //todo ��� �� ������� - ������ ��������� - ��������, � �� ��� ����� - �� ��� ��������

    public Fb2ResourceBundleResolver(final Downloader _downloader, final ConverterFactory _factory, final ResourceCache _cache, final String _dummy, final String _tempDir, final Controller _controller) {
        Assert.notNull(_downloader, "Downloader is null");
        Assert.notNull(_factory, "Converter factory is null");
        Assert.notNull(_cache, "Resource cache is null");
        Assert.isValidString(_dummy, "Resource dummy is not valid");
        Assert.isValidString(_tempDir, "Temp directory is not valid");
        Assert.isTrue(new File(_tempDir).exists(), "Temp directory does not exists");
        Assert.notNull(_controller, "Conttroller is null");

        this.downloader = _downloader;
        this.factory = _factory;
        this.cache = _cache;
        this.dummy = _dummy;
        this.tempDir = PathTools.normalize(_tempDir);
        this.controller = _controller;

        this.log = LogFactory.getLog(getClass());
    }

    public void resolve(final List<Fb2ResourceItem> _resources) {
        Assert.notNull(_resources, "Resources is null");

        this.itemMap = new HashMap<Fb2ResourceItem, RequestList>();

        List<RequestList> list = new ArrayList<RequestList>();

        for (Fb2ResourceItem item : _resources) {
            List<Request> requests = createRequests(item);

            if (requests.size() != 0) {
                //����� ����� ��������� ����� ���� �� ������
                RequestList requestList = new RequestList(requests, 0);
                list.add(requestList);
                this.itemMap.put(item, requestList);
            }
        }

        BatchLoader loader = new StandardBatchLoader(this.downloader, this);

        Map<RequestList, HttpData> loaded = loader.load(list);

        joinMaps(loaded);

        checkResourcesFormat(_resources);

        commitCacheToc();
    }

    private void commitCacheToc() {
        this.cache.commitToc();
    }

    private void joinMaps(final Map<RequestList, HttpData> _loaded) {

        for (Fb2ResourceItem item : this.itemMap.keySet()) {
            RequestList list = this.itemMap.get(item);
            HttpData data = _loaded.get(list);

            if (data == null) {
                this.log.error("Can`t find Data for request list id [ " + list.getId() + " ]");
            } else {
                item.setData(data.getData());
            }
        }
    }

    private void checkResourcesFormat(final List<Fb2ResourceItem> _resources) {

        for (Fb2ResourceItem item : _resources) {
            checkResourceFormat(item);
        }
    }

    private void checkResourceFormat(final Fb2ResourceItem _item) {

        try {

            if (_item.getData().size() == 0) {
                _item.setData(new DataFile(this.dummy));
                return;
            }

            ResourceType type = ResourceUtil.getResourceType(_item.getData());

            if (type == ResourceType.IMAGE_JPEG || type == ResourceType.IMAGE_PNG) {
                putDataToCache(_item);
                return;
            }

            if (type == ResourceType.UNKNOWN) {
                _item.setData(new DataFile(this.dummy));
                return;
            }

            Converter converter = this.factory.getConverter(type, ResourceType.IMAGE_PNG);

            if (converter == null) {
                _item.setData(new DataFile(this.dummy));
                return;
            }

            Data converted = converter.convert(_item.getData());
            _item.setData(converted);

            putDataToCache(_item);
        } catch (Exception e) {
            this.log.error("Error converting resource", e);
            _item.setData(new DataFile(this.dummy));
        }
    }

    private void putDataToCache(final Fb2ResourceItem _item) {
        String resolvedAddress = _item.getResolvedAddress();

        if (resolvedAddress != null && !resolvedAddress.isEmpty() && !_item.isFromCache()) {
            this.cache.put(resolvedAddress, _item.getData());
        }
    }

    private List<Request> createRequests(final Fb2ResourceItem _item) {
        List<Request> result;

        if (isItRemoteResource(_item)) {
            result = createRequestsForRemote(_item);
        } else {
            result = createRequestsForLocal(_item);
        }

        return result;
    }

    private List<Request> createRequestsForLocal(final Fb2ResourceItem _item) {
        List<Request> result = new ArrayList<Request>();

        Request request = new Request(_item.getAddress(), Destination.FILE);
        result.add(request);
        request = new Request(this.dummy, Destination.FILE);
        result.add(request);

        return result;
    }

    private List<Request> createRequestsForRemote(final Fb2ResourceItem _item) {
        List<Request> result = new ArrayList<Request>();
        Request request;

        String address = isItFullRemotePath(_item.getAddress()) ?
                _item.getAddress() :
                Fb2ResourceResolverTools.joinAddress(_item.getBase(), _item.getAddress());

        _item.setResolvedAddress(address);
        Data data = this.cache.get(address);

        if (data != null) {
            data = copyCached(data);
            _item.setData(data);
            _item.setFromCache(true);
        }

        if (data == null) {
            request = new Request(address, Destination.FILE);
            result.add(request);
        } else {
            this.log.debug("Resource from URL [ " + address + " ] taken from cache");
        }


        return result;
    }

    private Data copyCached(final Data _data) {
        Data result = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            File tempFile = File.createTempFile(TEMP_FILE_PRF, TEMP_FILE_SFX, new File(this.tempDir));
            outputStream = new FileOutputStream(tempFile);

            inputStream = new ByteArrayInputStream(_data.getData());

            IOTools.copy(inputStream, outputStream);

            result = new DataFile(tempFile.getAbsolutePath());
        } catch (IOException e) {
            this.log.error("Error copying cached resource", e);
        } catch (Data.DataException e) {
            this.log.error("Error copying cached resource", e);
        } finally {
            IOTools.close(outputStream);
            IOTools.close(inputStream);
        }

        return result;
    }

    private boolean isItRemoteResource(final Fb2ResourceItem _item) {
        boolean remoteBase = _item.getBase().startsWith(REMOTE_RESOURCE_SIGN) || _item.getBase().startsWith(REMOTE_RESOURCE_SIGN.toUpperCase());
        boolean remoteAddress = _item.getAddress().startsWith(REMOTE_RESOURCE_SIGN) || _item.getAddress().startsWith(REMOTE_RESOURCE_SIGN.toUpperCase());

        return remoteAddress || remoteBase;
    }

    private boolean isItFullRemotePath(final String _path) {
        return _path.trim().toUpperCase().startsWith(REMOTE_RESOURCE_SIGN.toUpperCase());
    }

    public void onStart() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onProgress(final SingleProcessInfo _info) {
        Assert.notNull(_info, "Process info is null");
        this.controller.onProgress(new SingleProcessInfo("process.loading.images", _info.getCurrent(), _info.getTotal()));
    }

    public void onComplete() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onFault() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onCancel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isCancelled() {
        return this.controller.isCancelled();
    }
}