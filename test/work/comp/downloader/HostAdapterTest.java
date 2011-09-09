package work.comp.downloader;

import junit.framework.TestCase;
import downloader.hostadapter.HostAdapter;
import downloader.hostadapter.HostAdapterConfig;
import downloader.*;

import java.util.concurrent.TimeUnit;

import work.testutil.CompTestsUtils;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class HostAdapterTest extends TestCase implements Listener {

    private static int COUNT = 100;

    private long[] id = new long[COUNT];
    private Result[] result = new Result[COUNT];
    private Data[] data = new Data[COUNT];
    private int index;

    private static final String ADDRESS01 = CompTestsUtils.getCompTestsRoot() + "/DownloaderAndHostAdapterTest/"  + "001.txt";
    private static final String ADDRESS02 = CompTestsUtils.getCompTestsRoot() + "/DownloaderAndHostAdapterTest/"  + "002.txt";

    public HostAdapterTest(String s) {
        super(s);
    }

    public void testOneRequest() throws Adapter.AdapterException, InterruptedException, Data.DataException {
        this.index = 0;

        HostAdapterConfig config = new HostAdapterConfig(10, 10, 1);
        HostAdapter adapter = new HostAdapter(this, config);
        Request request = new Request(ADDRESS01, Destination.MEMORY);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);

        adapter.start();

        adapter.download(adapterRequest);

        while (adapter.getActiveCount() != 0){
            Thread.sleep(500);    
        }

        adapter.shutdown();
        adapter.awaitTermination(10, TimeUnit.SECONDS);
        
        adapter.stop();

        assertEquals(48, this.id[0]);
        assertEquals("1", new String(this.data[0].getData(0, 0)));
        assertEquals("2", new String(this.data[0].getData(1, 1)));
        assertEquals("3", new String(this.data[0].getData(2, 2)));
        assertEquals("4", new String(this.data[0].getData(3, 3)));
        assertEquals("1234", new String(this.data[0].getData(0, 3)));

    }
    
    public void testThreeRequests() throws Adapter.AdapterException, InterruptedException, Data.DataException {
        this.index = 0;

        HostAdapterConfig config = new HostAdapterConfig(10, 10, 1);
        HostAdapter adapter = new HostAdapter(this, config);
        Request request = new Request(ADDRESS01, Destination.MEMORY);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);

        adapter.start();

        adapter.download(adapterRequest);
        adapter.download(adapterRequest);
        adapter.download(adapterRequest);

        while (adapter.getActiveCount() != 0){
            Thread.sleep(500);
        }

        adapter.shutdown();
        adapter.awaitTermination(10, TimeUnit.SECONDS);

        adapter.stop();

        assertEquals(48, this.id[0]);
        assertEquals(3, this.index);
        assertEquals("1", new String(this.data[0].getData(0, 0)));
        assertEquals("2", new String(this.data[0].getData(1, 1)));
        assertEquals("3", new String(this.data[0].getData(2, 2)));
        assertEquals("4", new String(this.data[0].getData(3, 3)));
        assertEquals("1234", new String(this.data[0].getData(0, 3)));

        assertEquals("1", new String(this.data[1].getData(0, 0)));
        assertEquals("2", new String(this.data[1].getData(1, 1)));
        assertEquals("3", new String(this.data[1].getData(2, 2)));
        assertEquals("4", new String(this.data[1].getData(3, 3)));
        assertEquals("1234", new String(this.data[1].getData(0, 3)));

        assertEquals("1", new String(this.data[2].getData(0, 0)));
        assertEquals("2", new String(this.data[2].getData(1, 1)));
        assertEquals("3", new String(this.data[2].getData(2, 2)));
        assertEquals("4", new String(this.data[2].getData(3, 3)));
        assertEquals("1234", new String(this.data[2].getData(0, 3)));

    }

    public void testTwoDiffRequests() throws Adapter.AdapterException, InterruptedException, Data.DataException {
        this.index = 0;

        HostAdapterConfig config = new HostAdapterConfig(10, 10, 1);
        HostAdapter adapter = new HostAdapter(this, config);
        Request request01 = new Request(ADDRESS01, Destination.MEMORY);
        AdapterRequest adapterRequest01 = new AdapterRequest(48, request01);
        Request request02 = new Request(ADDRESS02, Destination.FILE);
        AdapterRequest adapterRequest02 = new AdapterRequest(49, request02);

        adapter.start();

        adapter.download(adapterRequest01);
        adapter.download(adapterRequest02);

        while (adapter.getActiveCount() != 0){
            Thread.sleep(500);
        }

        adapter.shutdown();
        adapter.awaitTermination(10, TimeUnit.SECONDS);

        adapter.stop();

        assertTrue((48 == this.id[0]) || (49 == this.id[0]));
        assertTrue((48 == this.id[1]) || (49 ==this.id[1]));
        assertEquals(2, this.index);
    }

    public synchronized void requestDone(long _id, Result _result, Data _data) {
        this.id[this.index] = _id;
        this.result[this.index] = _result;
        this.data[this.index] = _data;
        ++this.index;
    }

    public void cancellingFinished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
