package app.workingarea.process;

import app.workingarea.ProcessWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Стандартный менеджер внешних процессов
 *
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public class ExternalProcessManager implements ProcessWrapper {

    private final int maxFailCount;
    private final ProcessWrapper executor;

    private final Map<String, ProcessStatistic> statistic;

    private final Log log;

    public ExternalProcessManager(final int _maxFailCount, final ProcessWrapper _executor) {
        Assert.greaterOrEqual(_maxFailCount, 0, "Max fail count < 0");
        this.maxFailCount = _maxFailCount;

        Assert.notNull(_executor, "Executor is null");
        this.executor = _executor;

        this.statistic = new HashMap<String, ProcessStatistic>();

        this.log = LogFactory.getLog(getClass());
    }

    public int call(final String _id, final String _cmd, final long _wait) {
        Assert.isValidString(_id, "Process id is invalid");
        Assert.notNull(_cmd, "Command line is null");
        Assert.greaterOrEqual(_wait, WAIT_FOREVER, "Wait time < WAIT_FOREVER");

        int result = PROCESS_DONT_RUN;

        ProcessStatistic statistic = getStatistic(_id);

        if (mayCalled(statistic)) {
            result = this.executor.call(_id, _cmd, _wait);
            updateStatistic(result, statistic);
            this.log.info("Process finished with exit code [ " + result + " ].");
        } else {
            this.log.info("Process [ " + _id + " ] can not be exectuted because it is banned");
        }

        return result;
    }

    private void updateStatistic(final int _result, final ProcessStatistic _statistic) {

        if (_result == PROCESS_OK) {
            _statistic.succeded();
        } else {
            _statistic.failed();
        }
    }

    private boolean mayCalled(final ProcessStatistic _statistic) {
        return _statistic.getFailed() < this.maxFailCount;
    }

    private ProcessStatistic getStatistic(final String _id) {
        ProcessStatistic result = this.statistic.get(_id);

        if (result == null) {
            result = new ProcessStatistic();
            this.statistic.put(_id, result);
        }

        return result;
    }

}
