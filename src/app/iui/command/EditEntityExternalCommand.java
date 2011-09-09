package app.iui.command;

import app.iui.entity.Entity;
import app.iui.tools.ExternalToolsUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 31.05.2010
 */
public class EditEntityExternalCommand implements Command {

    private final Entity entity;
    private final String pathToEditor;
    private final boolean oneViewerPerFile;
    private final Listener listener;

    private final Log log;

    public EditEntityExternalCommand(final Entity _entity, final String _pathToEditor, final boolean _oneViewerPerFile, final Listener _listener) {
        Assert.notNull(_entity, "Entity is null");
        this.entity = _entity;

        Assert.isValidString(_pathToEditor, "Path to external editor is invalid");
        this.pathToEditor = _pathToEditor;

        this.oneViewerPerFile = _oneViewerPerFile;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() {

        try {
            if (!(new File(this.pathToEditor).exists())) {
                throw new EditEntityExternalCommandException("Error starting external editor [ " + this.pathToEditor + " ]. File not exists");
            }

            List<String> files = this.entity.getSourceFiles();

            if (this.oneViewerPerFile) {
                ExternalToolsUtils.startOneProcessPerFile(this.pathToEditor, files);
            } else {
                ExternalToolsUtils.startOneProcessForAllFiles(this.pathToEditor, files);
            }

            this.listener.onComplete(this);
        } catch (Throwable e) {
            this.log.error(e);
            this.listener.onFault(this, e);
        }
    }

    public String toString() {
        return MessageFormat.format("editEntityExternal({0})", this.entity.getName());
    }

    public interface Listener {
        void onComplete(EditEntityExternalCommand _command);

        void onFault(EditEntityExternalCommand _command, Throwable _cause);
    }

    public class EditEntityExternalCommandException extends Exception {

        public EditEntityExternalCommandException() {
        }

        public EditEntityExternalCommandException(final String _s) {
            super(_s);
        }

        public EditEntityExternalCommandException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public EditEntityExternalCommandException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}