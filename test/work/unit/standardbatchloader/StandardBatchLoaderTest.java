package work.unit.standardbatchloader;

import junit.framework.TestCase;
import downloader.batchloader.StandardBatchLoader;
import downloader.RequestList;
import downloader.Request;
import downloader.Destination;
import downloader.Data;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import html.HttpData;
import app.controller.NullController;

/**
 * @author Igor Usenko
 *         Date: 04.04.2010
 */
public class StandardBatchLoaderTest extends TestCase {

    public StandardBatchLoaderTest(final String _s) {
        super(_s);
    }

    // первоначальный тест - при cancelled downloader все запросы возвращаются с флагом отмены
    // и устанавливается флаг отмены самого batch loader
    public void testSmoke() {
        DownloaderMock mock = new DownloaderMock();
        mock.setCancelled(true);

        StandardBatchLoader loader = new StandardBatchLoader(mock, new NullController());

        List<RequestList> requestList = new ArrayList<RequestList>();

        RequestList first = new RequestList(new Request("first", Destination.MEMORY), 0);
        RequestList second = new RequestList(new Request("second", Destination.MEMORY), 0);
        RequestList third = new RequestList(new Request("third", Destination.MEMORY), 0);

        requestList.add(first);
        requestList.add(second);
        requestList.add(third);

        Map<RequestList, HttpData> result = loader.load(requestList);

        assertEquals(3, result.size());
        assertTrue(loader.cancelled());
    }

    // тест - все прошло хорошо
    public void testAllOk() throws Data.DataException {
        DownloaderMock mock = new DownloaderMock();

        StandardBatchLoader loader = new StandardBatchLoader(mock, new NullController());

        List<RequestList> requestList = new ArrayList<RequestList>();

        RequestList first = new RequestList(new Request("first", Destination.MEMORY), 0);
        RequestList second = new RequestList(new Request("second", Destination.MEMORY), 0);
        RequestList third = new RequestList(new Request("third", Destination.MEMORY), 0);

        requestList.add(first);
        requestList.add(second);
        requestList.add(third);

        mock.flushLater(1000);

        Map<RequestList, HttpData> result = loader.load(requestList);

        assertEquals(3, result.size());
        assertFalse(loader.cancelled());
        assertEquals("first", new String(result.get(first).getData().getData()));
    }

    // тест - один запрос отменен
    public void testOneCancelled() throws Data.DataException {
        DownloaderMock mock = new DownloaderMock();

        StandardBatchLoader loader = new StandardBatchLoader(mock, new NullController());

        List<RequestList> requestList = new ArrayList<RequestList>();

        RequestList first = new RequestList(new Request("first", Destination.MEMORY), 0);
        RequestList second = new RequestList(new Request("second", Destination.MEMORY), 0);
        RequestList third = new RequestList(new Request("third", Destination.MEMORY), 0);

        requestList.add(first);
        requestList.add(second);
        requestList.add(third);

        mock.flushOneCancelledLater(1000);

        Map<RequestList, HttpData> result = loader.load(requestList);

        assertEquals(3, result.size());
        assertTrue(loader.cancelled());
    }

    // тест - загрузка списка адресов
    public void testLoadUrls() throws Data.DataException {
        DownloaderMock mock = new DownloaderMock();

        StandardBatchLoader loader = new StandardBatchLoader(mock, new NullController());

        List<String> urls = new ArrayList<String>();
        String url01 = "url01";
        String url02 = "url02";
        String url03 = "url03";
        urls.add(url01);
        urls.add(url02);
        urls.add(url03);

        mock.flushLater(1000);

        Map<String, HttpData> result = loader.loadUrls(urls, 0);

        assertEquals(3, result.size());
        assertEquals(url01, new String(result.get(url01).getData().getData()));
        assertEquals(url02, new String(result.get(url02).getData().getData()));
        assertEquals(url03, new String(result.get(url03).getData().getData()));
    }
}
