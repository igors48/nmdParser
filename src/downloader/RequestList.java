package downloader;

import util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 01.10.2008
 */
public class RequestList {

    private final List<Request> requests;
    private final long pauseBetweenRequests;
    private DownloaderListener listener;

    private long id;

    //TODO –азобратьс€ с таким количеством конструкторов

    public RequestList(final DownloaderListener _listener, final List<Request> _requests) {
        Assert.notNull(_listener, "Listener is null");
        Assert.notNull(_requests, "Requests is null");
        Assert.greater(_requests.size(), 0, "Requests size is 0");

        this.listener = _listener;
        this.requests = _requests;
        this.pauseBetweenRequests = 0;
    }

    public RequestList(final List<Request> _requests, final long _pauseBetweenRequests) {
        Assert.notNull(_requests, "Requests is null");
        Assert.greater(_requests.size(), 0, "Requests size is 0");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        this.listener = null;
        this.requests = _requests;
        this.pauseBetweenRequests = _pauseBetweenRequests;
    }

    public RequestList(final Request _request, final long _pauseBetweenRequests) {
        this(Arrays.asList(_request), _pauseBetweenRequests);
    }

    public DownloaderListener getListener() {
        return this.listener;
    }

    public void setListener(final DownloaderListener _listener) {
        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;
    }

    public List<Request> getRequests() {
        return this.requests;
    }

    public int getSize() {
        return this.requests.size();
    }

    public Request get(final int _index) {
        Assert.greaterOrEqual(_index, 0, "Index < 0");
        return this.requests.get(_index);
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long _id) {
        Assert.greater(_id, 0, "id <= 0");
        this.id = _id;
    }

    public long getPauseBetweenRequests() {
        return this.pauseBetweenRequests;
    }
}
