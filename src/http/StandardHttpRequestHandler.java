package http;

import http.banned.BannedList;
import http.cache.InMemoryCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import util.Assert;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class StandardHttpRequestHandler implements HttpRequestHandler {

    public static final String REFERER_HEADER_NAME = "Referer";
    public static final String ACCEPT_HEADER_NAME = "Accept";
    public static final String ACCEPT_REQUEST_HEADER_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    public static final String USER_AGENT_HEADER_NAME = "User-Agent";
    public static final String USER_AGENT_REQUEST_HEADER_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1";

    public static final String ACCEPT_CHARSET_HEADER_NAME = "Accept-Charset";
    public static final String ACCEPT_CHARSET_HEADER_VALUE = "windows-1251,utf-8;q=0.7,*;q=0.3";

    /*
    public static final String ACCEPT_LANGUAGE_HEADER_NAME = "Accept-Language";
    public static final String ACCEPT_LANGUAGE_HEADER_VALUE = "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";
    */

    public static final String ACCEPT_ENCODING_HEADER_NAME = "Accept-Encoding";
    public static final String ACCEPT_ENCODING_HEADER_VALUE = "gzip,deflate";

    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int SOCKET_TIMEOUT = 20000;
    private static final int RETRY_COUNT = 10;

    private static final String HTTP_SCHEME = "http";
    private static final int HTTP_DEFAULT_PORT = 80;

    private static final int BANNED_LIST_TRESHOLD = 5;
    private static final int BANNED_LIST_LIMIT = 50;

    private final DefaultHttpClient httpClient;
    private final InMemoryCache cache;
    private final BannedList bannedList;

    private final Log log;

    public StandardHttpRequestHandler() {
        final SchemeRegistry schemeRegistry = new SchemeRegistry();
        final Scheme scheme = new Scheme(HTTP_SCHEME, HTTP_DEFAULT_PORT, PlainSocketFactory.getSocketFactory());
        schemeRegistry.register(scheme);

        final ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(schemeRegistry);

        final HttpParams httpParams = new BasicHttpParams();

        this.httpClient = new ContentEncodingHttpClient(connectionManager, httpParams);

        final HttpRequestRetryHandler retryHandler = new CustomHttpRequestRetryHandler(RETRY_COUNT);
        httpClient.setHttpRequestRetryHandler(retryHandler);

        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);

        this.cache = new InMemoryCache();

        this.bannedList = new BannedList(BANNED_LIST_TRESHOLD, BANNED_LIST_LIMIT);

        this.log = LogFactory.getLog(getClass());
    }

    public synchronized Callable<HttpRequest> get(final HttpRequest _request) {
        Assert.notNull(_request, "Request is null");

        return new HttpCacheableGetTask(this.httpClient, this.cache, this.bannedList, _request);
    }

    public synchronized Callable<HttpRequest> post(final HttpRequest _request) {
        Assert.notNull(_request, "Request is null");

        return new HttpPostTask(this.httpClient, this.bannedList, _request);
    }

    public void stop() {
        this.httpClient.getConnectionManager().shutdown();
        this.log.info("StandardHttpRequestHandler stopped");
    }

}

