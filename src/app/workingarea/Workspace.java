package app.workingarea;

import cloud.PropertiesCloud;
import constructor.dom.ConstructorFactory;
import constructor.dom.Locator;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.storage.Storage;

/**
 * ��������� �������� ������������
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface Workspace {

    /**
     * ���������� ������� ��������
     *
     * @return ��������� ��������
     * @throws WorkspaceException ���� �� ����������
     */
    Locator getLocator() throws WorkspaceException;

    /**
     * ���������� ������� �������������
     *
     * @return ��������� �������
     * @throws WorkspaceException ���� �� ����������
     */
    ConstructorFactory getConstructorFactory() throws WorkspaceException;

    /**
     * ���������� ������ ���������
     *
     * @return ��������� ������
     * @throws WorkspaceException ���� �� ����������
     */
    PropertiesCloud getCloud() throws WorkspaceException;

    /**
     * ���������� ��������� ������� �����������
     *
     * @return ��������� ���������
     * @throws WorkspaceException ���� �� ����������
     */
    ModificationListStorage getModificationListStorage() throws WorkspaceException;

    /**
     * ���������� ��������� ������� ������ �������
     *
     * @return ��������� ���������
     * @throws WorkspaceException ���� �� ����������
     */
    ChannelDataListStorage getChannelDataListStorage() throws WorkspaceException;

    /**
     * ���������� ��������� �� ��������������
     *
     * @param _id ������������� ���������
     * @return �������� ���������
     * @throws WorkspaceException ���� �� ����������
     */
    Storage getStorage(String _id) throws WorkspaceException;

    /**
     * ��������� ������ � ������� �������������
     */
    void cleanup();

    public class WorkspaceException extends Exception {

        public WorkspaceException() {
        }

        public WorkspaceException(String _s) {
            super(_s);
        }

        public WorkspaceException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public WorkspaceException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
