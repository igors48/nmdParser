package app.workingarea.workspace;

import app.workingarea.ServiceManager;
import app.workingarea.Workspace;
import app.workingarea.WorkspaceManager;
import util.Assert;
import util.FileTools;
import util.PathTools;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер рабочих пространств расположенных в подкаталогах
 * основного каталога
 *
 * @author Igor Usenko
 *         Date: 17.04.2009
 */
public class WorkspaceDirectoriesManager implements WorkspaceManager {

    private static class DirectoryFilter implements FileFilter {

        public boolean accept(final File _file) {
            return _file.isDirectory();
        }
    }

    private static final DirectoryFilter DIRECTORY_FILTER = new DirectoryFilter();

    private final String root;

    public WorkspaceDirectoriesManager(final String _root) {
        Assert.isValidString(_root, "Workspace manager root path is not valid.");
        Assert.isTrue(new File(_root).exists(), "Workspace manager root path [ " + _root + " ] is not exists.");

        this.root = PathTools.normalize(_root);
    }

    public List<String> getWorkspacesIdList() throws WorkspaceManagerException {
        List<String> result = new ArrayList<String>();

        File[] list = new File(this.root).listFiles(DIRECTORY_FILTER);

        for (File current : list) {
            result.add(current.getName());
        }

        return result;
    }

    public Workspace getWorkspace(final String _id, final ServiceManager _serviceManager) throws WorkspaceManagerException {
        Assert.isValidString(_id, "Workspace id is not valid.");
        Assert.notNull(_serviceManager, "Service manager is null");

        String workspaceRoot = getWorkspaceRoot(_id);

        if (!(new File(workspaceRoot).exists())) {
            throw new WorkspaceManagerException("Parent directory for workspace [ " + _id + " ] does not exists.");
        }

        return new DirectoryWorkspace(workspaceRoot, _serviceManager);
    }

    public Workspace createWorkspace(String _id, final ServiceManager _serviceManager) throws WorkspaceManagerException {
        Assert.isValidString(_id, "Workspace id is not valid.");
        Assert.notNull(_serviceManager, "Service manager is null");

        try {
            DirectoryWorkspace.create(this.root, _id);

            return getWorkspace(_id, _serviceManager);
        } catch (Workspace.WorkspaceException e) {
            throw new WorkspaceManagerException(e);
        }
    }

    public void deleteWorkspace(final String _id) throws WorkspaceManagerException {
        Assert.isValidString(_id, "Workspace id is not valid.");

        if (!FileTools.delete(new File(getWorkspaceRoot(_id)), true)) {
            throw new WorkspaceManagerException("Error delete workspace id [ " + _id + " ]");
        }
    }

    public void renameWorkspace(final String _oldId, final String _newId) throws WorkspaceManagerException {
        Assert.isValidString(_oldId, "Old workspace id is not valid.");
        Assert.isValidString(_newId, "New workspace id is not valid.");

        File oldRoot = new File(getWorkspaceRoot(_oldId));
        File newRoot = new File(getWorkspaceRoot(_newId));

        if (!oldRoot.renameTo(newRoot)) {
            throw new WorkspaceManagerException("Error rename workspace id [ " + _oldId + " ] to id [ " + _newId + " ]");
        }
    }

    private String getWorkspaceRoot(final String _id) {
        return this.root + _id;
    }
}
