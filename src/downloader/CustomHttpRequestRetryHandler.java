package downloader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import util.Assert;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 30.08.2011
 */
public class CustomHttpRequestRetryHandler implements HttpRequestRetryHandler {

    private final int retryCount;

    private final Log log;

    public CustomHttpRequestRetryHandler(int retryCount) {
        Assert.greater(retryCount, 0, "");
        this.retryCount = retryCount;

        this.log = LogFactory.getLog(getClass());
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        Assert.notNull(exception);
        Assert.greater(executionCount, 0, "");
        Assert.notNull(context);

        this.log.debug(String.format("Execution [ %d ] of [ %d ] throws exception", executionCount, retryCount), exception);

        if (executionCount >= retryCount) {
            return false;
        }

        if (exception instanceof NoHttpResponseException) {
            return true;
        }

        if (exception instanceof SSLHandshakeException) {
            return false;
        }

        final HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);

        final boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);

        return idempotent;
    }
}