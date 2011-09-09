package downloader;

import downloader.data.EmptyData;
import downloader.hostadapter.HostAdapter;
import downloader.hostadapter.HostAdapterConfig;
import downloader.httpadapter.HttpAdapter;
import downloader.httpadapter.HttpAdapterConfig;
import html.HttpData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Стандартный асинхронный HTTP загрузчик
 *
 * @author Igor Usenko
 *         Date: 08.04.2010
 */
public class StandardDownloader implements Downloader, Listener {

    private static final String HTTP_PREFIX = "HTTP:";

    private static final String INPUT_THREAD_NAME = "DownloaderInputThread";
    private static final String PROCESSING_THREAD_NAME = "DownloaderProcessingThread";
    private static final String FINISHED_THREAD_NAME = "DownloaderFinishedThread";

    private final StandardDownloaderConfig config;

    private final Map<Protocol, Adapter> adapters;

    private final AtomicBoolean cancelling;
    private final AtomicBoolean stopped;

    private final BlockingQueue<RequestList> input;
    private final BlockingQueue<RequestListHolder> finished;
    private final BlockingQueue<RequestListHolder> processing;

    private final Map<Long, RequestListHolder> holderMap;

    private CountDownLatch cancelLatch;

    private final Log log;

    private class InputThread extends Thread {

        public InputThread() {
            super(INPUT_THREAD_NAME);

            setDaemon(true);
        }

        public void run() {

            while (true) {

                try {

                    RequestList list = input.take();
                    RequestListHolder holder = new RequestListHolder(list);

                    if (cancelling.get() || stopped.get()) {
                        holder.setResult(Result.CANCEL);
                        holder.setData(new EmptyData());

                        finished.put(holder);
                    } else {
                        processing.put(holder);
                    }
                } catch (InterruptedException e) {
                    log.error("Error while take request list from input queue", e);
                }
            }
        }
    }

    private class ProcessingThread extends Thread {

        public ProcessingThread() {
            super(PROCESSING_THREAD_NAME);

            setDaemon(true);
        }

        public void run() {

            while (true) {

                try {
                    RequestListHolder holder = processing.take();

                    if (holder.hasNext()) {
                        Request request = holder.getNextRequest();
                        Adapter adapter = getAdapter(request);

                        if (adapter == null) {
                            sendError(holder);
                        } else {
                            holderMap.put(holder.getId(), holder);
                            adapter.download(new AdapterRequest(holder.getId(), request));

                            log.debug("Download request successfully sended [ " + request.getDescription() + " ]");
                        }
                    } else {
                        sendError(holder);
                    }
                } catch (InterruptedException e) {
                    log.error("Error while take request list holder from processing queue", e);
                }
            }
        }

        private void sendError(final RequestListHolder _holder) throws InterruptedException {
            log.warn("Can`t download requested data. Id [ " + _holder.getId() + " ]");

            _holder.setResult(Result.ERROR);
            _holder.setData(new EmptyData());

            finished.put(_holder);
        }

        private Adapter getAdapter(final Request _request) {
            Protocol protocol = getProtocol(_request);

            Adapter result = adapters.get(protocol);

            if (result == null) {
                log.error("Can`t find adapter for request [ " + _request.getDescription() + " ]");
            }

            return result;
        }

        private Protocol getProtocol(final Request _request) {

            if (_request.getAddress().toUpperCase().trim().startsWith(HTTP_PREFIX.toUpperCase())) {
                return Protocol.HTTP;
            }

            return Protocol.HOST;
        }

    }

    private class FinishedThread extends Thread {

        public FinishedThread() {
            super(FINISHED_THREAD_NAME);

            setDaemon(true);
        }

        public void run() {

            while (true) {

                try {
                    RequestListHolder holder = finished.take();

                    if (cancelling.get() || stopped.get()) {
                        holder.getListener().requestDone(holder.getId(), HttpData.CANCEL_DATA);
                    } else {
                        HttpData data = new HttpData(holder.getCurrentRequest().getResponseUrl(), holder.getData(), holder.getResult());
                        holder.getListener().requestDone(holder.getId(), data);
                    }

                    log.debug("Request list done notification sended to listener");
                } catch (InterruptedException e) {
                    log.error("Error while take request list holder from finished queue", e);
                }
            }
        }
    }

    public StandardDownloader(final StandardDownloaderConfig _config) {
        Assert.notNull(_config, "Standard downloader config is null");
        this.config = _config;

        this.cancelling = new AtomicBoolean(false);
        this.stopped = new AtomicBoolean(false);

        this.input = new LinkedBlockingQueue<RequestList>();
        this.finished = new LinkedBlockingQueue<RequestListHolder>();
        this.processing = new LinkedBlockingQueue<RequestListHolder>();

        this.holderMap = Collections.synchronizedMap(new HashMap<Long, RequestListHolder>());

        this.adapters = new HashMap<Protocol, Adapter>();

        this.log = LogFactory.getLog(getClass());
    }

    public void start() throws DownloaderException {

        try {
            createAdaptersMap();
            startAdapters();

            new InputThread().start();
            new FinishedThread().start();
            new ProcessingThread().start();

            this.log.debug("Standard downloader started");
            this.stopped.set(false);
        } catch (Adapter.AdapterException e) {
            throw new DownloaderException("Error creating adapters map", e);
        }
    }

