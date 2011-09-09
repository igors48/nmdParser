package downloader;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 25.09.2008
 */
public class Request {

    private final String address;
    private final Destination destination;
    private final String referer;

    /**
     * адрес с которого пришел ответ - feed-proxy посвящается
     */
    private String responseUrl;

    public Request(final String _address, final String _referer, final Destination _destination) {
        Assert.notNull(_address, "Address is null");
        Assert.notNull(_referer, "Referer is null");
        Assert.notNull(_destination, "Destination is null");

        this.address = _address;
        this.referer = _referer;
        this.destination = _destination;

        this.responseUrl = _address;
    }

    public Request(final String _address, final Destination _destination) {
        this(_address, "", _destination);
    }

    public String getAddress() {
        return this.address;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public String getReferer() {
        return this.referer;
    }

    public String getResponseUrl() {
        return this.responseUrl;
    }

    public void setResponseUrl(final String _responseUrl) {
        Assert.isValidString(_responseUrl, "Response URL is not valid");
        this.responseUrl = _responseUrl.trim();
    }

    public String getDescription() {
        return "Address : " + this.address + " Destination : " + this.getDestination();
    }
}
