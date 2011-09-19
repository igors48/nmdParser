package converter.format.fb2.resource.resolver;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import converter.format.fb2.resource.Fb2ResourceItem;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import html.HttpData;
import http.BatchLoader;
import http.Data;
import http.HttpRequestHandler;
import http.StandardBatchLoader;
import http.data.DataFile;
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

    private final HttpRequestHandler httpRequestHandler;
    private final String dummy;
    private final Log log;
    private final ConverterFactory factory;
    private final ResourceCache cache;
    private final String tempDir;
    private final Controller controller;

    private Map<Fb2ResourceItem, String> itemMap;

    //todo ���� ��� ������� ����� ��������� �� ����� �����, �� ��� ������������ ������� ����� �������� � �������������� � ������ ��������

    //todo ���������, ����� ������� ������������ ����� � �������� �� ��������

    //todo ��� ����� ������ ��������� ���� ����� � Fb2, ��� �� ��� ���������� - ��� Fb2ResourceItem - ���� ����� ��� ���������

    //todo ���� ������ ����� �������� �������� - ����� ����� ����������� ��������������, �� �������������� � �������������� ����
    //todo ��� �� ������� - ������ ��������� - ��������, � �� ��� ����� - �� ��� ��������

    public Fb2ResourceBundleResolver(final HttpRequestHandler _httpRequestHandler, final ConverterFactory _factory, final ResourceCache _cache, final String _dummy, final String _tempDir, final Controller _controller) {
        Assert.notNull(_httpRequestHandler, "Http request handler is null");
        Assert.notNull(_factory, "Converter factory is null");
        Assert.notNull(_cache, "Resource cache is null");
        Assert.isValidString(_dummy, "Resource dummy is not valid");
        Assert.isValidString(_tempDir, "Temp directory is not valid");
        Assert.isTrue(new File(_tempDir).exists(), "Temp directory does not exists");
        Assert.notNull(_controller, "Conttroller is null");

        this.httpRequestHandler = _httpRequestHandler;
        this.factory = _factory;
        this.cache = _cache;
        this.dummy = _dummy;
        this.tempDir = PathTools.normalize(_tempDir);
        this.controller = _controller;

        this.log = LogFactory.getLog(getClass());
    }

    public void resolve(final List<Fb2ResourceItem> _resources) {
        Assert.notNull(_resources, "Resources is null");

        this.itemMap = new HashMap<Fb2ResourceItem, String>();

        List<String> list = new ArrayList<String>();

        for (Fb2ResourceItem item : _resources) {
            String request = createRequest(item);

            if (!request.isEmpty()) {
                //����� ����� ��������� ����� ���� �� ������
                list.add(request);
                this.itemMap.put(item, request);
            }
        }

        BatchLoader loader = new StandardBatchLoader(this.httpRequestHandler, this);

        Map<String, HttpData> loaded = loader.loadUrls(list, 0);

        joinMaps(loaded);

        checkResourcesFormat(_resources);

        commitCacheToc();
    }

    private void commitCacheToc() {
        this.cache.commitToc();
    }

    private void joinMaps(final Map<String, HttpData> _loaded) {

        for (Fb2ResourceItem item : this.itemMap.keySet()) {
            String list = this.itemMap.get(item);
            HttpData data = _loaded.get(list);

            if (data == null) {
                this.log.error("Can`t find Data for request list id [ " + list + " ]");
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

    private String createRequest(final Fb2ResourceItem _item) {
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
            return address;
        } else {
            this.log.debug("Resource from URL [ " + address + " ] taken from cache");
            return "";
        }
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