    public void stop() {
        stopAdapters();

        this.stopped.set(true);
        this.log.debug("Standard downloader stopped");
    }

    public long download(final RequestList _requestList) {
        Assert.notNull(_requestList, "Request list is null");

        long result = System.nanoTime();

        try {
            _requestList.setId(result);
            this.input.put(_requestList);

            this.log.debug("Request put in queue with id [ " + result + " ]");
        } catch (InterruptedException e) {
            result = -1;
            this.log.error("Error while put request in downloader queue", e);
        }

        return result;
    }

    public void cancel() {
        this.cancelling.set(true);

        try {
            this.cancelLatch = new CountDownLatch(this.adapters.size());

            for (Adapter adapter : this.adapters.values()) {
                adapter.cancel();
            }

            this.log.debug("Cancel latch raised");
            this.cancelLatch.await();
            this.log.debug("Cancel latch released");
        } catch (InterruptedException e) {
            this.log.debug("Error while waiting cancel latch releasing", e);
        }

        this.cancelling.set(false);
    }

    public void requestDone(final long _id, final Result _result, final Data _data) {
        Assert.notNull(_result, "Result is null");
        Assert.notNull(_data, "Data is null");

        RequestListHolder holder = this.holderMap.get(_id);

        if (holder == null) {
            this.log.error("Can not find request list holder for request id [ " + _id + " ]");
        } else {
            this.holderMap.remove(_id);

            if (_result == Result.ERROR) {
                anotherTry(holder);
            } else {
                putToFinished(holder, _result, _data);
            }
        }
    }

    public void cancellingFinished() {

        if (this.cancelLatch == null) {
            this.log.error("Cancel latch is null");
        } else {
            this.cancelLatch.countDown();
            this.log.debug("Cancel latch decreased");
        }
    }

    private void anotherTry(final RequestListHolder _holder) {

        try {
            this.processing.put(_holder);
        } catch (InterruptedException e) {
            this.log.error("Error while put request list holder to processing queue", e);
        }
    }

    private void putToFinished(final RequestListHolder holder, final Result _result, final Data _data) {
        try {
            holder.setData(_data);
            holder.setResult(_result);

            this.finished.put(holder);
        } catch (InterruptedException e) {
            this.log.error("Error while put request list holder to finished queue", e);
        }
    }

    private void createAdaptersMap() throws Adapter.AdapterException {
        HostAdapterConfig hostAdapterConfig = new HostAdapterConfig(this.config.getCorePoolSize(),
                this.config.getMaxPoolSize(),
                this.config.getKeepAliveTime());

        HttpAdapterConfig httpAdapterConfig = new HttpAdapterConfig(this.config.getCorePoolSize(),
                this.config.getMaxPoolSize(),
                this.config.getKeepAliveTime(),
                this.config.getWorkDirectory(),
                this.config.getTimeService(),
                this.config.getCacheStorageTime(),
                this.config.getBannedListTreshold(),
                this.config.getBannedListLimit(),
                this.config.getMaxConnectionsPerHost(),
                this.config.getMaxTotalConnections(),
                this.config.getUserAgent(),
                this.config.isProxyUsing(),
                this.config.getProxyHost(),
                this.config.getProxyPort(),
                this.config.getUserName(),
                this.config.getUserPassword(),
                this.config.getMaxTryCount(),
                this.config.getSocketTimeout(),
                this.config.getErrorTimeout(),
                this.config.getMinTimeout());

        this.adapters.put(Protocol.HTTP, new HttpAdapter(this, httpAdapterConfig));
        this.adapters.put(Protocol.HOST, new HostAdapter(this, hostAdapterConfig));
    }

    private void startAdapters() {

        for (Adapter current : this.adapters.values()) {
            current.start();
        }
    }

    private void stopAdapters() {

        for (Adapter current : this.adapters.values()) {
            current.stop();
        }
    }

    private static class RequestListHolder {
        private final RequestList requestList;
        private int index;

        private Result result;
        private Data data;

        public RequestListHolder(final RequestList _requestList) {
            Assert.notNull(_requestList, "Request list is null");
            this.requestList = _requestList;

            this.index = 0;
        }

        public DownloaderListener getListener() {
            return this.requestList.getListener();
        }

        public long getId() {
            return this.requestList.getId();
        }

        public boolean hasNext() {
            return (this.index <= (this.requestList.getSize() - 1));
        }

        public Request getNextRequest() {
            Request result = null;

            if (this.index < this.requestList.getSize()) {
                result = this.requestList.get(this.index);
                ++this.index;
            }

            return result;
        }

        public Request getCurrentRequest() {
            return this.requestList.get(this.index - 1);
        }

        public Data getData() {
            return this.data;
        }

        public void setData(final Data _data) {
            Assert.notNull(_data, "Data is null");
            this.data = _data;
        }

        public Result getResult() {
            return this.result;
        }

        public void setResult(final Result _result) {
            Assert.notNull(_result, "Result is null");
            this.result = _result;
        }
    }
}
