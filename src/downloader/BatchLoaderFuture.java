package downloader;

import html.HttpData;
import util.Assert;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author Igor Usenko
 *         Date: 29.06.2010
 */
public class BatchLoaderFuture {

    private final CountDownLatch latch;

    private Map<String, HttpData> result;

    public BatchLoaderFuture(final CountDownLatch _latch) {
        Assert.notNull(_latch, "Latch is null");
        this.latch = _latch;
    }

    public CountDownLatch getLatch() {
        return this.latch;
    }

    public Map<String, HttpData> getResult() {
        return this.result;
    }

    public void setResult(final Map<String, HttpData> _result) {
        Assert.notNull(_result, "Result is null");
        this.result = _result;
    }
}
