package constructor.objects.output.core.forEach;

import app.workingarea.ProcessWrapper;
import app.workingarea.process.ProcessManagerUtils;
import constructor.objects.output.core.ForEachPostProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.PathTools;

import java.util.HashMap;
import java.util.Map;

/**
 * Процессор постобработки, вызывающий внешний процесс для реализации своих намерений
 *
 * @author Igor Usenko
 *         Date: 25.11.2009
 */
public class ExternalForEachPostProcessor implements ForEachPostProcessor {

    private final ProcessWrapper wrapper;
    private final String path;
    private final String pattern;
    private final long timeout;

    private final Log log;

    public ExternalForEachPostProcessor(final ProcessWrapper _wrapper, final String _path, final String _pattern, final long _timeout) {
        Assert.notNull(_wrapper, "Process wrapper is null");
        this.wrapper = _wrapper;

        Assert.isValidString(_path, "Path is not valid");
        this.path = _path;

        Assert.isValidString(_pattern, "Pattern is not valid");
        this.pattern = _pattern;

        Assert.greaterOrEqual(_timeout, -1, "Timeout < -1");
        this.timeout = _timeout;

        this.log = LogFactory.getLog(getClass());
    }

    public void process(final String _dir, final String _name) {
        Assert.notNull(_dir, "Directory is null");
        Assert.isValidString(_name, "Name is null");

        Map<String, String> parms = new HashMap<String, String>();

        parms.put(ProcessManagerUtils.DIRECTORY_KEY, PathTools.normalize(_dir));
        parms.put(ProcessManagerUtils.FULL_FILE_NAME_KEY, PathTools.normalize(_dir) + _name);
        parms.put(ProcessManagerUtils.FILE_NAME_KEY, _name);

        String cmdLine = ProcessManagerUtils.createCommandLine(this.pattern, parms);

        this.log.info("Try to execute external process [ " + this.path + " ] with command line [ " + cmdLine + " ] and timeout [ " + this.timeout + " ]");
        this.wrapper.call(this.path, cmdLine, this.timeout);
    }
}
