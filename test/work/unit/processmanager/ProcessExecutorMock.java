package work.unit.processmanager;

import app.workingarea.ProcessWrapper;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 24.11.2009
 */
public class ProcessExecutorMock implements ProcessWrapper {

    private int reply;

    public ProcessExecutorMock() {
        this.reply = PROCESS_TIMEOUT;
    }

    public int call(String _id, String _cmd, long _wait) {
        Assert.isValidString(_id, "Process id is invalid");
        Assert.notNull(_cmd, "Command line is null");
        Assert.greaterOrEqual(_wait, WAIT_FOREVER, "Wait time < WAIT_FOREVER");

        return this.reply;
    }

    public void setReply(final int _reply) {
        this.reply = _reply;
    }
}
