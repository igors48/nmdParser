package http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import util.Assert;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class SimpleHttpRequestHandler implements HttpRequestHandler {

    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 20000;
    private static final int RETRY_COUNT = 10;

    public static final String REFERER_HEADER_NAME = "Referer";
    public static final String ACCEPT_HEADER_NAME = "Accept";
    public static final String ACCEPT_REQUEST_HEADER_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    public static final String USER_AGENT_HEADER_NAME = "User-Agent";
    public static final String USER_AGENT_REQUEST_HEADER_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1";

    //TODO maybe standard names defined already???
    /*
    public static final String ACCEPT_CHARSET_HEADER_NAME = "Accept-Charset";
    public static final String ACCEPT_CHARSET_HEADER_VALUE = "windows-1251,utf-8;q=0.7,*;q=0.3";
    public static final String ACCEPT_LANGUAGE_HEADER_NAME = "Accept-Language";
    public static final String ACCEPT_LANGUAGE_HEADER_VALUE = "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";
    */
    public static final String ACCEPT_ENCODING_HEADER_NAME = "Accept-Encoding";
    public static final String ACCEPT_ENCODING_HEADER_VALUE = "gzip,deflate";

    public static final String CACHE_CONTROL_HEADER_NAME = "Cache-Control";
    public static final String CACHE_CONTROL_HEADER_VALUE = "max-age=2048";
    /*
    public static final String CONNECTION_HEADER_NAME = "Connection";
    public static final String CONNECTION_HEADER_VALUE = "keep-alive";
    public static final String PRAGMA_HEADER_NAME = "Pragma";
    public static final String PRAGMA_HEADER_VALUE = "no-cache";
    */


    private final DefaultHttpClient httpClient;
    private final HttpContext localContext;

    private final Log log;

    public SimpleHttpRequestHandler() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        ClientConnectionManager cm = new ThreadSafeClientConnManager(schemeRegistry);
        this.httpClient = new DefaultHttpClient(cm);

        final HttpRequestRetryHandler retryHandler = new CustomHttpRequestRetryHandler(RETRY_COUNT);
        httpClient.setHttpRequestRetryHandler(retryHandler);

        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);

        this.localContext = new BasicHttpContext();

        this.log = LogFactory.getLog(getClass());
    }

    public synchronized Callable<HttpGetRequest> get(final HttpGetRequest _request) {
        Assert.notNull(_request, "Request is null");

        return new HttpGetTask(this.httpClient, _request);
    }

    public void cancel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stop() {
        httpClient.getConnectionManager().shutdown();
        this.log.info("SimpleHttpRequestHandler stopped");
    }
}

