package app.workingarea;

import java.util.List;

/**
 * ��������� ��������� ������� �����������
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface WorkspaceManager {

    /**
     * ���������� ������ ��������������� ��������� ������� �����������
     *
     * @return ������ ���������������
     * @throws WorkspaceManagerException ���� �� ����������
     */
    List<String> getWorkspacesIdList() throws WorkspaceManagerException;

    /**
     * ���������� ������� ������������ �� ��������������
     *
     * @param _id             �������������
     * @param _serviceManager �������� ��������
     * @return ��������� �������� ������������
     * @throws WorkspaceManagerException ���� �� ����������
     */
    Workspace getWorkspace(String _id, ServiceManager _serviceManager) throws WorkspaceManagerException;

    /**
     * ������� ������� ������������ � ��������� ���������������
     *
     * @param _id             �������������
     * @param _serviceManager �������� ��������
     * @return ��������� �������� ������������
     * @throws WorkspaceManagerException ���� �� ����������
     */
    Workspace createWorkspace(String _id, ServiceManager _serviceManager) throws WorkspaceManagerException;

    /**
     * ������� ������� ������������ � ��������� ���������������
     *
     * @param _id ��������������
     * @throws WorkspaceManagerException ���� �� ����������
     */
    void deleteWorkspace(String _id) throws WorkspaceManagerException;

    /**
     * ��������������� ������� ������������
     *
     * @param _oldId ������ �������������
     * @param _newId ����� �������������
     * @throws WorkspaceManagerException ���� �� ����������
     */
    void renameWorkspace(String _oldId, String _newId) throws WorkspaceManagerException;

    public class WorkspaceManagerException extends Exception {

        public WorkspaceManagerException() {
        }

        public WorkspaceManagerException(String _s) {
            super(_s);
        }

        public WorkspaceManagerException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public WorkspaceManagerException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
