package app.iui;

/**
 * Интерфейс работы с установками ГУИ
 *
 * @author Igor Usenko
 *         Date: 13.04.2010
 */
public interface GuiSettings {

    int getX();

    void setX(int _x);

    int getY();

    void setY(int _y);

    int getHeight();

    void setHeight(int _height);

    int getWidth();

    void setWidth(int _width);

    String getPathToExternalEditor();

    void setPathToExternalEditor(String _pathToEditor);

    void setOneEditorPerOneFileMode(boolean _value);

    boolean getOneEditorPerOneFileMode();

    String getPathToExternalFb2Viewer();

    void setPathToExternalFb2Viewer(String _pathToViewer);

    void setOneFb2ViewerPerOneFileMode(boolean _value);

    boolean getOneFb2ViewerPerOneFileMode();

    void setShowExternalToolsDialogAtStartup(boolean _value);

    boolean showExternalToolsWarning();

    String getStartupWorkspaceName();

    void setStartupWorkspaceName(String _workspaceName);

    boolean allExternalToolsAvailable();

    boolean externalFb2ViewerAvailable();

    boolean externalEditorAvailable();

    String getExternalPostprocessorPath();

    void setExternalPostprocessorPath(String _path);

    String getExternalPostprocessorCommandLinePattern();

    void setExternalPostprocessorCommandLinePattern(String _pattern);

    long getExternalPostprocessorTimeout();

    void setExternalPostprocessorTimeout(long _timeout);

    boolean isExternalPostprocessorEnabled();

    void setExternalPostprocessorEnabled(boolean _value);

    boolean externalPostprocessorAvailable();

    public class GuiSettingsException extends Exception {

        public GuiSettingsException() {
        }

        public GuiSettingsException(final String _s) {
            super(_s);
        }

        public GuiSettingsException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public GuiSettingsException(final Throwable _throwable) {
            super(_throwable);
        }
    }

}
