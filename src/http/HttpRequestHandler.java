package http;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public interface HttpRequestHandler {

    Callable<HttpGetRequest> get(HttpGetRequest _request);

   // HttpResponse post(String _request, String _referer);
    
    void stop();
}
