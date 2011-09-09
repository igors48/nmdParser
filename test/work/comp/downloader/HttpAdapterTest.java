package work.comp.downloader;

import junit.framework.TestCase;
import downloader.httpadapter.HttpAdapterConfig;
import downloader.httpadapter.HttpAdapter;
import downloader.*;

import timeservice.StandardTimeService;

/**
 * @author Igor Usenko
 *         Date: 28.09.2008
 */
public class HttpAdapterTest extends TestCase implements Listener {

    private static int COUNT = 100;
    public static final int SIZE = 232;
    private static final String WORK_CACHE = ".\\work\\cache\\";
    private static final String ADDRESS01 = "http://192.168.1.1/main.html";
    private static final String ADDRESS02 = "http://forum.ixbt.com/topic.cgi?id=47:1479-47";
    private static final String ADDRESS03 = "http://www.roombareview.com/chat/index.php";
    /*private static final String ADDRESS04 = "http://forums.irobot.com/";*/
    private static final String ADDRESS04 = "http://google.com/";


    private long[] id = new long[COUNT];
    private Result[] result = new Result[COUNT];
    private Data[] data = new Data[COUNT];
    private int index;

    public HttpAdapterTest(String s) {
        super(s);
    }

    public void testOneMemory() throws Adapter.AdapterException, InterruptedException {
        this.index = 0;

        HttpAdapterConfig config = new HttpAdapterConfig(10, 10, 10, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
                        5,
                        500,
                        64,
                        64,
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)",
                        false,
                        "",
                        0,
                        "",
                        "",
                        5,
                        30000,
                        60000,
                        15000);
        HttpAdapter adapter = new HttpAdapter(this, config);
        Request request = new Request(ADDRESS01, Destination.MEMORY);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);

        adapter.start();
        adapter.download(adapterRequest);

        while (adapter.getActiveCount() != 0){
            Thread.sleep(500);
        }

        Thread.sleep(2000);

        assertEquals(1, index);
        assertEquals(48, id[0]);
        assertEquals(SIZE, data[0].size());
        assertEquals(Result.OK, result[0]);

        adapter.stop();
    }

    public void testOneFile() throws Adapter.AdapterException, InterruptedException {
        this.index = 0;

        HttpAdapterConfig config = new HttpAdapterConfig(10, 10, 10, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
                        5,
                        500,
                        64,
                        64,
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)",
                        false,
                        "",
                        0,
                        "",
                        "",
                        5,
                        30000,
                        60000,
                        15000);
        HttpAdapter adapter = new HttpAdapter(this, config);
        Request request = new Request(ADDRESS01, Destination.FILE);
        AdapterRequest adapterRequest = new AdapterRequest(48, request);

        adapter.start();
        adapter.download(adapterRequest);

        while (adapter.getActiveCount() != 0){
            Thread.sleep(500);
        }

        Thread.sleep(2000);

        assertEquals(1, index);
        assertEquals(48, id[0]);
        assertEquals(Result.OK, result[0]);
        assertEquals(SIZE, data[0].size());

        adapter.stop();
    }

    public void testSeries() throws Adapter.AdapterException, InterruptedException {
        this.index = 0;

        HttpAdapterConfig config = new HttpAdapterConfig(10, 10, 10, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
                        5,
                        500,
                        64,
                        64,
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)",
                        false,
                        "",
                        0,
                        "",
                        "",
                        5,
                        30000,
                        60000,
                        15000);
        HttpAdapter adapter = new HttpAdapter(this, config);
        Request request01 = new Request(ADDRESS01, Destination.MEMORY);
        AdapterRequest adapterRequest01 = new AdapterRequest(48, request01);
        Request request02 = new Request(ADDRESS02, Destination.MEMORY);
        AdapterRequest adapterRequest02 = new AdapterRequest(49, request02);
        Request request03 = new Request(ADDRESS03, Destination.MEMORY);
        AdapterRequest adapterRequest03 = new AdapterRequest(50, request03);
        Request request04 = new Request(ADDRESS04, Destination.MEMORY);
        AdapterRequest adapterRequest04 = new AdapterRequest(51, request04);

        adapter.start();
        adapter.download(adapterRequest01);
        adapter.download(adapterRequest02);
        adapter.download(adapterRequest03);
        adapter.download(adapterRequest04);

        while (adapter.getActiveCount() != 0){
            Thread.sleep(500);
        }

        Thread.sleep(4000);

        assertEquals(4, index);

        adapter.stop();
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