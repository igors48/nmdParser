package work.comp.downloader;

import junit.framework.TestCase;
import downloader.*;

import java.util.List;
import java.util.ArrayList;

import timeservice.StandardTimeService;
import work.testutil.CompTestsUtils;
import html.HttpData;

/**
 * @author Igor Usenko
 *         Date: 28.09.2008
 */
public class StandardDownloaderTest extends TestCase implements DownloaderListener {

    private static final String WORK_CACHE = ".\\work\\cache\\";
    private static final int THREAD_COUNT = 10;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final String LOCAL_ADDRESS01 = CompTestsUtils.getCompTestsRoot() + "/DownloaderAndHostAdapterTest/"  + "001.txt";
    private static final String LOCAL_ADDRESS01_ERR = CompTestsUtils.getCompTestsRoot() + "/DownloaderAndHostAdapterTest/"  + "001.txt0";
    private static final String LOCAL_ADDRESS02 = CompTestsUtils.getCompTestsRoot() + "/DownloaderAndHostAdapterTest/"  + "002.txt";

    public static final int SIZE = 141;
    private static final String HTTP_ADDRESS01 = "http://192.168.1.1/main.html";
    private static final String HTTP_ADDRESS01_ERR = "http://192.168.1.128/main.html";
    private static final String HTTP_ADDRESS02 = "http://forum.ixbt.com/topic.cgi?id=47:1479-47";
    private static final String HTTP_ADDRESS03 = "http://www.roombareview.com/chat/index.php";
    /*private static final String HTTP_ADDRESS04 = "http://forums.irobot.com/";*/
    private static final String HTTP_ADDRESS04 = "http://google.com/";

    private static int COUNT = 100;

    private long[] id = new long[COUNT];
    private Result[] result = new Result[COUNT];
    private Data[] data = new Data[COUNT];
    private int index;

    private int lock;

    public StandardDownloaderTest(String s) {
        super(s);
    }

    public void testHost() throws Adapter.AdapterException, InterruptedException, Data.DataException, Downloader.DownloaderException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request = new Request(LOCAL_ADDRESS01, Destination.MEMORY);
        List<Request> list = new ArrayList<Request>();
        list.add(request);

        RequestList requestList = new RequestList(this, list);

        long reqId = downloader.download(requestList);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        downloader.stop();

