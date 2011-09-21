package http;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import html.HttpData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static util.CollectionUtils.newHashMap;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class StandardBatchLoader implements BatchLoader {

    private static final String PROCESS_LOADING_DATA = "process.loading.data";

    private final HttpRequestHandler requestHandler;

    private final CompletionService<HttpGetRequest> completionService;

    private final Log log;

    public StandardBatchLoader(final HttpRequestHandler _requestHandler) {
        Assert.notNull(_requestHandler, "Request handler is null");
        this.requestHandler = _requestHandler;

        this.completionService = new ExecutorCompletionService<HttpGetRequest>(Executors.newFixedThreadPool(16, new DaemonThreadFactory("daemon")));

        this.log = LogFactory.getLog(getClass());
    }

    public Map<String, HttpData> loadUrls(final List<String> _urls, final long _pauseBetweenRequests, final Controller _controller) {
        Assert.notNull(_urls, "Url list is null");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");
        Assert.notNull(_controller, "Controller is null");

        final Map<String, HttpData> result = newHashMap();

        try {
            _controller.onProgress(new SingleProcessInfo(PROCESS_LOADING_DATA, 0, _urls.size()));

            for (final String url : _urls) {
                final Callable<HttpGetRequest> requestTask = createTask(url, "");

                this.completionService.submit(requestTask);

                Thread.sleep(_pauseBetweenRequests);
            }

            int count = 0;

            while (count != _urls.size()) {
                // TODO check for cancel
                final HttpGetRequest request = this.completionService.take().get();

                result.put(request.getUrl(), request.getResult());

                _controller.onProgress(new SingleProcessInfo(PROCESS_LOADING_DATA, count++, _urls.size()));
            }

        } catch (InterruptedException e) {
            this.log.error(String.format("Error loading from urls list"), e);
        } catch (ExecutionException e) {
            this.log.error(String.format("Error loading from urls list"), e);
        }

        return result;
    }

    public HttpData loadUrlWithReferer(final String _url, final String _referer) {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_referer, "Referer is null");

        try {
            final Callable<HttpGetRequest> requestTask = createTask(_url, _referer);

            return requestTask.call().getResult();
        } catch (Exception e) {
            this.log.error(String.format("Error loading from [ %s ]", _url), e);

            return HttpData.EMPTY_DATA;
        }
    }

    public boolean cancelled() {
        return false;
    }

    public void cancel() {
        // empty
    }

    private Callable<HttpGetRequest> createTask(String _url, String _referer) {
        final HttpGetRequest request = new HttpGetRequest(_url, "", _referer);

        return this.requestHandler.get(request);
    }

}
