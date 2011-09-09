package downloader.hostadapter;

import downloader.Adapter;
import downloader.AdapterRequest;
import downloader.Listener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class HostAdapter extends ThreadPoolExecutor implements Adapter {

    private final Log log;
    private final Listener listener;

    private long dataCounter;

    public HostAdapter(Listener _listener, HostAdapterConfig _config) {
        super(_config.getCorePoolSize(), _config.getMaxPoolSize(),
                _config.getKeepAliveTime(), TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        Assert.notNull(_listener);
        Assert.notNull(_config);

        this.listener = _listener;
        this.log = LogFactory.getLog(getClass());
    }

    public synchronized void start() {
        setDataCounter(0);
        this.log.debug("Host adapter started.");
    }

    public synchronized void stop() {
        shutdown();
        this.log.debug("Host adapter stopped. Received data count [ " + getDataCounter()
                + " ] Number of completed requests [ " + this.getCompletedTaskCount() + " ].");
    }

    public synchronized long download(AdapterRequest _request) {
        Assert.notNull(_request);

        HostAdapterThread thread = new HostAdapterThread(_request);
        execute(thread);

        this.log.debug("Request put in queue [ " + _request.getDescription() + " ] Queue size [ " + getTaskCount() + " ].");
        return 0;
    }

    public void cancel() {
        //List<Runnable> notExecuted = shutdownNow();

        this.log.debug("Host adapter cancelled.");
        this.listener.cancellingFinished();
    }

    protected void afterExecute(Runnable _runnable, Throwable _throwable) {
        super.afterExecute(_runnable, _throwable);

        HostAdapterThread thread = (HostAdapterThread) _runnable;
        incDataCounter(thread.getData().size());

        this.log.debug("Request done [ " + thread.getRequestDescription() + " ] Queue size [ " + getTaskCount() + " ].");

        this.listener.requestDone(thread.getRequestId(), thread.getResult(), thread.getData());
    }

    private synchronized long getDataCounter() {
        return dataCounter;
    }

    private synchronized void setDataCounter(final long _dataCounter) {
        this.dataCounter = _dataCounter;
    }

    private synchronized void incDataCounter(final long _increment) {
        this.dataCounter += _increment;
    }


}
