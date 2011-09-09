package app.workingarea;

import java.util.List;

/**
 * Интерфейс менеджера рабочих пространств
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface WorkspaceManager {

    /**
     * Возвращает список идентификаторов доступных рабочих пространств
     *
     * @return список идентификаторов
     * @throws WorkspaceManagerException если не получилось
     */
    List<String> getWorkspacesIdList() throws WorkspaceManagerException;

    /**
     * Возвращает рабочее пространство по идентификатору
     *
     * @param _id             идентификатор
     * @param _serviceManager менеджер сервисов
     * @return экземпляр рабочего пространства
     * @throws WorkspaceManagerException если не получилось
     */
    Workspace getWorkspace(String _id, ServiceManager _serviceManager) throws WorkspaceManagerException;

    /**
     * Создает рабочее пространство с указанным идентификатором
     *
     * @param _id             идентификатор
     * @param _serviceManager менеджер сервисов
     * @return экземпляр рабочего пространства
     * @throws WorkspaceManagerException если не получилось
     */
    Workspace createWorkspace(String _id, ServiceManager _serviceManager) throws WorkspaceManagerException;

    /**
     * Удаляет рабочее пространство с указанным идентификатором
     *
     * @param _id идентиыфикатор
     * @throws WorkspaceManagerException если не получилось
     */
    void deleteWorkspace(String _id) throws WorkspaceManagerException;

    /**
     * Переименовывает рабочее пространство
     *
     * @param _oldId старый идентификатор
     * @param _newId новый идентификатор
     * @throws WorkspaceManagerException если не получилось
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
