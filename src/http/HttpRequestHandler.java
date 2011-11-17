package http;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public interface HttpRequestHandler {

    Callable<HttpRequest> get(HttpRequest _request);

    Callable<HttpRequest> getSecured(HttpSecureRequest _request);

    Callable<HttpRequest> postSecured(HttpSecureRequest _request);

    void stop();
}
