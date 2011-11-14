package http;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 14.11.2011
 */
public abstract class AbstractHttpRequestTask implements Callable<HttpRequest> {

    protected static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";

    protected final HttpContext context;

    public AbstractHttpRequestTask() {
        this.context = new BasicHttpContext();
    }

    protected String getResponseUrl() {
        final HttpUriRequest currentRequest = (HttpUriRequest) this.context.getAttribute(ExecutionContext.HTTP_REQUEST);
        final HttpHost currentHost = (HttpHost) this.context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

        return currentHost.toURI() + currentRequest.getURI();
    }
    
}
