package work.unit.fb2.resource;

import converter.format.fb2.resource.resolver.Fb2ResourceBundleResolver;
import converter.format.fb2.resource.resolver.cache.NullResourceCache;
import converter.format.fb2.resource.Fb2ResourceItem;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import junit.framework.TestCase;
import downloader.Downloader;
import downloader.RequestList;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;

import resource.ResourceConverterFactory;
import work.testutil.CompTestsUtils;
import app.controller.NullController;

/**
 * @author Igor Usenko
 *         Date: 04.10.2008
 */
public class ResourceResolverTest extends TestCase {

    private static final Fb2ResourceConversionContext CONTEXT = new Fb2ResourceConversionContext(700, 500, true, new byte[0], 0.7, true, true, 5);
    
    private static final String TEMP_DIRECTORY = ".\\work\\cache";
    private static final String DUMMY = CompTestsUtils.getCompTestsRoot() + "ResourceResolverTest\\notSupFmt.jpg";

    private static final String LOCAL_FILE_01 = ".\\work\\550.jpg";
    private static final String LOCAL_FILE_02 = ".\\work\\340.jpg";
    
    private static final String HTTP_FILE_01 = "/vr3x_RRJdd4/2.jpg";
    private static final String HTTP_BASE_01 = "http://img.youtube.com/vi/";
    private static final String HTTP_FILE_FULL_01 = "http://img.youtube.com/vr3x_RRJdd4/2.jpg";

    private static final String HTTP_FILE_02 = "/vr3x_RRJdd4/2.jpg";
    private static final String HTTP_BASE_02 = "http://img.youtube.com/vi";
    private static final String HTTP_FILE_FULL_02 = "http://img.youtube.com/vr3x_RRJdd4/2.jpg";

    private static final String HTTP_FILE_03 = "vr3x_RRJdd4/2.jpg";
    private static final String HTTP_BASE_03 = "http://img.youtube.com/vi/";
    private static final String HTTP_FILE_FULL_03 = "http://img.youtube.com/vi/vr3x_RRJdd4/2.jpg";
    //private static final String HTTP_FILE_FULL_03 = "http://img.youtube.com/vr3x_RRJdd4/2.jpg";

    private static final String HTTP_FILE_04 = "vr3x_RRJdd4/2.jpg";
    private static final String HTTP_BASE_04 = "http://img.youtube.com/vi/va";
    private static final String HTTP_FILE_FULL_04 = "http://img.youtube.com/vi/vr3x_RRJdd4/2.jpg";
    //private static final String HTTP_FILE_FULL_04 = "http://img.youtube.com/vr3x_RRJdd4/2.jpg";

    private static final int MOCK_FLUSH_DELAY = 500;

    public ResourceResolverTest(String s) {
        super(s);
    }

    public void testHostOne() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem("", LOCAL_FILE_01, "TAG01", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(1, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        RequestList requestList = requestLists[0];

        assertEquals(2, requestList.getRequests().size());
        assertEquals(LOCAL_FILE_01, requestList.getRequests().get(0).getAddress());
        assertEquals(DUMMY, requestList.getRequests().get(1).getAddress());

    }

    public void testHostTwo() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem("", LOCAL_FILE_01, "TAG01", CONTEXT);
        Fb2ResourceItem item02 = new Fb2ResourceItem("", LOCAL_FILE_02, "TAG02", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);
        list.add(item02);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(2, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        
        RequestList requestList01 = requestLists[0];
        RequestList requestList02 = requestLists[1];

        if(requestList01.getId() > requestList02.getId()){
            requestList01 = requestLists[1];
            requestList02 = requestLists[0];
        }

        assertEquals(2, requestList01.getRequests().size());
        assertEquals(LOCAL_FILE_01, requestList01.getRequests().get(0).getAddress());
        assertEquals(DUMMY, requestList01.getRequests().get(1).getAddress());

        assertEquals(2, requestList02.getRequests().size());
        assertEquals(LOCAL_FILE_02, requestList02.getRequests().get(0).getAddress());
        assertEquals(DUMMY, requestList02.getRequests().get(1).getAddress());
    }

    public void testHttpOneFull() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem(HTTP_BASE_03, HTTP_FILE_FULL_03, "TAG01", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(1, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        RequestList requestList = requestLists[0];

        assertEquals(1, requestList.getRequests().size());
        assertEquals(HTTP_FILE_FULL_03, requestList.getRequests().get(0).getAddress());
    }

    public void testHttpOneBrief01() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem(HTTP_BASE_01, HTTP_FILE_01, "TAG01", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(1, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        RequestList requestList = requestLists[0];

        assertEquals(1, requestList.getRequests().size());
        assertEquals(HTTP_FILE_FULL_01, requestList.getRequests().get(0).getAddress());
    }

    public void testHttpOneBrief02() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem(HTTP_BASE_02, HTTP_FILE_02, "TAG01", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(1, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        RequestList requestList = requestLists[0];

        assertEquals(1, requestList.getRequests().size());
        assertEquals(HTTP_FILE_FULL_02, requestList.getRequests().get(0).getAddress());
    }

    public void testHttpOneBrief03() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem(HTTP_BASE_03, HTTP_FILE_03, "TAG01", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(1, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        RequestList requestList = requestLists[0];

        assertEquals(1, requestList.getRequests().size());
        assertEquals(HTTP_FILE_FULL_03, requestList.getRequests().get(0).getAddress());
    }

    public void testHttpOneBrief04() throws Downloader.DownloaderException {
        DownloaderMock downloader = new DownloaderMock();
        downloader.start();

        Fb2ResourceBundleResolver resolver = new Fb2ResourceBundleResolver(downloader, new ResourceConverterFactory(TEMP_DIRECTORY), new NullResourceCache(), DUMMY, TEMP_DIRECTORY, new NullController());

        Fb2ResourceItem item01 = new Fb2ResourceItem(HTTP_BASE_04, HTTP_FILE_04, "TAG01", CONTEXT);

        List<Fb2ResourceItem> list = new ArrayList<Fb2ResourceItem>();
        list.add(item01);

        new Timer().schedule(new DownloaderMockTimerTask(downloader), MOCK_FLUSH_DELAY);

        resolver.resolve(list);

        downloader.stop();

        Map<Long, RequestList> map = downloader.getMap();

        assertEquals(1, map.size());

        RequestList[] requestLists = map.values().toArray(new RequestList[map.size()]);
        RequestList requestList = requestLists[0];

        assertEquals(1, requestList.getRequests().size());
        assertEquals(HTTP_FILE_FULL_04, requestList.getRequests().get(0).getAddress());
    }

}
