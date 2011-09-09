package work.comp.downloader;

import junit.framework.TestCase;
import downloader.hostadapter.HostAdapterThread;
import downloader.*;
import work.testutil.CompTestsUtils;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class HostAdapterThreadTest extends TestCase implements Listener {
    private static final String ADDRESS = CompTestsUtils.getCompTestsRoot() + "/DownloaderAndHostAdapterTest/"  + "001.txt";

    public HostAdapterThreadTest(String s) {
        super(s);
    }

    public void testMemory() throws InterruptedException, Data.DataException {
        Request request = new Request(ADDRESS, Destination.MEMORY);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);
        HostAdapterThread thread = new HostAdapterThread(adapterRequest);

        thread.start();

        while (thread.isAlive()) {
            Thread.sleep(500);
        }

        assertEquals(4, thread.getData().size());
        assertEquals("1", new String(thread.getData().getData(0, 0)));
        assertEquals("2", new String(thread.getData().getData(1, 1)));
        assertEquals("3", new String(thread.getData().getData(2, 2)));
        assertEquals("4", new String(thread.getData().getData(3, 3)));
        assertEquals("1234", new String(thread.getData().getData(0, 3)));
    }

    public void testFile() throws InterruptedException, Data.DataException {
        Request request = new Request(ADDRESS, Destination.MEMORY);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);
        HostAdapterThread thread = new HostAdapterThread(adapterRequest);

        thread.start();

        while (thread.isAlive()) {
            Thread.sleep(500);
        }

        assertEquals(4, thread.getData().size());
        assertEquals("1", new String(thread.getData().getData(0, 0)));
        assertEquals("2", new String(thread.getData().getData(1, 1)));
        assertEquals("3", new String(thread.getData().getData(2, 2)));
        assertEquals("4", new String(thread.getData().getData(3, 3)));
        assertEquals("1234", new String(thread.getData().getData(0, 3)));
    }
    
    public void requestDone(long _id, Result _result, Data _data) {
    }

    public void cancellingFinished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
