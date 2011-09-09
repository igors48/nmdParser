package work.comp.downloader;

import junit.framework.TestCase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.HttpClient;
import downloader.httpadapter.HttpAdapterThread;
import downloader.httpadapter.HttpAdapterThreadConfig;
import downloader.httpadapter.HttpAdapterThreadOwner;
import downloader.*;

/**
 * @author Igor Usenko
 *         Date: 28.09.2008
 */
public class HttpAdapterThreadTest extends TestCase implements Listener, HttpAdapterThreadOwner {

    public static final int SIZE = 232;
    public static final String ADDRESS = "http://192.168.1.1/main.html";
    private static final String WORK_CACHE = ".\\work\\cache\\";

    public HttpAdapterThreadTest(String s) {
        super(s);
    }

    public void testToMemory() throws InterruptedException {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        Request request = new Request(ADDRESS, Destination.MEMORY);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);
        HttpAdapterThreadConfig config = new HttpAdapterThreadConfig(WORK_CACHE, 5, 30000, 60000, 15000, this);

        HttpAdapterThread thread = new HttpAdapterThread(client, adapterRequest, config);
        thread.start();

        while (thread.isAlive()) {
            Thread.sleep(500);
        }

        assertTrue(thread.getData().size() == SIZE);
    }

    public void testToFile() throws InterruptedException {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        Request request = new Request(ADDRESS, Destination.FILE);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);
        HttpAdapterThreadConfig config = new HttpAdapterThreadConfig(WORK_CACHE, 5, 30000, 60000, 15000, this);

        HttpAdapterThread thread = new HttpAdapterThread(client, adapterRequest, config);
        thread.start();

        while (thread.isAlive()) {
            Thread.sleep(500);
        }

        assertTrue(thread.getData().size() == SIZE);
    }

    public void requestDone(long _id, Result _result, Data _data) {
    }

    public void cancellingFinished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean cancelling() {
        return false;
    }
}
