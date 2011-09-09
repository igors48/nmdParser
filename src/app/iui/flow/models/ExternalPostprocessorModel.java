package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import app.workingarea.ProcessWrapper;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.04.2011
 */
public class ExternalPostprocessorModel extends Model {

    private boolean externalPostprocessingEnabled;
    private String pathToExternalPostprocessor;
    private String commandLinePattern;
    private long externalPostprocessingTimeout;

    public ExternalPostprocessorModel(final boolean externalPostprocessingEnabled, final String pathToExternalPostprocessor, final String commandLinePattern, final long externalPostprocessingTimeout) {
        super(ModelType.EXTERNAL_POSTPROCESSOR_SETTINGS);

        this.externalPostprocessingEnabled = externalPostprocessingEnabled;
        this.pathToExternalPostprocessor = pathToExternalPostprocessor;
        this.commandLinePattern = commandLinePattern;
        this.externalPostprocessingTimeout = externalPostprocessingTimeout;
    }

    public String getCommandLinePattern() {
        return this.commandLinePattern;
    }

    public void setCommandLinePattern(final String _commandLinePattern) {
        Assert.notNull(_commandLinePattern, "Commandline pattern is null");
        this.commandLinePattern = _commandLinePattern;
    }

    public boolean isExternalPostprocessingEnabled() {
        return this.externalPostprocessingEnabled;
    }

    public void setExternalPostprocessingEnabled(final boolean _externalPostprocessingEnabled) {
        this.externalPostprocessingEnabled = _externalPostprocessingEnabled;
    }

    public long getExternalPostprocessingTimeout() {
        return this.externalPostprocessingTimeout;
    }

    public void setExternalPostprocessingTimeout(final long _externalPostprocessingTimeout) {
        Assert.greaterOrEqual(_externalPostprocessingTimeout, ProcessWrapper.WAIT_FOREVER, "Timeout < WAIT_FOREVER");
        this.externalPostprocessingTimeout = _externalPostprocessingTimeout;
    }

    public String getPathToExternalPostprocessor() {
        return this.pathToExternalPostprocessor;
    }

    public void setPathToExternalPostprocessor(final String _pathToExternalPostprocessor) {
        Assert.notNull(_pathToExternalPostprocessor, "Path to postprocessor is null");
        this.pathToExternalPostprocessor = _pathToExternalPostprocessor;
    }
}
