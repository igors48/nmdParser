package downloader.batchloader;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import downloader.*;
import html.HttpData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Стандартный пакетный загрузчик
 *
 * @author Igor Usenko
 *         Date: 03.04.2010
 */
public class StandardBatchLoader implements BatchLoader, DownloaderListener {

    private static final String FUTURE_THREAD_NAME = "StandardBatchLoaderFutureThread";
    private static final String SENDER_THREAD_NAME = "StandardBatchLoaderSenderThread";
    private static final String RECEIVER_THREAD_NAME = "StandardBatchLoaderReceiverThread";
    private static final String WAITER_THREAD_NAME = "StandardBatchLoaderWaiterThread";
    private static final String PROCESS_LOADING_DATA = "process.loading.data";

    private final Downloader downloader;
    private final Controller controller;

    private final Map<Long, RequestList> sended;
    private final Map<Long, HttpData> received;

    private final Map<RequestList, HttpData> result;

    private final BlockingQueue<CompletedRequest> completed;

    private final AtomicBoolean cancelled;
    private final AtomicInteger counter;

    private ReceiverThread receiverThread;
    private boolean receiverThreadStarted;

    private CountDownLatch allSendedSignal;
    private CountDownLatch allReceivedSignal;

    private int total;

    private final Log log;

    private class SenderThread extends Thread {

        private final List<RequestList> requests;

        private SenderThread(final List<RequestList> _requests) {
            super(SENDER_THREAD_NAME);
            setDaemon(true);

            Assert.notNull(_requests, "Requests is null");
            this.requests = _requests;
        }

        public void run() {

            for (RequestList list : this.requests) {

                if (cancelled.get()) {
                    result.put(list, HttpData.CANCEL_DATA);
                } else {
                    handle(list);
                }

                pause(list.getPauseBetweenRequests());
            }

            allSendedSignal.countDown();
        }

        private void pause(long _timeout) {

            try {
                log.debug(MessageFormat.format("Paused between requests for [ {0} ] ms", _timeout));

                Thread.sleep(_timeout);
            } catch (InterruptedException e) {
                log.debug("Interrupted while sleeping");
            }
        }

        private void handle(final RequestList _list) {
            long id = downloader.download(_list);

            if (id == -1) {
                result.put(_list, HttpData.CANCEL_DATA);
                cancelled.set(true);

                log.debug("Request cancelled");
            } else {
                sended.put(id, _list);
                counter.incrementAndGet();

                log.debug("Request sended - id [ " + id + " ]");
            }
        }
    }

    private class ReceiverThread extends Thread {

        private ReceiverThread() {
            super(RECEIVER_THREAD_NAME);
            setDaemon(true);
        }

        public void run() {

            //TODO а потом оно висит в списке тредов
            while (true) {

                try {
                    CompletedRequest current = completed.take();

                    if (current.getData().getResult() == Result.CANCEL) {
                        cancelled.set(true);
                    }

                    RequestList source = sended.get(current.getId());

                    // есть впечатление, что SenderThread не успевает 
                    if (source == null) {
                        Thread.sleep(100);
                        source = sended.get(current.getId());
                    }

                    if (source == null) {
                        log.error("Can not find request list for id [ " + current.getId() + " ]");
                    } else {
                        result.put(source, current.getData());
                        controller.onProgress(new SingleProcessInfo(PROCESS_LOADING_DATA, result.size(), total));
                    }

                    counter.decrementAndGet();
                } catch (InterruptedException e) {
                    log.error("Error in " + RECEIVER_THREAD_NAME, e);
                }
            }
        }
    }

    private class WaiterThread extends Thread {

        private WaiterThread() {
            super(WAITER_THREAD_NAME);
            setDaemon(true);
        }

        public void run() {

            try {
                allSendedSignal.await();

                while (counter.get() > 0) {
                    sleep(250);
                }

            } catch (InterruptedException e) {
                log.error("Error in " + RECEIVER_THREAD_NAME, e);
            }

            allReceivedSignal.countDown();
        }
    }

