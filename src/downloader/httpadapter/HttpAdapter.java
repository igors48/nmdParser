package downloader.httpadapter;

import downloader.*;
import downloader.data.DataFile;
import downloader.data.EmptyData;
import downloader.data.MemoryData;
import downloader.httpadapter.banned.BannedList;
import downloader.httpadapter.cache.Cache;
import downloader.httpadapter.cache.CacheItem;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * HTTP адаптер
 *
 * @author Igor Usenko
 *         Date: 01.04.2010
 */
public class HttpAdapter implements Adapter, HttpAdapterThreadOwner {

    private static final String INPUT_THREAD_NAME = "HttpAdapterInputThread";
    private static final String OUTPUT_THREAD_NAME = "HttpAdapterOutputThread";
    private static final String CANCELLER_THREAD_NAME = "HttpAdapterCancellerThread";

    private static final int CACHE_REPLY_DELAY = 5;

    private final Listener listener;
    private final HttpAdapterConfig config;

    private final BlockingQueue<AdapterRequest> input;
    private final List<HttpAdapterThread> threads;
    private final BlockingQueue<Container> output;

    private final Executor executor;

    private final Cache cache;
    private final BannedList banned;

    private final HttpAdapterThreadConfig threadConfig;

    private final AtomicBoolean cancelling;

    private HttpClient client;

    private long dataCounter;

    private final Log log;

    private class InputThread extends Thread {

        private InputThread() {
            super(INPUT_THREAD_NAME);

            setDaemon(true);
        }

        public void run() {

            while (true) {

                try {
                    AdapterRequest request = input.take();

                    if (cancelling.get()) {
                        output.offer(new Container(request, new EmptyData(), Result.CANCEL));

                        log.debug("Request [ " + request.getDescription() + " ] cancelled");
                    } else {
                        handle(request);
                    }
                } catch (InterruptedException e) {
                    log.error("Error in [ " + INPUT_THREAD_NAME + " ]", e);
                }
            }
        }

        private void handle(final AdapterRequest _request) throws InterruptedException {
            CacheItem cached = cache.get(_request.getAddress());

            if (cached == null) {
                submit(_request);
            } else {
                sleep(CACHE_REPLY_DELAY); //todo если слишком быстро ответить - получаетс€ дурацка€ ситуаци€. решение, конечно, не очень - нужно чего-то другое придумать
                _request.setResponseUrl(cached.getResponseAddress()); // feed proxy посв€щаетс€
                output.offer(new Container(_request, new DataFile(cached.getFileName(), cached.getCharset()), Result.OK));

                log.debug("Request [ " + _request.getDescription() + " ] taken from adapter cache");
            }
        }

        private void submit(AdapterRequest _request) {

            if (hostBanned(HttpAdapterTools.getHost(_request.getAddress()))) {
                output.offer(new Container(_request, new MemoryData(("Data can not be loaded because host [ " + HttpAdapterTools.getHost(_request.getAddress()) + " ] is banned.").getBytes()), Result.OK));

                log.debug("Request [ " + _request.getDescription() + " ] can not be loaded because host is banned.");
            } else {
                HttpAdapterThread thread = createThreadForRequest(_request);
                threads.add(thread);
                executor.execute(thread);

                log.debug("Thread for request [ " + _request.getDescription() + " ] sumbitted");
                log.debug("Balancer [ " + threads.size() + " ]");
            }
        }
    }

    private class OutputThread extends Thread {

        private OutputThread() {
            super(OUTPUT_THREAD_NAME);

            setDaemon(true);
        }

        public void run() {

            while (true) {

                try {
                    Container container = output.take();
                    dataCounter += container.getData().size();


                    if (container.getResult() == Result.ERROR) {
                        complain(container);
                    }


                    if (container.getResult() == Result.OK) {

                        if (container.notCached()) {
                            putToCache(container);
                        }
                    }

                    listener.requestDone(container.getRequest().getId(), cancelling.get() ? Result.CANCEL : container.getResult(), container.getData());

                    log.debug("Request done [ " + container.getRequest().getDescription() + " ] Size [ " + container.getData().size() + " ]");
                } catch (InterruptedException e) {
                    log.error("Error in [ " + OUTPUT_THREAD_NAME + " ]", e);
                }
            }
        }
    }

    private class CancellerThread extends Thread {

        private CancellerThread() {
            super(CANCELLER_THREAD_NAME);

            setDaemon(true);
        }

        public void run() {

            try {

                while (cancelling.get() && threads.size() != 0) {
                    sleep(500);
                }
            } catch (InterruptedException e) {
                log.error("Error in " + CANCELLER_THREAD_NAME, e);
            }

            cancelling.set(false);
            listener.cancellingFinished();
        }
    }

