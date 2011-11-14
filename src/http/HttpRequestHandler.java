package http;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public interface HttpRequestHandler {

    Callable<HttpRequest> get(HttpRequest _request);

    Callable<HttpRequest> post(HttpRequest _request);

    void stop();
}
