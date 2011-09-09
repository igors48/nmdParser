package html;

import downloader.Data;
import downloader.Result;
import downloader.data.EmptyData;
import downloader.data.MemoryData;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 09.10.2008
 */
public class HttpData {

    public static HttpData EMPTY_DATA = new HttpData("url", new EmptyData(), Result.OK);
    public static HttpData ERROR_DATA = new HttpData("url", new EmptyData(), Result.ERROR);
    public static HttpData CANCEL_DATA = new HttpData("url", new EmptyData(), Result.CANCEL);

    private final String url;
    private final Data data;
    private final Result result;

    public HttpData(final String _url, final Data _data, final Result _result) {
        Assert.isValidString(_url, "URL is invalid");
        this.url = _url;

        Assert.notNull(_data, "Data is null");
        this.data = _data;

        Assert.notNull(_result, "Result is null");
        this.result = _result;
    }

    public HttpData(final String _url, final String _data, final Result _result) {
        Assert.isValidString(_url, "URL is invalid");
        this.url = _url;

        Assert.notNull(_data, "Data is null");
        this.data = new MemoryData(_data.getBytes());

        Assert.notNull(_result, "Result is null");
        this.result = _result;
    }

    public Data getData() {
        return this.data;
    }

    public String getUrl() {
        return this.url;
    }

    public Result getResult() {
        return this.result;
    }
}