        assertEquals(1, this.index);
        assertEquals(reqId, this.id[0]);
        assertEquals("1", new String(this.data[0].getData(0, 0)));
        assertEquals("2", new String(this.data[0].getData(1, 1)));
        assertEquals("3", new String(this.data[0].getData(2, 2)));
        assertEquals("4", new String(this.data[0].getData(3, 3)));
        assertEquals("1234", new String(this.data[0].getData(0, 3)));
    }

    public void testHostDouble() throws Adapter.AdapterException, InterruptedException, Data.DataException, Downloader.DownloaderException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(LOCAL_ADDRESS01, Destination.MEMORY);
        Request request02 = new Request(LOCAL_ADDRESS02, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        RequestList requestList01 = new RequestList(this, list01);

        List<Request> list02 = new ArrayList<Request>();
        list02.add(request02);
        RequestList requestList02 = new RequestList(this, list02);

        long reqId01 = downloader.download(requestList01);
        lock();
        long reqId02 = downloader.download(requestList02);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        downloader.stop();

        assertEquals(2, this.index);

        assertTrue(((reqId01 == this.id[0]) && (reqId02 == this.id[1])) || ((reqId01 == this.id[1]) && (reqId02 == this.id[0])));

        int reqId01Index = 0;
        int reqId02Index = 1;

        if((reqId01 == this.id[1])){
            reqId01Index = 1;
            reqId02Index = 0;
        }

        assertEquals(reqId01, this.id[reqId01Index]);
        assertEquals("1", new String(this.data[reqId01Index].getData(0, 0)));
        assertEquals("2", new String(this.data[reqId01Index].getData(1, 1)));
        assertEquals("3", new String(this.data[reqId01Index].getData(2, 2)));
        assertEquals("4", new String(this.data[reqId01Index].getData(3, 3)));
        assertEquals("1234", new String(this.data[reqId01Index].getData(0, 3)));

        assertEquals(reqId02, this.id[reqId02Index]);
        assertEquals("1", new String(this.data[reqId02Index].getData(0, 0)));
        assertEquals("2", new String(this.data[reqId02Index].getData(1, 1)));
        assertEquals("3", new String(this.data[reqId02Index].getData(2, 2)));
        assertEquals("4", new String(this.data[reqId02Index].getData(3, 3)));
        assertEquals("2345", new String(this.data[reqId02Index].getData(1, 4)));
    }

    public void testHttp() throws Adapter.AdapterException, InterruptedException, Data.DataException, Downloader.DownloaderException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request = new Request(HTTP_ADDRESS01, Destination.MEMORY);
        List<Request> list = new ArrayList<Request>();
        list.add(request);

        RequestList requestList = new RequestList(this, list);

        long reqId = downloader.download(requestList);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(1, this.index);
        assertEquals(reqId, this.id[0]);
        assertEquals(SIZE, this.data[0].size());
        assertEquals(Result.OK, this.result[0]);

        downloader.stop();
    }

    public void testHttpThree() throws Adapter.AdapterException, InterruptedException, Data.DataException, Downloader.DownloaderException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(HTTP_ADDRESS01, Destination.MEMORY);
        Request request02 = new Request(HTTP_ADDRESS01, Destination.MEMORY);
        Request request03 = new Request(HTTP_ADDRESS01, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        RequestList requestList01 = new RequestList(this, list01);

        List<Request> list02 = new ArrayList<Request>();
        list02.add(request02);
        RequestList requestList02 = new RequestList(this, list02);

        List<Request> list03 = new ArrayList<Request>();
        list03.add(request03);
        RequestList requestList03 = new RequestList(this, list03);

        long reqId01 = downloader.download(requestList01);
        lock();
        long reqId02 = downloader.download(requestList02);
        lock();
        long reqId03 = downloader.download(requestList03);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(3, this.index);

        assertTrue((reqId01 == this.id[0]) || (reqId01 == this.id[1]) || (reqId01 == this.id[2]));
        assertTrue((reqId02 == this.id[0]) || (reqId02 == this.id[1]) || (reqId02 == this.id[2]));
        assertTrue((reqId03 == this.id[0]) || (reqId03 == this.id[1]) || (reqId03 == this.id[2]));

        assertEquals(SIZE, this.data[0].size());
        assertEquals(SIZE, this.data[1].size());
        assertEquals(SIZE, this.data[2].size());

        assertEquals(Result.OK, this.result[0]);
        assertEquals(Result.OK, this.result[1]);
        assertEquals(Result.OK, this.result[2]);

        downloader.stop();
    }

    public void testBulk() throws Adapter.AdapterException, InterruptedException, Data.DataException, Downloader.DownloaderException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(LOCAL_ADDRESS01, Destination.MEMORY);
        Request request02 = new Request(LOCAL_ADDRESS02, Destination.MEMORY);
        Request request03 = new Request(HTTP_ADDRESS01, Destination.MEMORY);
        Request request04 = new Request(HTTP_ADDRESS02, Destination.MEMORY);
        Request request05 = new Request(HTTP_ADDRESS03, Destination.MEMORY);
        Request request06 = new Request(HTTP_ADDRESS04, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        RequestList requestList01 = new RequestList(this, list01);

        List<Request> list02 = new ArrayList<Request>();
        list02.add(request02);
        RequestList requestList02 = new RequestList(this, list02);

        List<Request> list03 = new ArrayList<Request>();
        list03.add(request03);
        RequestList requestList03 = new RequestList(this, list03);

        List<Request> list04 = new ArrayList<Request>();
        list04.add(request04);
        RequestList requestList04 = new RequestList(this, list04);

        List<Request> list05 = new ArrayList<Request>();
        list05.add(request05);
        RequestList requestList05 = new RequestList(this, list05);

        List<Request> list06 = new ArrayList<Request>();
        list06.add(request06);
        RequestList requestList06 = new RequestList(this, list06);

        long reqId01 = downloader.download(requestList01);
        lock();
        long reqId02 = downloader.download(requestList02);
        lock();
        long reqId03 = downloader.download(requestList03);
        lock();
        long reqId04 = downloader.download(requestList04);
        lock();
        long reqId05 = downloader.download(requestList05);
        lock();
        long reqId06 = downloader.download(requestList06);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(6, this.index);

        int reqId01Index = getIndex(reqId01);
        int reqId02Index = getIndex(reqId02);
        int reqId03Index = getIndex(reqId03);
        int reqId04Index = getIndex(reqId04);
        int reqId05Index = getIndex(reqId05);
        int reqId06Index = getIndex(reqId06);

        assertTrue(reqId01Index != -1);
        assertTrue(reqId02Index != -1);
        assertTrue(reqId03Index != -1);
        assertTrue(reqId04Index != -1);
        assertTrue(reqId05Index != -1);
        assertTrue(reqId06Index != -1);

        downloader.stop();
    }

    public void testHostManyItemFirstOk() throws Downloader.DownloaderException, InterruptedException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(LOCAL_ADDRESS01, Destination.MEMORY);
        Request request02 = new Request(LOCAL_ADDRESS02, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        list01.add(request02);
        RequestList requestList01 = new RequestList(this, list01);

        long reqId01 = downloader.download(requestList01);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(1, this.index);
        assertTrue(reqId01 == this.id[0]);
        assertEquals(4, this.data[0].size());

        downloader.stop();
    }

    public void testHostManyItemSecondOk() throws Downloader.DownloaderException, InterruptedException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(LOCAL_ADDRESS01_ERR, Destination.MEMORY);
        Request request02 = new Request(LOCAL_ADDRESS02, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        list01.add(request02);
        RequestList requestList01 = new RequestList(this, list01);

        long reqId01 = downloader.download(requestList01);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(1, this.index);
        assertTrue(reqId01 == this.id[0]);
        assertEquals(5, this.data[0].size());

        downloader.stop();
    }

    public void testHttpManyItemFirstOk() throws Downloader.DownloaderException, InterruptedException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(HTTP_ADDRESS01, Destination.MEMORY);
        Request request02 = new Request(HTTP_ADDRESS02, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        list01.add(request02);
        RequestList requestList01 = new RequestList(this, list01);

        long reqId01 = downloader.download(requestList01);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(1, this.index);
        assertTrue(reqId01 == this.id[0]);
        assertEquals(SIZE, this.data[0].size());

        downloader.stop();
    }

    public void testHttpManyItemSecondOk() throws Downloader.DownloaderException, InterruptedException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(LOCAL_ADDRESS01_ERR, Destination.MEMORY);
        Request request02 = new Request(HTTP_ADDRESS01, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        list01.add(request02);
        RequestList requestList01 = new RequestList(this, list01);

        long reqId01 = downloader.download(requestList01);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(1, this.index);
        assertTrue(reqId01 == this.id[0]);
        assertEquals(SIZE, this.data[0].size());

        downloader.stop();
    }

    public void testHostManyItemAllError() throws Downloader.DownloaderException, InterruptedException {
        this.index = 0;
        resetLock();

        StandardDownloaderConfig downloaderConfig = new StandardDownloaderConfig(THREAD_COUNT,
                THREAD_COUNT, KEEP_ALIVE_TIME, WORK_CACHE, new StandardTimeService(), 1000 * 60 * 30,
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
        StandardDownloader downloader = new StandardDownloader(downloaderConfig);
        downloader.start();

        Request request01 = new Request(LOCAL_ADDRESS01_ERR, Destination.MEMORY);
        Request request02 = new Request(LOCAL_ADDRESS01_ERR, Destination.MEMORY);

        List<Request> list01 = new ArrayList<Request>();
        list01.add(request01);
        list01.add(request02);
        RequestList requestList01 = new RequestList(this, list01);

        long reqId01 = downloader.download(requestList01);
        lock();

        while(isLocked()){
            Thread.sleep(250);
        }

        assertEquals(1, this.index);
        assertTrue(reqId01 == this.id[0]);
        assertEquals(0, this.data[0].size());

        downloader.stop();
    }

    public synchronized void requestDone(long _id, HttpData _data) {
        this.id[this.index] = _id;
        this.result[this.index] = _data.getResult();
        this.data[this.index] = _data.getData();
        ++this.index;

        unlock();
    }

    private int getIndex(long _id){

        if (this.index == 0){
            return -1;
        }

        for (int current = 0; current < this.index; ++current){

            if (this.id[current] == _id){
                return current;
            }
        }

        return -1;
    }

    private synchronized void lock(){
        ++this.lock;
    }

    private synchronized void unlock(){
        --this.lock;
    }

    private synchronized void resetLock(){
        this.lock = 0;
    }

    private synchronized boolean isLocked(){
        return (this.lock != 0);
    }
}