package http;

import http.banned.BannedList;
import http.cache.InMemoryCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import util.Assert;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class StandardHttpRequestHandler implements HttpRequestHandler {

    private static final String HTTP_SCHEME = "http";
    private static final int HTTP_DEFAULT_PORT = 80;

    private static final String HTTPS_SCHEME = "https";
    private static final int HTTPS_DEFAULT_PORT = 443;

    private final String userAgent;
    private final DefaultHttpClient httpClient;
    private final InMemoryCache cache;
    private final BannedList bannedList;

    private final Log log;

    public StandardHttpRequestHandler(final StandardHttpRequestHandlerContext _context) {
        Assert.notNull(_context, "Context is null");

        this.userAgent = _context.getUseragent();

        final ClientConnectionManager connectionManager = createConnectionManager();
        final HttpParams httpParams = new BasicHttpParams();
        this.httpClient = new ContentEncodingHttpClient(connectionManager, httpParams);

        final HttpRequestRetryHandler retryHandler = new CustomHttpRequestRetryHandler(_context.getRetryCount());
        this.httpClient.setHttpRequestRetryHandler(retryHandler);

        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, _context.getConnectionTimeout());
        HttpConnectionParams.setSoTimeout(params, _context.getSocketTimeout());

        if (_context.isProxyUse()) {
            this.httpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(_context.getProxyHost(), _context.getProxyPort()),
                    new UsernamePasswordCredentials(_context.getProxyUser(), _context.getProxyPassword()));

            HttpHost proxy = new HttpHost(_context.getProxyHost(), _context.getProxyPort());

            this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        this.cache = new InMemoryCache();

        this.bannedList = new BannedList(_context.getBannedListTreshold(), _context.getBannedListLimit());

        this.log = LogFactory.getLog(getClass());
    }

    public synchronized Callable<HttpRequest> get(final HttpRequest _request) {
        Assert.notNull(_request, "Request is null");

        return new HttpCacheableGetTask(this.httpClient, this.cache, this.bannedList, _request, this.userAgent);
    }

    public Callable<HttpRequest> getSecured(final HttpSecureRequest _request) {
        Assert.notNull(_request, "Request is null");

        return new HttpSecureGetTask(this.httpClient, this.bannedList, _request, this.userAgent);
    }

    public Callable<HttpRequest> postSecured(HttpSecureRequest _request) {
        Assert.notNull(_request, "Request is null");

        return new HttpSecurePostTask(this.httpClient, this.bannedList, _request, this.userAgent);
    }

    public void stop() {
        this.httpClient.getConnectionManager().shutdown();
        this.log.info("StandardHttpRequestHandler stopped");
    }

    private ClientConnectionManager createConnectionManager() {
        final SchemeRegistry schemeRegistry = new SchemeRegistry();

        registerHttpScheme(schemeRegistry);
        registerHttpsScheme(schemeRegistry);

        return new ThreadSafeClientConnManager(schemeRegistry);
    }

    private void registerHttpScheme(final SchemeRegistry _schemeRegistry) {
        final Scheme httpScheme = new Scheme(HTTP_SCHEME, HTTP_DEFAULT_PORT, PlainSocketFactory.getSocketFactory());

        _schemeRegistry.register(httpScheme);
    }

    private void registerHttpsScheme(final SchemeRegistry _schemeRegistry) {

        try {
            final SSLSocketFactory sslSocketFactory = new SSLSocketFactory(new TrustStrategy() {

                public boolean isTrusted(final X509Certificate[] _chain, final String _authType) throws CertificateException {
                    return true;
                }
            });

            Scheme httpsScheme = new Scheme(HTTPS_SCHEME, HTTPS_DEFAULT_PORT, sslSocketFactory);

            _schemeRegistry.register(httpsScheme);
        } catch (Exception e) {
            this.log.error("Error registering HTTPS scheme", e);
        }
    }

}

