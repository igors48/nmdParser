package work.unit.fb2.resource;

import downloader.Downloader;
import downloader.RequestList;

import java.util.HashMap;
import java.util.Map;

import html.HttpData;

/**
 * @author Igor Usenko
 *         Date: 04.10.2008
 */
public class DownloaderMock implements Downloader {

    private final Map<Long, RequestList> list;

    public DownloaderMock() {
        this.list = new HashMap<Long, RequestList>();
    }

    public void start() throws DownloaderException {
    }

    public void stop() {
    }

    public long download(RequestList _requestList) {
        long id = System.nanoTime();
        _requestList.setId(id);
        this.list.put(id, _requestList);

        return id;
    }

    public void cancel() {
    }

    public Map<Long, RequestList> getMap(){
        return this.list;
    }

    public void flush(){
                
        for(Map.Entry<Long, RequestList> entry : this.list.entrySet()){
            entry.getValue().getListener().requestDone(entry.getKey(), HttpData.EMPTY_DATA);
        }
    }
}
