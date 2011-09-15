package downloader;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public interface HttpRequestHandler {

    Data get(String request, String referer);

    Data post(String url, String referer);
}
