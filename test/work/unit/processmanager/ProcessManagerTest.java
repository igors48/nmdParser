package work.unit.processmanager;

import app.workingarea.ProcessWrapper;
import app.workingarea.process.ExternalProcessManager;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 24.11.2009
 */
public class ProcessManagerTest extends TestCase {

    public ProcessManagerTest(final String _s) {
        super(_s);
    }

    // процесс превысивший предел отказов идет в бан

    public void testSmoke() {
        ProcessExecutorMock mock = new ProcessExecutorMock();
        ExternalProcessManager manager = new ExternalProcessManager(3, mock);

        assertEquals(ProcessWrapper.PROCESS_TIMEOUT, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_TIMEOUT, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_TIMEOUT, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_DONT_RUN, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
    }

    // процесс ведуший себя нормально вызывается, а когда лажает больше предела идет в бан

    public void testNormalThenBan() {
        ProcessExecutorMock mock = new ProcessExecutorMock();
        mock.setReply(ProcessWrapper.PROCESS_OK);

        ExternalProcessManager manager = new ExternalProcessManager(3, mock);

        assertEquals(ProcessWrapper.PROCESS_OK, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_OK, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_OK, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));

        mock.setReply(ProcessWrapper.PROCESS_TIMEOUT);

        assertEquals(ProcessWrapper.PROCESS_TIMEOUT, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_TIMEOUT, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_TIMEOUT, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
        assertEquals(ProcessWrapper.PROCESS_DONT_RUN, manager.call("1", "2", ProcessWrapper.WAIT_FOREVER));
    }

}
