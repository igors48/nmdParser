package work.unit.fb2.resource;

import util.Assert;

import java.util.TimerTask;

/**
 * @author Igor Usenko
 *         Date: 04.10.2008
 */
public class DownloaderMockTimerTask extends TimerTask {

    private final DownloaderMock mock;

    public DownloaderMockTimerTask(DownloaderMock _mock){
        Assert.notNull(_mock);

        this.mock = _mock;
    }

    public void run() {
        this.mock.flush();
    }
}
