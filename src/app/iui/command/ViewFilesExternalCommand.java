package app.iui.command;

import app.iui.tools.ExternalToolsUtils;
import util.Assert;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 11.12.2010
 */
public class ViewFilesExternalCommand implements Command {

    private final List<String> files;
    private final String pathToViewer;
    private final boolean oneViewerPerFile;
    private final Listener listener;

    public ViewFilesExternalCommand(final List<String> _files, final String _pathToViewer, final boolean _oneViewerPerFile, final Listener _listener) {
        Assert.notNull(_files, "File list is null");
        this.files = _files;

        Assert.isValidString(_pathToViewer, "Path to external viewer is invalid");
        this.pathToViewer = _pathToViewer;

        this.oneViewerPerFile = _oneViewerPerFile;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;
    }

    public void execute() {

        try {

            if (!(new File(this.pathToViewer).exists())) {
                throw new ViewFilesExternalCommandException("Error starting external viewer [ " + this.pathToViewer + " ]. File not exists");
            }

            if (this.oneViewerPerFile) {
                ExternalToolsUtils.startOneProcessPerFile(this.pathToViewer, this.files);
            } else {
                ExternalToolsUtils.startOneProcessForAllFiles(this.pathToViewer, this.files);
            }

            this.listener.onComplete(this);
        } catch (Throwable e) {
            this.listener.onFault(this, e);
        }
    }

    public String toString() {
        StringBuilder list = new StringBuilder();

        for (String current : this.files) {
            list.append(current).append(", ");
        }

        return MessageFormat.format("iui_ViewFilesExternal({0}, {1})", this.files.size(), list);
    }

    public interface Listener {
        void onComplete(ViewFilesExternalCommand _command);

        void onFault(ViewFilesExternalCommand _command, Throwable _cause);
    }

    public class ViewFilesExternalCommandException extends Exception {

        public ViewFilesExternalCommandException() {
        }

        public ViewFilesExternalCommandException(final String _s) {
            super(_s);
        }

        public ViewFilesExternalCommandException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public ViewFilesExternalCommandException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
