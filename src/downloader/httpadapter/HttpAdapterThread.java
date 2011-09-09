package downloader.httpadapter;

import downloader.AdapterRequest;
import downloader.Data;
import downloader.Result;
import downloader.data.DataFile;
import downloader.data.EmptyData;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.IOTools;
import util.TimeoutTools;

import java.io.*;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class HttpAdapterThread extends Thread {

    private final Log log;

    private final AdapterRequest request;
    private final HttpClient client;
    private final HttpAdapterThreadConfig config;

    private Data data;
    private Result result;

    private static final String TEMP_FILE_PRF = "tmp";
    private static final String TEMP_FILE_SFX = "tmp";
    private static final String CONTENT_ENCODING_TOKEN = "Content-Encoding";
    private static final String GZIP_TOKEN = "gzip";
    private static final String CLOSING_BRACE = " ].";
    private static final String HTTP_ADAPTER_THREAD_NAME = "HttpAdapterRequestThread";

    public HttpAdapterThread(final HttpClient _client, final AdapterRequest _request, final HttpAdapterThreadConfig _config) {
        super(HTTP_ADAPTER_THREAD_NAME);

        Assert.notNull(_client);
        Assert.notNull(_request);
        Assert.notNull(_config);

        this.client = _client;
        this.request = _request;
        this.config = _config;

        this.data = new EmptyData();
        this.result = Result.OK;

        this.log = LogFactory.getLog(getClass());

        this.log.debug("Created HttpAdapterThread for " + this.request.getDescription());
    }

    public Data getData() {
        return this.data;
    }

    public Result getResult() {
        return this.result;
    }

    public long getRequestId() {
        return this.request.getId();
    }

    public String getRequestDescription() {
        return this.request.getDescription();
    }

    public AdapterRequest getRequest() {
        return this.request;
    }

    //todo перептсать это как-то надо
    public void run() {

        if (isInterrupted() || this.config.getOwner().cancelling()) {
            this.result = Result.CANCEL;
            this.log.debug("HttpAdapterThread cancelled for [ " + this.request.getDescription() + CLOSING_BRACE);

            return;
        }

        this.log.debug("HttpAdapterThread started for [ " + this.request.getDescription() + CLOSING_BRACE);

        int tryCount = this.config.getMaxTryCount();

        do {

            GetMethod method = null;

            try {
                method = createMethod(tryCount);
                this.log.debug("GetMethod created for [ " + this.request.getDescription() + " ]. SO timeout is [ " + method.getParams().getSoTimeout() + " ] Try [ " + tryCount + " ] of [ " + this.config.getMaxTryCount() + CLOSING_BRACE);

                executeMethod(method);
                int statusCode = method.getStatusCode();
                this.log.debug(MessageFormat.format("HTTP Response is [ {0} ]", statusCode));

                /*if (statusCode == 200) {*/
                resolveMethod(method);

                /*this.log.debug("HttpAdapterThread ended for [ " + this.request.getDescription() + " ] Data size [ " + this.data.size() + CLOSING_BRACE);*/
                this.log.debug(MessageFormat.format("HttpAdapterThread ended for [ {0} ] Data size [ {1} ] Status code [ {2} ]", this.request.getDescription(), this.data.size(), statusCode));

                if (this.data.size() == 0) {
                    this.log.debug("Readed data size is 0. Try left [ " + (tryCount - 1) + CLOSING_BRACE);
                    this.result = Result.ERROR;

                    int timeout = 1500/*TimeoutTools.getTimeout(this.config.getMinTimeout(), this.config.getErrorTimeout(), this.config.getMaxTryCount() - tryCount, this.config.getMaxTryCount())*/;
                    this.log.debug(MessageFormat.format("Calculated no data timeout is [ {0} ] milliseconds", timeout));

                    sleep(timeout);
                } else {
                    this.result = Result.OK;
                }
                /*} else {
                    this.result = Result.ERROR;

                    int timeout = 10000;
                    this.log.debug(MessageFormat.format("Calculated error status timeout is [ {0} ] milliseconds", timeout));

                    sleep(timeout);
                } */
            } catch (InterruptedException e) {
                this.result = Result.CANCEL;

                this.log.error("Interrupt while downloading [ " + this.request.getDescription() + " ]. Try left [ " + (tryCount - 1) + CLOSING_BRACE, e);
            } catch (Throwable e) {
                this.result = Result.ERROR;

                this.log.error("Error while downloading [ " + this.request.getDescription() + " ]. Try left [ " + (tryCount - 1) + CLOSING_BRACE, e);
            } finally {
                releaseConnection(method);
            }

            --tryCount;

            if (isInterrupted() || this.config.getOwner().cancelling()) {
                this.result = Result.CANCEL;
            }

            //TODO если результат ошибка - нотифицировать владельца 
        } while (tryCount > 0 && this.result == Result.ERROR);

    }

    private void executeMethod(GetMethod method) throws IOException {
        this.client.executeMethod(method);
        this.log.debug("After method execute [ " + this.request.getDescription() + CLOSING_BRACE);

        // feed-proxy посвящается
        this.log.debug("Response from URI [ " + method.getURI() + " ]");
        this.request.setResponseUrl(method.getURI().toString());
    }

    private GetMethod createMethod(final int _tryCount) throws URIException {
        this.log.debug("Original address [ " + this.request.getAddress() + " ]");

        final String address = removeSpaces(this.request.getAddress().contains("%") ? new URI(this.request.getAddress(), false).toString() : this.request.getAddress());

        this.log.debug("Escaped address [ " + address + " ]");

        final int timeout = TimeoutTools.getTimeout(this.config.getMinTimeout(), this.config.getSocketTimeout(), this.config.getMaxTryCount() - _tryCount, this.config.getMaxTryCount());
        this.log.debug(MessageFormat.format("Calculated SO timeout is [ {0} ] milliseconds", timeout));

        final GetMethod method = new GetMethod(address);
        method.getParams().setSoTimeout(timeout);

        method.setRequestHeader("Referer", this.request.getReferer());
        this.log.debug(MessageFormat.format("Referer set to [ {0} ]", this.request.getReferer()));

        //method.setRequestHeader("Host", "rusrep.ru");
        //method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13");
        method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        //method.setRequestHeader("Accept-Language", "ru");
        //method.setRequestHeader("Accept-Encoding", "gzip,deflate");
        //method.setRequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        //method.setRequestHeader("Keep-Alive", "115");
        //method.setRequestHeader("Connection", "keep-alive");

        method.setDoAuthentication(true);

        return method;
    }

    private void resolveMethod(final GetMethod _method) throws IOException {
        InputStream inputStream = null;

        try {
            inputStream = _method.getResponseBodyAsStream();

            if (inputStream == null) {
                this.log.debug(MessageFormat.format("Null data received from [ {0} ]", _method.getPath()));

                this.data = new EmptyData();
            } else {
                String charset = _method.getResponseCharSet();
                boolean gZipped = isGZipped(_method);
                this.log.debug("Response charset [ " + charset + " ] GZipped : [ " + gZipped + CLOSING_BRACE);

                this.data = writeToFile(inputStream, charset, gZipped);
            }
        } finally {
            IOTools.close(inputStream);
        }

    }

    private void releaseConnection(final GetMethod _method) {

        if (_method != null) {
            _method.releaseConnection();
        }
    }

    /**
     * Удаляет пробелы из адреса перед точкой. Посвящено баге на инопрессе от 28/01/09
     * http://inopressa.ru/article/28Jan2009/independent/cello .html
     *
     * @param _address - адрес
     * @return - исправленный адрес
     */
    private String removeSpaces(final String _address) {
        return _address.replaceAll(" .", ".");
    }

    private boolean isGZipped(final GetMethod _method) {
        boolean result = false;

        Header[] headers = _method.getResponseHeaders(CONTENT_ENCODING_TOKEN);

        for (Header header : headers) {

            if (header.getValue().equalsIgnoreCase(GZIP_TOKEN)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private Data writeToFile(final InputStream _inputStream, final String _charset, final boolean _gZipped) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            File tempFile = File.createTempFile(TEMP_FILE_PRF, TEMP_FILE_SFX, new File(this.config.getWorkDirectory()));
            outputStream = new FileOutputStream(tempFile);

            inputStream = _gZipped ? new GZIPInputStream(_inputStream) : _inputStream;

            IOTools.copy(inputStream, outputStream);

            return new DataFile(tempFile.getAbsolutePath(), _charset);
        } finally {
            IOTools.close(outputStream);

            if (_gZipped) {
                IOTools.close(inputStream);
            }
        }
    }
}
