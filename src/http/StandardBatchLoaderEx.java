package http;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import html.HttpData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import static util.CollectionUtils.newHashMap;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class StandardBatchLoaderEx implements BatchLoader {

    private static final String PROCESS_LOADING_DATA = "process.loading.data";

    private final HttpRequestHandler requestHandler;
    private final Controller controller;

    final CompletionService<HttpGetRequest> completionService;

    private final Log log;

    public StandardBatchLoaderEx(final HttpRequestHandler _requestHandler, final Controller _controller) {
        Assert.notNull(_requestHandler, "Request handler is null");
        this.requestHandler = _requestHandler;

        Assert.notNull(_controller, "Controller is null");
        this.controller = _controller;

        this.completionService = new ExecutorCompletionService<HttpGetRequest>(Executors.newFixedThreadPool(16, new DaemonThreadFactory("daemon")));

        this.log = LogFactory.getLog(getClass());
    }

    public Map<String, HttpData> loadUrls(final List<String> _urls, final long _pauseBetweenRequests) {
        Assert.notNull(_urls, "Url list is null");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        final Map<String, HttpData> result = newHashMap();

        try {
            this.controller.onProgress(new SingleProcessInfo(PROCESS_LOADING_DATA, 0, _urls.size()));

            for (final String url : _urls) {
                final HttpGetTask task = createTask(url, "");

                this.completionService.submit(task);
            }

            int count = 0;

            while (count != _urls.size()) {
                // TODO check for cancel
                final HttpGetRequest request = this.completionService.take().get();

                result.put(request.getUrl(), request.getResult());

                this.controller.onProgress(new SingleProcessInfo(PROCESS_LOADING_DATA, count++, _urls.size()));
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
            final HttpGetTask task = createTask(_url, _referer);

            return task.call().getResult();
        } catch (Exception e) {
            this.log.error(String.format("Error loading from [ %s ]", _url), e);

            return HttpData.EMPTY_DATA;
        }
    }

    public boolean cancelled() {
        return false;
    }

    private HttpGetTask createTask(String _url, String _referer) {
        final HttpGetRequest request = new HttpGetRequest(_url, "", _referer);

        return new HttpGetTask(request, this.requestHandler);
    }

}
