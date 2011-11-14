package http;

import html.HttpData;
import http.banned.BannedList;
import http.data.MemoryData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import util.Assert;

import java.io.IOException;

import static http.HttpTools.getHostFromMethod;
import static http.HttpTools.getUrlWithEscapedRequest;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 14.11.2011
 */
public class HttpPostTask extends AbstractHttpRequestTask {

    private final HttpClient httpClient;
    private final BannedList bannedList;
    private final HttpRequest request;

    private final HttpContext context;

    private final Log log;

    public HttpPostTask(final HttpClient _httpClient, final BannedList _bannedList, final HttpRequest _request) {
        Assert.notNull(_httpClient, "Http client is null");
        this.httpClient = _httpClient;

        Assert.notNull(_bannedList, "Banned list is null");
        this.bannedList = _bannedList;

        Assert.notNull(_request, "Request is null");
        this.request = _request;

        this.context = new BasicHttpContext();

        this.log = LogFactory.getLog(getClass());
    }

    public HttpRequest call() throws Exception {
        final String escapedUrl = getUrlWithEscapedRequest(this.request.getUrl(), this.request.getRequest());
        final HttpPost httpPost = createMethod(escapedUrl);

        final String targetHost = getHostFromMethod(httpPost);

        try {
            final boolean banned = this.bannedList.isBanned(targetHost);

            if (banned) {
                this.log.info(String.format("Host [ %s ] is banned", targetHost));

                this.request.setResult(HttpData.EMPTY_DATA);
            } else {
                this.log.debug(String.format("POST request to [ %s ]", escapedUrl));

                getResponse(httpPost);
            }
        } catch (Exception e) {
            httpPost.abort();

            this.bannedList.complain(targetHost);

            this.request.setResult(HttpData.ERROR_DATA);

            this.log.error(String.format("Error in POST request to [ %s ]", escapedUrl), e);
        }

        return this.request;
    }

    private void getResponse(final HttpPost _httpPost) throws IOException {
        final HttpResponse response = this.httpClient.execute(_httpPost, this.context);
        final HttpEntity entity = response.getEntity();

        if (entity == null) {
            this.request.setResult(HttpData.EMPTY_DATA);
        } else {
            handleEntity(entity, response);
        }

        EntityUtils.consume(entity);
    }

    private void handleEntity(final HttpEntity _entity, final HttpResponse _response) throws IOException {
        final Header[] headers = _response.getHeaders(CONTENT_TYPE_HEADER_NAME);
        final String charset = HttpTools.getCharset(headers);

        final Data data = charset.isEmpty() ? new MemoryData(EntityUtils.toByteArray(_entity)) : new MemoryData(EntityUtils.toByteArray(_entity), charset);

        final String responseUrl = getResponseUrl();
        final HttpData httpData = new HttpData(responseUrl, data, Result.OK);

        this.request.setResult(httpData);
    }

    //TODO method factory?

    private HttpPost createMethod(final String _url) {
        final HttpPost httpPost = new HttpPost(_url);

        httpPost.setHeader(StandardHttpRequestHandler.REFERER_HEADER_NAME, _url);

        httpPost.setHeader(StandardHttpRequestHandler.ACCEPT_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_REQUEST_HEADER_VALUE);
        httpPost.setHeader(StandardHttpRequestHandler.USER_AGENT_HEADER_NAME, StandardHttpRequestHandler.USER_AGENT_REQUEST_HEADER_VALUE);
        httpPost.setHeader(StandardHttpRequestHandler.ACCEPT_CHARSET_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_CHARSET_HEADER_VALUE);
        httpPost.setHeader(StandardHttpRequestHandler.ACCEPT_ENCODING_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_ENCODING_HEADER_VALUE);

        return httpPost;
    }

}
