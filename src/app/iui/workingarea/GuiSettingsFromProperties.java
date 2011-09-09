package app.iui.workingarea;

import app.iui.GuiSettings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.io.File;
import java.util.Properties;

/**
 * Установки ГУИ приложения хранящиеся в пропертях
 *
 * @author Igor Usenko
 *         Date: 11.04.2010
 */
public class GuiSettingsFromProperties implements GuiSettings {

    private static final String X_KEY = "gui.window.x";
    private static final String X_DEFAULT = "10";
    private static final String Y_KEY = "gui.window.y";
    private static final String Y_DEFAULT = "10";
    private static final String WIDTH_KEY = "gui.window.width";
    private static final String WIDTH_DEFAULT = "200";
    private static final String HEIGHT_KEY = "gui.window.height";
    private static final String HEIGHT_DEFAULT = "200";
    private static final String PATH_TO_EDITOR_KEY = "gui.editor.path";
    private static final String PATH_TO_EDITOR_DEFAULT = " ";
    private static final String ONE_EDITOR_PER_FILE_KEY = "gui.editor.one.per.file";
    private static final String TRUE_TOKEN = "true";
    private static final String ONE_EDITOR_PER_FILE_DEFAULT = TRUE_TOKEN;
    private static final String PATH_TO_FB2_VIEWER_KEY = "gui.fb2.viewer.path";
    private static final String PATH_TO_FB2_VIEWER_EDITOR_DEFAULT = " ";
    private static final String ONE_FB2_VIEWER_PER_FILE_KEY = "gui.fb2.viewer.one.per.file";
    private static final String ONE_FB2_VIEWER_PER_FILE_DEFAULT = TRUE_TOKEN;
    private static final String SHOW_EXTERNAL_TOOLS_WARNING_KEY = "gui.show.external.tools.warning";
    private static final String SHOW_EXTERNAL_TOOLS_WARNING_DEFAULT = TRUE_TOKEN;
    private static final String STARTUP_WORKSPACE_NAME_KEY = "gui.startup.workspace.name";
    private static final String STARTUP_WORKSPACE_NAME_DEFAULT = "";

    private static final String EXTERNAL_POSTPROCESSOR_PATH_KEY = "gui.external.postprocessor.path";
    private static final String EXTERNAL_POSTPROCESSOR_PATH_DEFAULT = "";
    private static final String EXTERNAL_POSTPROCESSOR_COMMAND_LINE_PATTERN_KEY = "gui.external.postprocessor.command.line.pattern";
    private static final String EXTERNAL_POSTPROCESSOR_COMMAND_LINE_PATTERN_DEFAULT = "";
    private static final String EXTERNAL_POSTPROCESSOR_TIMEOUT_KEY = "gui.external.postprocessor.timeout";
    private static final String EXTERNAL_POSTPROCESSOR_TIMEOUT_DEFAULT = "-1";
    private static final String EXTERNAL_POSTPROCESSOR_ENABLED_KEY = "gui.external.postprocessor.enabled";
    private static final String EXTERNAL_POSTPROCESSOR_ENABLED_DEFAULT = "false";

    private static final String APPLICATION_DEFAULTS_TITLE = "GUI application defaults";
    private static final String DIVIDER_STRING = " ======= ";

    private final Properties properties;

    private int x;
    private int y;
    private int width;
    private int height;

    private String pathToExternalEditor;
    private boolean oneExternalEditorPerFile;
    private String pathToExternalFb2Viewer;
    private boolean oneExternalFb2ViewerPerFile;

    private boolean showExternalToolsWarning;

    private String startupWorkspaceName;

    private String externalPostprocessorPath;
    private String externalPostprocessorCommandLinePattern;
    private long externalPostprocessorTimeout;
    private boolean externalPostprocessorEnabled;

    private final Log log;

    public GuiSettingsFromProperties(final Properties _properties) throws GuiSettingsException {
        Assert.notNull(_properties, "Properties is null");
        this.properties = _properties;

        this.log = LogFactory.getLog(getClass());

        mapProperties();
        logProperties();
    }

