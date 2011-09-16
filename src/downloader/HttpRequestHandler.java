package downloader;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public interface HttpRequestHandler {

    void get(HttpGetRequest _request);

    void cancel();
   // HttpResponse post(String _request, String _referer);
    
}
