package http;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class HttpResponse {

    private final Data data;
    private final Result result;
    private final String responseUrl;

    public HttpResponse(final Data _data, final Result _result, final String _responseUrl) {
        Assert.notNull(_data, "Data is null");
        this.data = _data;

        Assert.notNull(_result, "Result is null");
        this.result = _result;

        Assert.isValidString(_responseUrl, "Response URL is not valid");
        this.responseUrl = _responseUrl;
    }

    public Data getData() {
        return this.data;
    }

    public Result getResult() {
        return this.result;
    }

    public String getResponseUrl() {
        return this.responseUrl;
    }

}
