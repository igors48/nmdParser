package app.workingarea;

import cloud.PropertiesCloud;
import constructor.dom.ConstructorFactory;
import constructor.dom.Locator;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.storage.Storage;

/**
 * Интерфейс рабочего пространства
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface Workspace {

    /**
     * Возвращает локатор объектов
     *
     * @return экземпляр локатора
     * @throws WorkspaceException если не получилось
     */
    Locator getLocator() throws WorkspaceException;

    /**
     * Возвращает фабрику конструкторов
     *
     * @return экземпляр фабрики
     * @throws WorkspaceException если не получилось
     */
    ConstructorFactory getConstructorFactory() throws WorkspaceException;

    /**
     * Возвращает облако пропертей
     *
     * @return экземпляр облака
     * @throws WorkspaceException если не получилось
     */
    PropertiesCloud getCloud() throws WorkspaceException;

    /**
     * Возвращает хранилище списков модификаций
     *
     * @return экземпляр хранилища
     * @throws WorkspaceException если не получилось
     */
    ModificationListStorage getModificationListStorage() throws WorkspaceException;

    /**
     * Возвращает хранилище списков данных каналов
     *
     * @return экземпляр хранилища
     * @throws WorkspaceException если не получилось
     */
    ChannelDataListStorage getChannelDataListStorage() throws WorkspaceException;

    /**
     * Возвращает хранилище по идентификатору
     *
     * @param _id идентификатор хранилища
     * @return экзепляр хранилища
     * @throws WorkspaceException если не получилось
     */
    Storage getStorage(String _id) throws WorkspaceException;

    /**
     * Завершает работу с рабочим пространством
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