    public Properties getProperties() {
        Properties result = new Properties();

        result.setProperty(X_KEY, String.valueOf(this.x));
        result.setProperty(Y_KEY, String.valueOf(this.y));
        result.setProperty(WIDTH_KEY, String.valueOf(this.width));
        result.setProperty(HEIGHT_KEY, String.valueOf(this.height));
        result.setProperty(PATH_TO_EDITOR_KEY, this.pathToExternalEditor);
        result.setProperty(PATH_TO_FB2_VIEWER_KEY, this.pathToExternalFb2Viewer);
        result.setProperty(ONE_EDITOR_PER_FILE_KEY, String.valueOf(this.oneExternalEditorPerFile));
        result.setProperty(ONE_FB2_VIEWER_PER_FILE_KEY, String.valueOf(this.oneExternalFb2ViewerPerFile));
        result.setProperty(SHOW_EXTERNAL_TOOLS_WARNING_KEY, String.valueOf(this.showExternalToolsWarning));
        result.setProperty(STARTUP_WORKSPACE_NAME_KEY, this.startupWorkspaceName);

        result.setProperty(EXTERNAL_POSTPROCESSOR_PATH_KEY, this.externalPostprocessorPath);
        result.setProperty(EXTERNAL_POSTPROCESSOR_COMMAND_LINE_PATTERN_KEY, this.externalPostprocessorCommandLinePattern);
        result.setProperty(EXTERNAL_POSTPROCESSOR_TIMEOUT_KEY, String.valueOf(this.externalPostprocessorTimeout));
        result.setProperty(EXTERNAL_POSTPROCESSOR_ENABLED_KEY, String.valueOf(this.externalPostprocessorEnabled));

        return result;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int _height) {
        Assert.greater(_height, 0, "Height <= 0");
        this.height = _height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(final int _width) {
        Assert.greater(_width, 0, "Width <= 0");
        this.width = _width;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int _x) {
        this.x = _x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int _y) {
        this.y = _y;
    }

    public String getPathToExternalEditor() {
        return this.pathToExternalEditor;
    }

    public void setPathToExternalEditor(final String _pathToEditor) {
        Assert.notNull(_pathToEditor, "Path to editor is null");
        this.pathToExternalEditor = _pathToEditor;
    }

    public void setOneEditorPerOneFileMode(boolean _value) {
        this.oneExternalEditorPerFile = _value;
    }

    public boolean getOneEditorPerOneFileMode() {
        return this.oneExternalEditorPerFile;
    }

    public String getPathToExternalFb2Viewer() {
        return this.pathToExternalFb2Viewer;
    }

    public void setPathToExternalFb2Viewer(String _pathToViewer) {
        Assert.notNull(_pathToViewer, "Path to viewer is null");
        this.pathToExternalFb2Viewer = _pathToViewer;
    }

    public void setOneFb2ViewerPerOneFileMode(boolean _value) {
        this.oneExternalFb2ViewerPerFile = _value;
    }

    public boolean getOneFb2ViewerPerOneFileMode() {
        return this.oneExternalFb2ViewerPerFile;
    }

    public void setShowExternalToolsDialogAtStartup(boolean _value) {
        this.showExternalToolsWarning = _value;
    }

    public boolean showExternalToolsWarning() {
        return this.showExternalToolsWarning;
    }

    public String getStartupWorkspaceName() {
        return this.startupWorkspaceName;
    }

    public void setStartupWorkspaceName(final String _workspaceName) {
        Assert.isValidString(_workspaceName, "Workspace name is invalid");

        this.startupWorkspaceName = _workspaceName;
    }

    public boolean allExternalToolsAvailable() {
        return externalFb2ViewerAvailable() && externalEditorAvailable();
    }

    public boolean externalFb2ViewerAvailable() {
        return new File(getPathToExternalFb2Viewer()).canExecute();
    }

    public boolean externalEditorAvailable() {
        return new File(getPathToExternalEditor()).canExecute();
    }

    public String getExternalPostprocessorPath() {
        return this.externalPostprocessorPath;
    }

    public void setExternalPostprocessorPath(final String _path) {
        Assert.notNull(_path, "Path is null");
        this.externalPostprocessorPath = _path;
    }

    public String getExternalPostprocessorCommandLinePattern() {
        return this.externalPostprocessorCommandLinePattern;
    }

    public void setExternalPostprocessorCommandLinePattern(final String _pattern) {
        Assert.notNull(_pattern, "Pattern is null");
        this.externalPostprocessorCommandLinePattern = _pattern;
    }

    public long getExternalPostprocessorTimeout() {
        return this.externalPostprocessorTimeout;
    }

    public void setExternalPostprocessorTimeout(final long _timeout) {
        Assert.greaterOrEqual(_timeout, -1, "Timeout < -1");
        this.externalPostprocessorTimeout = _timeout;
    }

    public boolean isExternalPostprocessorEnabled() {
        return this.externalPostprocessorEnabled;
    }

    public void setExternalPostprocessorEnabled(boolean _value) {
        this.externalPostprocessorEnabled = _value;
    }

    public boolean externalPostprocessorAvailable() {
        return this.externalPostprocessorEnabled && new File(getExternalPostprocessorPath()).canExecute();
    }

    private void mapProperties() throws GuiSettingsException {

        try {
            this.x = Integer.valueOf(this.properties.getProperty(X_KEY, X_DEFAULT));
            this.y = Integer.valueOf(this.properties.getProperty(Y_KEY, Y_DEFAULT));
            this.width = Integer.valueOf(this.properties.getProperty(WIDTH_KEY, WIDTH_DEFAULT));
            this.height = Integer.valueOf(this.properties.getProperty(HEIGHT_KEY, HEIGHT_DEFAULT));
            this.pathToExternalEditor = this.properties.getProperty(PATH_TO_EDITOR_KEY, PATH_TO_EDITOR_DEFAULT);
            this.oneExternalEditorPerFile = this.properties.getProperty(ONE_EDITOR_PER_FILE_KEY, ONE_EDITOR_PER_FILE_DEFAULT).equalsIgnoreCase(TRUE_TOKEN);
            this.pathToExternalFb2Viewer = this.properties.getProperty(PATH_TO_FB2_VIEWER_KEY, PATH_TO_FB2_VIEWER_EDITOR_DEFAULT);
            this.oneExternalFb2ViewerPerFile = this.properties.getProperty(ONE_FB2_VIEWER_PER_FILE_KEY, ONE_FB2_VIEWER_PER_FILE_DEFAULT).equalsIgnoreCase(TRUE_TOKEN);
            this.showExternalToolsWarning = this.properties.getProperty(SHOW_EXTERNAL_TOOLS_WARNING_KEY, SHOW_EXTERNAL_TOOLS_WARNING_DEFAULT).equalsIgnoreCase(TRUE_TOKEN);
            this.startupWorkspaceName = this.properties.getProperty(STARTUP_WORKSPACE_NAME_KEY, STARTUP_WORKSPACE_NAME_DEFAULT);

            this.externalPostprocessorPath = this.properties.getProperty(EXTERNAL_POSTPROCESSOR_PATH_KEY, EXTERNAL_POSTPROCESSOR_PATH_DEFAULT);
            this.externalPostprocessorCommandLinePattern = this.properties.getProperty(EXTERNAL_POSTPROCESSOR_COMMAND_LINE_PATTERN_KEY, EXTERNAL_POSTPROCESSOR_COMMAND_LINE_PATTERN_DEFAULT);
            this.externalPostprocessorTimeout = Long.valueOf(this.properties.getProperty(EXTERNAL_POSTPROCESSOR_TIMEOUT_KEY, EXTERNAL_POSTPROCESSOR_TIMEOUT_DEFAULT));
            this.externalPostprocessorEnabled = this.properties.getProperty(EXTERNAL_POSTPROCESSOR_ENABLED_KEY, EXTERNAL_POSTPROCESSOR_ENABLED_DEFAULT).equalsIgnoreCase(TRUE_TOKEN);

        } catch (Exception e) {
            throw new GuiSettingsException(e);
        }
    }

    private void logProperties() {
        this.log.debug(DIVIDER_STRING + APPLICATION_DEFAULTS_TITLE + DIVIDER_STRING);

        this.log.debug(X_KEY + "=" + this.x);
        this.log.debug(Y_KEY + "=" + this.y);
        this.log.debug(WIDTH_KEY + "=" + this.width);
        this.log.debug(HEIGHT_KEY + "=" + this.height);
        this.log.debug(PATH_TO_EDITOR_KEY + "=" + this.pathToExternalEditor);
        this.log.debug(ONE_EDITOR_PER_FILE_KEY + "=" + this.oneExternalEditorPerFile);
        this.log.debug(PATH_TO_FB2_VIEWER_KEY + "=" + this.pathToExternalFb2Viewer);
        this.log.debug(ONE_FB2_VIEWER_PER_FILE_KEY + "=" + this.oneExternalFb2ViewerPerFile);
        this.log.debug(SHOW_EXTERNAL_TOOLS_WARNING_KEY + "=" + this.showExternalToolsWarning);
        this.log.debug(STARTUP_WORKSPACE_NAME_KEY + "=" + this.startupWorkspaceName);

        this.log.debug(EXTERNAL_POSTPROCESSOR_PATH_KEY + "=" + this.externalPostprocessorPath);
        this.log.debug(EXTERNAL_POSTPROCESSOR_COMMAND_LINE_PATTERN_KEY + "=" + this.externalPostprocessorCommandLinePattern);
        this.log.debug(EXTERNAL_POSTPROCESSOR_TIMEOUT_KEY + "=" + this.externalPostprocessorTimeout);
        this.log.debug(EXTERNAL_POSTPROCESSOR_ENABLED_KEY + "=" + this.externalPostprocessorEnabled);
    }

}
