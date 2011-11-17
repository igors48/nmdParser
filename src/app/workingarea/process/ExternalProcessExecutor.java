package app.workingarea.process;

import app.workingarea.ProcessWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static app.workingarea.process.StreamDumper.createErrorStreamDumper;
import static app.workingarea.process.StreamDumper.createOutputStreamDumper;

/**
 * Инициатор внешнего процесса
 *
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public class ExternalProcessExecutor implements ProcessWrapper {

    private final Log log;
    private final String separator;

    private static final String SPACE_TOKEN = " ";
    private static final String BACKSLASH = "\\";
    private static final String SLASH = "/";

    public ExternalProcessExecutor() {
        this.separator = System.getProperty("file.separator");

        this.log = LogFactory.getLog(getClass());
    }

    public int call(final String _id, final String _cmd, long _wait) {
        Assert.isValidString(_id, "Process id is invalid");
        Assert.notNull(_cmd, "Command line is null");
        Assert.greaterOrEqual(_wait, WAIT_FOREVER, "Wait time < WAIT_FOREVER");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        int result = PROCESS_DONT_RUN;
        Process process = null;

        try {
            process = createProcess(_id, _cmd);

            startStreamDumpers(process);

            Future<Integer> future = executor.submit(getCallableWrapper(process));

            result = _wait == WAIT_FOREVER ? future.get() : future.get(_wait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            this.log.error("Error executing external process", e);
        } catch (ExecutionException e) {
            this.log.error("Error executing external process", e);
        } catch (TimeoutException e) {
            this.log.error("Error executing external process", e);
            kill(process);
            result = PROCESS_TIMEOUT;
        } catch (IOException e) {
            this.log.error("Error executing external process", e);
        } finally {
            shutdown(executor);
        }

        return result;
    }

    private void startStreamDumpers(final Process _process) {
        createOutputStreamDumper(_process.getInputStream()).start();
        createErrorStreamDumper(_process.getErrorStream()).start();
    }

    private void shutdown(final ExecutorService executor) {

        if (executor != null) {
            executor.shutdown();
        }
    }

    private void kill(final Process _process) {

        if (_process != null) {
            _process.destroy();
        }
    }

    private Callable<Integer> getCallableWrapper(final Process _process) {

        return new Callable<Integer>() {

            public Integer call() {
                int result = PROCESS_DONT_RUN;

                try {
                    result = _process.waitFor();
                } catch (Throwable e) {
                    log.error("Error executing external process", e);
                }

                return result;
            }
        };
    }

    private Process createProcess(final String _id, final String _cmd) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(createArguments(_id, _cmd));

        return builder.start();
    }

    private List<String> createArguments(final String _id, final String _cmd) {
        List<String> result = new ArrayList<String>();

        result.add(correctSeparator(_id));

        for (String current : _cmd.split(SPACE_TOKEN)) {
            result.add(correctSeparator(current));
        }

        return result;
    }

    private String correctSeparator(final String _argument) {
        return this.separator.equals(BACKSLASH) ? reverseSlash(_argument) : _argument;
    }

    private String reverseSlash(final String _string) {
        String result = _string;

        while (result.indexOf(SLASH) != -1) {
            result = result.replace(SLASH, BACKSLASH);
        }

        return result;
    }
}
