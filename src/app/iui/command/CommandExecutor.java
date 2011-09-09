package app.iui.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Igor Usenko
 *         Date: 03.05.2010
 */
public class CommandExecutor {

    private static final String EXECUTION_THREAD_NAME = "CommandExecutionThread";

    private final AtomicInteger balancer;
    private final ExitSignalListener listener;

    private final Log log = LogFactory.getLog(getClass());

    private class ExecutionThread extends Thread {

        private Command command;

        private ExecutionThread(final Command _command) {
            super(EXECUTION_THREAD_NAME);

            this.command = _command;
            setDaemon(true);
        }

        public void run() {

            try {
                balancer.incrementAndGet();

                this.command.execute();
            } catch (Throwable t) {
                log.error("Uncaught exception", t);

                listener.onFailureExit(t);
            } finally {
                balancer.decrementAndGet();
                log.debug(MessageFormat.format("Execution of command [ {0} ] ended", command.toString()));
            }
        }
    }

    public CommandExecutor(final ExitSignalListener _listener) {
        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        this.balancer = new AtomicInteger(0);
    }

    public boolean execute(final Command _command, final boolean _rejectIfBusy) {
        Assert.notNull(_command, "Command is null");

        if (_rejectIfBusy && this.balancer.get() != 0) {
            this.log.info(MessageFormat.format("Reject command [ {0} ]. Balancer is [ {1} ]", _command, this.balancer.get()));

            return false;
        } else {
            this.log.info(MessageFormat.format("Execute command [ {0} ]. Balancer is [ {1} ]", _command, this.balancer.get()));

            new ExecutionThread(_command).start();

            return true;
        }
    }
}
