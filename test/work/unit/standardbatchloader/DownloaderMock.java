package work.unit.standardbatchloader;

import downloader.Downloader;
import downloader.RequestList;
import downloader.Result;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import html.HttpData;

/**
 * @author Igor Usenko
 *         Date: 04.04.2010
 */
public class DownloaderMock implements Downloader {

    private final Random random;
    private final List<RequestList> requests;
    
    private boolean cancelled;

    private enum Action {
        FLUSH,
        FLUSH_SOME_CANCELLED;
    }
    
    private class Invoker extends Thread {

        private final long delay;
        private final Action action;

        private Invoker(final long _delay, final Action _action) {
            this.delay = _delay;
            this.action = _action;
        }

        public void run() {

            try {
                sleep(this.delay);
                action();
            } catch (InterruptedException e) {
                // empty
            }
        }

        private void action() {
            switch (this.action) {
                case FLUSH : {
                    flush();
                    break;
                }
                case FLUSH_SOME_CANCELLED: {
                    flushSomeCancelled();
                    break;
                }
            }
        }
    }

    public DownloaderMock() {
        this.random = new Random(System.currentTimeMillis());
        this.requests = new ArrayList<RequestList>();
    }

    public void start() throws DownloaderException {
    }

    public void stop() {
    }

    public long download(RequestList _requestList) {
        long result = -1;

        if (this.cancelled) {
            sleepRandom(250);
        } else {
            sleepRandom(250);
            this.requests.add(_requestList);
            result = this.requests.size();
        }

        return result;
    }

    public void cancel() {
    }

    public void flushLater(final long _delay) {
        new Invoker(_delay, Action.FLUSH).start();
    }

    public void flushOneCancelledLater(final long _delay) {
        new Invoker(_delay, Action.FLUSH_SOME_CANCELLED).start();
    }

    public void setCancelled(final boolean _cancelled) {
        this.cancelled = _cancelled;
    }

    private void flush() {

        for (RequestList request : this.requests) {
            sleepRandom(2000);
            request.getListener().requestDone(this.requests.indexOf(request) + 1, new HttpData(request.get(0).getAddress(), request.get(0).getAddress(), Result.OK));
        }

        this.requests.clear();
    }
    
    private void flushSomeCancelled() {
        Result result = Result.OK;
        
        for (RequestList request : this.requests) {
            sleepRandom(2000);
            request.getListener().requestDone(this.requests.indexOf(request) + 1, new HttpData(request.get(0).getAddress(), request.get(0).getAddress(), result));

            result = result == Result.OK ? Result.CANCEL : Result.OK;
        }

        this.requests.clear();
    }

    private void sleepRandom(final int _max) {
        sleep(this.random.nextInt(_max));
    }

    private void sleep(final long _millis) {
        
        try {
            Thread.sleep(_millis);
        } catch (InterruptedException e) {
            // empty
        }
    }
}