    public HttpAdapter(final Listener _listener, final HttpAdapterConfig _config) throws AdapterException {
        Assert.notNull(_listener, "Listener is null");
        Assert.notNull(_config, "Config is null");

        try {
            this.listener = _listener;
            this.config = _config;

            this.cache = new Cache(this.config.getWorkDirectory(), this.config.getCacheStorageTime(), this.config.getTimeService());
            this.banned = new BannedList(this.config.getBannedListTreshold(), this.config.getBannedListLimit());

            this.input = new LinkedBlockingQueue<AdapterRequest>();
            this.threads = Collections.synchronizedList(new ArrayList<HttpAdapterThread>());
            this.output = new LinkedBlockingQueue<Container>();

            this.executor = new Executor();

            this.threadConfig = new HttpAdapterThreadConfig(this.config.getWorkDirectory(), this.config.getMaxTryCount(), this.config.getSocketTimeout(), this.config.getErrorTimeout(), this.config.getMinTimeout(), this);

            this.cancelling = new AtomicBoolean(false);

            createHttpClient();

            this.dataCounter = 0;

            this.log = LogFactory.getLog(getClass());
        } catch (Cache.CacheException e) {
            throw new AdapterException(e);
        }
    }

    public void start() {
        new OutputThread().start();
        new InputThread().start();

        this.log.debug("Http adapter started");
    }

    public void stop() {
        this.cache.clear();

        executor.shutdown();

        this.log.debug("Http adapter stopped. Received data count [ " + this.dataCounter
                + " ] Number of completed requests [ " + executor.getCompletedTaskCount() + " ]");
    }

    public long download(final AdapterRequest _request) {
        Assert.notNull(_request, "Request is null");
        long result = 0;

        try {
            this.input.put(_request);

            this.log.debug("Adapter request successfully put in queue");
        } catch (InterruptedException e) {
            result = -1;

            this.log.error("Error while put adapter request in queue");
        }

        return result;
    }

    public void cancel() {

        if (!this.cancelling.get()) {
            this.cancelling.set(true);

            new CancellerThread().start();
        }
    }

    public boolean cancelling() {
        return this.cancelling.get();
    }

    public int getActiveCount() {
        return executor.getActiveCount();
    }

    private void createHttpClient() {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

        connectionManager.getParams().setDefaultMaxConnectionsPerHost(this.config.getMaxConnectionsPerHost());
        connectionManager.getParams().setMaxTotalConnections(this.config.getMaxTotalConnections());

        this.client = new HttpClient(connectionManager);

        this.client.getParams().setParameter(HttpMethodParams.USER_AGENT, this.config.getUserAgent());
        this.client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        if (this.config.isProxyUsing()) {
            this.client.getHostConfiguration().setProxy(this.config.getProxyHost(), this.config.getProxyPort());
            this.client.getParams().setAuthenticationPreemptive(true);
            Credentials defaultCreds = new UsernamePasswordCredentials(this.config.getUserName(), this.config.getUserPassword());
            this.client.getState().setProxyCredentials(AuthScope.ANY, defaultCreds);
        }
    }

    private HttpAdapterThread createThreadForRequest(final AdapterRequest _request) {
        return new HttpAdapterThread(this.client, _request, this.threadConfig);
    }

    private boolean hostBanned(final String _host) {
        return _host.length() != 0 && this.banned.isBanned(_host);
    }

    private synchronized void putToCache(final Container _container) {
        this.log.debug("Before put to cache data from address [ " + _container.getRequest().getAddress() + " ]");

        String address = ((DataFile) _container.getData()).getAddress();
        String charset = _container.getData().getCharsetName();

        this.cache.put(_container.getRequest().getAddress(), _container.getRequest().getResponseUrl(), address, charset);

        this.log.debug("Data from address [ " + _container.getRequest().getAddress() + " ] put to cache");
    }

    private void complain(final Container _container) {
        String host = HttpAdapterTools.getHost(_container.getRequest().getAddress());

        if (!host.isEmpty()) {
            this.banned.complain(host);
            this.log.debug("Complain to host [ " + host + " ]");
        }
    }

    private class Container {
        private final AdapterRequest request;
        private final Data data;
        private final Result result;
        private final boolean notCached;

        public Container(final HttpAdapterThread _thread) {
            Assert.notNull(_thread, "Thread is null");

            this.request = _thread.getRequest();
            this.data = _thread.getData();
            this.result = _thread.getResult();

            this.notCached = true;
        }

        public Container(final AdapterRequest _request, final Data _data, final Result _result) {
            Assert.notNull(_request, "Request is null");
            this.request = _request;

            Assert.notNull(_data, "Data is null");
            this.data = _data;

            Assert.notNull(_result, "Result is null");
            this.result = _result;

            this.notCached = false;
        }

        public Data getData() {
            return this.data;
        }

        public AdapterRequest getRequest() {
            return this.request;
        }

        public Result getResult() {
            return this.result;
        }

        public boolean notCached() {
            return this.notCached;
        }
    }

    private class Executor extends ThreadPoolExecutor {

        public Executor() {
            super(config.getCorePoolSize(), config.getMaxPoolSize(), config.getKeepAliveTime(), TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }

        protected void afterExecute(final Runnable _runnable, final Throwable _throwable) {
            super.afterExecute(_runnable, _throwable);

            try {
                output.put(new Container((HttpAdapterThread) _runnable));
                threads.remove(_runnable);

                log.debug("Balancer [ " + threads.size() + " ]");
                log.debug("Thread put to finished queue");
            } catch (InterruptedException e) {
                log.error("Unexpected error while put thread in finished queue", e);
            }
        }
    }
}
