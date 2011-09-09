package app.workingarea;

import constructor.dom.Preprocessor;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.storage.Storage;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import debug.DebugConsole;
import downloader.Downloader;
import resource.ConverterFactory;
import timeservice.TimeService;

import java.util.Map;

import greader.GoogleReaderAdapter;

/**
 * Менеджер системных сервисов. Системные сервисы не привязаны
 * ни к какому из рабочих пространств. Они зависят только от
 * системных установок
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public interface ServiceManager {

    /**
     * Устанавливает текущий внешний контекст
     *
     * @param _context внешний контекст
     */
    void setExternalContext(final Map<String, String> _context);

    /**
     * Возвращает сервис времени
     *
     * @return экземпляр сервиса времени
     * @throws ServiceManagerException если не получилось
     */
    TimeService getTimeService() throws ServiceManagerException;

    //todo нужно ли это в сервис менеджере?
    /**
     * Возвращает фабрику фетчеров модификаций
     *
     * @return экземпляр фабрики
     * @throws ServiceManagerException если не получилось
     */
    FetcherFactory getFetcherFactory() throws ServiceManagerException;

    /**
     * Возвращает HTTP загрузчик
     *
     * @return экземпляр загрузчика
     * @throws ServiceManagerException если не получилось
     */
    Downloader getDownloader() throws ServiceManagerException;

    /**
     * Возвращает фабрику конверторов внешних форматов ресурсов
     *
     * @return экземпляр фабрики
     * @throws ServiceManagerException если не получилось
     */
    ConverterFactory getConverterFactory() throws ServiceManagerException;

    /**
     * Возвращает отладочную консоль
     *
     * @return отладочная консоль
     */
    DebugConsole getDebugConsole();

    /**
     * Возвращает внешний контекст
     *
     * @return внешний контекст
     */
    Map<String, String> getExternalContext();

    /**
     * Возвращает кеш ресурсов
     *
     * @return кеш ресурсов
     */
    ResourceCache getResourceCache();

    /**
     * Возвращает оболочку для запуска внешних процессов
     *
     * @return оболочка для запуска внешних процессов
     */
    ProcessWrapper getProcessWrapper();

    /**
     * Возвращает хранилище по умолчанию
     *
     * @return хранилище по умолчанию
     * @throws ServiceManagerException если не получилось
     */
    Storage getDefaultStorage() throws ServiceManagerException;

    /**
     * Активирует режим рефлексии (подстановочные параметры анализируются, но не обрабатываются)
     */
    void activateReflectionMode();

    /**
     * Деактивирует режим рефлексии (подстановочные параметры анализируются и обрабатываются)
     */
    void deactivateReflectionMode();

    /**
     * Возвращает препроцессор исходных файлов
     *
     * @return препроцессор исходных файлов
     */
    Preprocessor getPreprocessor();

    /**
     * Возвращает адаптер для работы с Google Reader
     *
     * @return адаптер для работы с Google Reader
     * @throws ServiceManagerException если не получилось
     */
    GoogleReaderAdapter getGoogleReaderAdapter() throws ServiceManagerException;

    /**
     * Завершает работу менеджера сервисов
     */
    void cleanup();

    public class ServiceManagerException extends Exception {

        public ServiceManagerException() {
        }

        public ServiceManagerException(String _s) {
            super(_s);
        }

        public ServiceManagerException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public ServiceManagerException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