    public StandardBatchLoader(final Downloader _downloader, final Controller _controller) {
        Assert.notNull(_downloader, "Downloader is null");
        this.downloader = _downloader;

        Assert.notNull(_controller, "Controller is null");
        this.controller = _controller;

        this.sended = Collections.synchronizedMap(new HashMap<Long, RequestList>());
        this.received = Collections.synchronizedMap(new HashMap<Long, HttpData>());

        this.result = Collections.synchronizedMap(new HashMap<RequestList, HttpData>());

        this.completed = new LinkedBlockingQueue<CompletedRequest>();

        this.counter = new AtomicInteger();
        this.cancelled = new AtomicBoolean(false);

        this.log = LogFactory.getLog(getClass());

        this.receiverThread = new ReceiverThread();
        this.receiverThreadStarted = false;
    }

    public Map<String, HttpData> loadUrls(final List<String> _urls, final long _pauseBetweenRequests) {
        Assert.notNull(_urls, "Url list is null");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        Map<String, RequestList> urlMap = new HashMap<String, RequestList>();
        List<RequestList> requestList = new ArrayList<RequestList>();

        for (String url : _urls) {
            RequestList request = new RequestList(new Request(url, Destination.FILE), _pauseBetweenRequests);
            urlMap.put(url, request);
            requestList.add(request);
        }

        return join(_urls, urlMap, load(requestList));
    }

    public Map<RequestList, HttpData> load(final List<RequestList> _requests) {
        Assert.notNull(_requests, "Requests is null");

        try {
            this.total = _requests.size();

            for (RequestList request : _requests) {
                request.setListener(this);
            }

            reset();

            startReceiverThread();

            new WaiterThread().start();

            this.controller.onProgress(new SingleProcessInfo(PROCESS_LOADING_DATA, 0, this.total));

            new SenderThread(_requests).start();

            this.allReceivedSignal.await();
        } catch (InterruptedException e) {
            this.log.error("Error while loading", e);
        }

        return this.result;
    }

    public HttpData loadUrlWithReferer(final String _url, final String _referer) {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_referer, "Referer is null");

        Map<String, RequestList> urlMap = new HashMap<String, RequestList>();
        List<RequestList> requestList = new ArrayList<RequestList>();

        RequestList request = new RequestList(new Request(_url, _referer, Destination.FILE), 0);
        urlMap.put(_url, request);
        requestList.add(request);

        HttpData data = join(Arrays.asList(_url), urlMap, load(requestList)).get(_url);

        return data == null ? HttpData.EMPTY_DATA : data;
    }

    public boolean cancelled() {
        return this.cancelled.get();
    }

    public void requestDone(final long _id, final HttpData _data) {
        Assert.notNull(_data, "Http data is null");

        try {
            this.completed.put(new CompletedRequest(_id, _data));
        } catch (InterruptedException e) {
            log.error("Unexpected error while put completed request in queue", e);
        }
    }

    private Map<String, HttpData> join(final List<String> _urls, final Map<String, RequestList> _urlMap, final Map<RequestList, HttpData> _loaded) {
        Map<String, HttpData> result = new HashMap<String, HttpData>();

        for (String url : _urls) {
            RequestList request = _urlMap.get(url);

            if (request == null) {
                result.put(url, HttpData.EMPTY_DATA);
            } else {
                HttpData data = _loaded.get(request);

                result.put(url, data == null ? HttpData.EMPTY_DATA : data);
            }
        }

        return result;
    }

    private void reset() {
        this.sended.clear();
        this.received.clear();

        this.allSendedSignal = new CountDownLatch(1);
        this.allReceivedSignal = new CountDownLatch(1);

        this.counter.set(0);
        this.cancelled.set(false);
    }

    private void startReceiverThread() {

        if (!this.receiverThreadStarted) {
            this.receiverThread.start();
            this.receiverThreadStarted = true;
        }
    }

    private class CompletedRequest {
        private final long id;
        private final HttpData data;

        private CompletedRequest(final long _id, final HttpData _data) {
            this.id = _id;

            Assert.notNull(_data, "Http data is null");
            this.data = _data;
        }

        public HttpData getData() {
            return this.data;
        }

        public long getId() {
            return this.id;
        }
    }
}
