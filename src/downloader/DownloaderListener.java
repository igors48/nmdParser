package downloader;

import html.HttpData;

/**
 * @author Igor Usenko
 *         Date: 25.09.2008
 */
public interface DownloaderListener {
    void requestDone(long _id, HttpData _data);
}