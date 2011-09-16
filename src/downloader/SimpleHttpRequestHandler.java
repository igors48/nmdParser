package downloader;

import downloader.data.MemoryData;
import html.HttpData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import util.Assert;
import util.IOTools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Author: Igor Usenko ( Igor.Usenko@teamodc.com )
 * Date: 16.09.2011
 */
public class SimpleHttpRequestHandler implements HttpRequestHandler {

    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 120000;
    private static final int RETRY_COUNT = 10;

    private static final String REFERER_HEADER_NAME = "Referer";
    private static final String ACCEPT_HEADER_NAME = "Accept";
    private static final String ACCEPT_REQUEST_HEADER_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String USER_AGENT_REQUEST_HEADER_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.220 Safari/535.1";

    //TODO maybe standard names defined already???
    /*
    private static final String ACCEPT_CHARSET_HEADER_NAME = "Accept-Charset";
    private static final String ACCEPT_CHARSET_HEADER_VALUE = "windows-1251,utf-8;q=0.7,*;q=0.3";
    private static final String ACCEPT_LANGUAGE_HEADER_NAME = "Accept-Language";
    private static final String ACCEPT_LANGUAGE_HEADER_VALUE = "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";
    */
    private static final String ACCEPT_ENCODING_HEADER_NAME = "Accept-Encoding";
    private static final String ACCEPT_ENCODING_HEADER_VALUE = "gzip,deflate";
    /*
    private static final String CACHE_CONTROL_HEADER_NAME = "Cache-Control";
    private static final String CACHE_CONTROL_HEADER_VALUE = "no-cache";
    private static final String CONNECTION_HEADER_NAME = "Connection";
    private static final String CONNECTION_HEADER_VALUE = "keep-alive";
    private static final String PRAGMA_HEADER_NAME = "Pragma";
    private static final String PRAGMA_HEADER_VALUE = "no-cache";
    */

    private final Log log;

    public SimpleHttpRequestHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public void get(final HttpGetRequest _request) {
        Assert.notNull(_request, "Request is null");
        final DefaultHttpClient httpClient = new ContentEncodingHttpClient();

        final HttpRequestRetryHandler retryHandler = new CustomHttpRequestRetryHandler(RETRY_COUNT);
        httpClient.setHttpRequestRetryHandler(retryHandler);

        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);

        try {
            final String escapedUrl = _request.getUrl() + _request.getRequest();
            final String escapedReferer = _request.getReferer();

            final HttpGet httpGet = new HttpGet(escapedUrl);
            httpGet.setHeader(REFERER_HEADER_NAME, escapedReferer);
            httpGet.setHeader(ACCEPT_HEADER_NAME, ACCEPT_REQUEST_HEADER_VALUE);
            httpGet.setHeader(USER_AGENT_HEADER_NAME, USER_AGENT_REQUEST_HEADER_VALUE);

            /*
            httpGet.setHeader(ACCEPT_LANGUAGE_HEADER_NAME, ACCEPT_LANGUAGE_HEADER_VALUE);
            httpGet.setHeader(ACCEPT_CHARSET_HEADER_NAME, ACCEPT_CHARSET_HEADER_VALUE);
            */
            httpGet.setHeader(ACCEPT_ENCODING_HEADER_NAME, ACCEPT_ENCODING_HEADER_VALUE);
            /*
            httpGet.setHeader(CACHE_CONTROL_HEADER_NAME, CACHE_CONTROL_HEADER_VALUE);
            httpGet.setHeader(CONNECTION_HEADER_NAME, CONNECTION_HEADER_VALUE);
            httpGet.setHeader(PRAGMA_HEADER_NAME, PRAGMA_HEADER_VALUE);
            */

            final HttpResponse response = httpClient.execute(httpGet);
            final HttpEntity entity = response.getEntity();

            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOTools.copy(entity.getContent(), output);

            //TODO charset
            //TODO response URL
            final Data data = new MemoryData(output.toByteArray());
            final HttpData httpData = new HttpData(httpGet.getURI().toURL().toString(), data, Result.OK);

            _request.setResult(httpData);
        } catch (ClientProtocolException e) {
            this.log.error(String.format("Error in GET request from url [ %s ] referer [ %s ]", _request.getUrl(), _request.getReferer()), e);
            _request.setResult(HttpData.ERROR_DATA);
        } catch (IOException e) {
            this.log.error(String.format("Error in GET request from url [ %s ] referer [ %s ]", _request.getUrl(), _request.getReferer()), e);
            _request.setResult(HttpData.ERROR_DATA);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void cancel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}

