package downloader;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public class AdapterRequest {
    private final Request request;
    private final long id;

    public AdapterRequest(long _id, Request _request) {
        Assert.notNull(_request);
        Assert.greater(_id, 0, "");

        this.id = _id;
        this.request = _request;
    }

    public long getId() {
        return this.id;
    }

    public String getAddress() {
        return this.request.getAddress();
    }

    public String getReferer() {
        return this.request.getReferer();
    }

    public Destination getDestination() {
        return this.request.getDestination();
    }

    public String getResponseUrl() {
        return this.request.getResponseUrl();
    }

    public void setResponseUrl(final String _responseUrl) {
        Assert.isValidString(_responseUrl, "Response URL is not valid");
        this.request.setResponseUrl(_responseUrl);
    }

    public String getDescription() {
        return "Address [ " + getAddress() + " ] Destination [ " + getDestination() + " ] Response URL [ " + getResponseUrl() + " ]";
    }
}
