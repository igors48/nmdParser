package downloader;

/**
 * @author Igor Usenko
 *         Date: 25.09.2008
 */
public interface Listener {
    void requestDone(long _id, Result _result, Data _data);

    void cancellingFinished();
}
