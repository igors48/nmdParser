package converter.format.fb2.resource;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import converter.format.fb2.Stringable;
import converter.format.fb2.resource.resolver.Fb2ResourceBundleResolver;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import http.BatchLoader;
import http.HttpRequestHandler;
import resource.ConverterFactory;
import util.Assert;
import util.PathTools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 06.09.2008
 */
public class Fb2ResourceBundle implements Stringable {

    private final static int ITEM_NOT_FOUND = -1;

    private final static long RAND_HIGH_LIMIT = 1000000;

    private final static String TAG_PREFIX = "TAG";

    private final List<Fb2ResourceItem> content;
    private final String dummy;
    private final BatchLoader batchLoader;
    private final ConverterFactory factory;
    private final ResourceCache cache;
    private final String tempDir;
    private final Fb2ResourceConversionContext conversionContext;
    private final Controller controller;

    //private final Log log;

    //todo �������� � ����������� ������, ������� ����� ��� �� �����. ��� ������� - ������ ���������� ��� �������� �������� � ���

    public Fb2ResourceBundle(final BatchLoader _batchLoader, final ConverterFactory _factory, final ResourceCache _cache, final String _dummy, final String _tempDir, final Fb2ResourceConversionContext _conversionContext, final Controller _controller) {
        Assert.notNull(_batchLoader, "Batch loader is null");
        this.batchLoader = _batchLoader;

        Assert.notNull(_factory, "Converter factory is null");
        this.factory = _factory;

        Assert.notNull(_cache, "Resource cache is null");
        this.cache = _cache;

        Assert.isValidString(_dummy, "Resource dummy is not valid");
        this.dummy = _dummy;

        Assert.isValidString(_tempDir, "Temp directory is not valid");
        Assert.isTrue(new File(_tempDir).exists(), String.format("Temp directory [ %s ] does not exists", _tempDir));
        this.tempDir = PathTools.normalize(_tempDir);

        Assert.notNull(_conversionContext, "Conversion context is null");
        this.conversionContext = _conversionContext;

        Assert.notNull(_controller, "Controller is null");
        this.controller = _controller;

        this.content = new ArrayList<Fb2ResourceItem>();
    }

    public String appendResourceItem(final String _base, final String _address) {
        Assert.notNull(_base, "Base is null");
        Assert.isValidString(_address, "Address is not valid");

        int index = find(_address);

        if (index == ITEM_NOT_FOUND) {
            String tag = generate(_address);
            this.content.add(new Fb2ResourceItem(_base, _address, tag, this.conversionContext));

            return tag;
        }

        return this.content.get(index).getTag();
    }

    public String[] getStrings() {
        List<String> result = new ArrayList<String>();

        int index = 0;

        for (Fb2ResourceItem current : this.content) {

            if (this.controller.isCancelled()) {
                break;
            }

            result.addAll(Arrays.asList(current.getStrings()));

            this.controller.onProgress(new SingleProcessInfo("process.image.processing", ++index, this.content.size()));
        }

        return result.toArray(new String[result.size()]);
    }

    public void resolve() {
        new Fb2ResourceBundleResolver(this.batchLoader, this.factory, this.cache, this.dummy, this.tempDir, this.controller).resolve(this.content);
    }

    private String generate(final String _address) {
        StringBuilder tag = new StringBuilder();

        do {
            String insert = PathTools.removeIllegals(_address);
            tag = tag.append(TAG_PREFIX).append(insert).append(Long.toString((long) (Math.random() * RAND_HIGH_LIMIT)));
        } while (!(findTag(tag.toString()) == ITEM_NOT_FOUND));

        return tag.toString();
    }

    private int find(final String _address) {

        for (int index = 0; index < this.content.size(); ++index) {
            Fb2ResourceItem current = this.content.get(index);

            if (current.getAddress().equals(_address)) {
                return index;
            }
        }

        return ITEM_NOT_FOUND;
    }

    private int findTag(final String _tag) {

        for (int index = 0; index < this.content.size(); ++index) {
            Fb2ResourceItem current = this.content.get(index);

            if (current.getTag().equals(_tag)) {
                return index;
            }
        }

        return ITEM_NOT_FOUND;
    }
}
