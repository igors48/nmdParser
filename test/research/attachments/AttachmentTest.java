package research.attachments;

import downloader.Data;
import downloader.Listener;
import downloader.Result;
import junit.framework.TestCase;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Igor Usenko
 *         Date: 03.01.2009
 */
public class AttachmentTest extends TestCase implements Listener {

    private static final String WORK_CACHE = ".\\work\\cache\\";
    private static final int THREAD_COUNT = 10;
    private static final int KEEP_ALIVE_TIME = 10;

    private static final String URL = "http://www.kharkovforum.com/login.php?do=login";
    private static final String URL_SEC = "http://www.kharkovforum.com/attachment.php?attachmentid=185155&d=1230930627";

    private static final Log log = LogFactory.getLog(Object.class);

    private Data data;

    public AttachmentTest(String s) {
        super(s);
    }

    public synchronized void testOne() {
/*        StandardDownloaderConfig config = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE);

        Downloader downloader = new SimpleDownloader(config);

        try {

            downloader.start();

            Request request = new Request(URL, Destination.FILE);
            RequestList requestList = new RequestList(this, request);

            downloader.download(requestList);
            wait();

            String image = DataUtil.getDataImage(this.data);
            System.out.println(image);
            
        } catch (Exception e) {
            log.error(e);
        } finally {

            try {
                downloader.stop();
            } catch (Exception e) {
                log.error(e);
            }
        }*/
    }

    public synchronized void requestDone(long _id, Result _result, Data _data) {
        this.data = _data;

        notifyAll();
    }

    public void cancellingFinished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testTwo() {


        HttpClient client = new HttpClient();

        // connect to a login page to retrieve session ID
        PostMethod method = new PostMethod(URL);

        // post auth information with it
        method.setParameter("vb_login_username", "igors48");
        method.setParameter("vb_login_password", "74191974");

        try {
            client.executeMethod(method);

            String redirectLocation = null;
            Header locationHeader = method.getResponseHeader("location");
            if (locationHeader != null) {
                redirectLocation = locationHeader.getValue();
            } else {
                // The response is invalid and did not provide the new location for
                // the resource. Report an error or possibly handle the response
                // like a 404 Not Found error.
                log.info(method.getResponseBodyAsString());
            }
            method.setURI(new URI(redirectLocation, true));
            client.executeMethod(method);

            // read the session info for the next call
            Header[] headers = method.getResponseHeaders();

            Thread.sleep(2000);

            // connect to a page you're interested...
            //PostMethod getMethod = new PostMethod(URL_SEC);
            PostMethod getMethod = new PostMethod(URL_SEC);

            // ...using the session ID retrieved before
            for (Header header : headers) {
                getMethod.setRequestHeader(header);
            }
            client.executeMethod(getMethod);

            // log the page source
            log.info(getMethod.getResponseBodyAsString());
        } catch (Exception e) {
            log.error(e);
        }
    }
}

