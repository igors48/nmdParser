package app.workingarea;

import converter.format.fb2.resource.Fb2ResourceConversionContext;

/**
 * Интерфейс к рабочим установкам приложения
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface Settings {

    /**
     * Возвращает каталог для временных файлов
     *
     * @return путь к каталогу
     */
    String getTempDirectory();

    /**
     * Вовращает заглушку для неподдерживаемых / недоступных ресурсов
     *
     * @return путь к файлу заглушке
     */
    String getResourceDummy();

    /**
     * Вовращает заглушку для неподдерживаемых / недоступных ресурсов в виде байт массива
     *
     * @return байт массив содержащий образ заглушки
     */
    byte[] getResourceDummyAsBytes();

    /**
     * Возвращает каталог для отладочных файлов
     *
     * @return путь к каталогу
     */
    String getDebugDirectory();

    /**
     * Возвращает количество попыток обновления источника/канала/документа
     *
     * @return количество попыток
     */
    int getUpdateAttempts();

    /**
     * Возвращает таймаут между попытками обновления источника/канала/документа
     *
     * @return таймаут в миллисекундах
     */
    long getUpdateTimeout();

    /**
     * Возвращает режим использования прокси
     *
     * @return true - прокси используется false - прокси не используется
     */
    boolean isProxyUsed();

    /**
     * Возвращает максимальное количество ошибок хоста, после которых он идет в баню
     *
     * @return максимальное количество ошибок
     */
    int getBannedListTreshold();

    /**
     * Возвращает максимальный размер списка забаненных хостов
     *
     * @return максимальный размер списка
     */
    int getBannedListLimit();

    /**
     * Возвращает строку с именем агента
     *
     * @return строка с именем агента
     */
    String getUserAgent();

    /**
     * Возвращает адрес прокси хоста
     *
     * @return адрес прокси хоста
     */
    String getProxyHost();

    /**
     * Возвращает порт прокси
     *
     * @return порт прокси
     */
    int getProxyPort();

    /**
     * Возвращает логин для прокси
     *
     * @return логин для прокси
     */
    String getUserName();

    /**
     * Возвращает пароль для прокси
     *
     * @return пароль для прокси
     */
    String getUserPassword();

    /**
     * Возвращает максимальное количество попыток обращения к хосту
     * перед тем как будет признана ошибка
     *
     * @return количество попыток
     */
    int getMaxTryCount();

    /**
     * Возвращает время ожидания на сокете перед тем как будет признан таймаут
     *
     * @return время ожидания
     */
    int getSocketTimeout();

    /**
     * Возвращает таймаут между попытками обращения к хосту
     *
     * @return значение таймаута
     */
    int getErrorTimeout();

    /**
     * Возвращает минимальное значение таймаута
     *
     * @return минимальное значение таймаута
     */
    int getMinTimeout();

    /**
     * Возвращает максимальное количество символов в имени выходного файла
     *
     * @return максимальное количество символов
     */
    int getMaxFileNameLength();

    /**
     * Возвращает режим работы кеша ресурсов
     *
     * @return true если кеш активен false если отдыхает
     */
    boolean isResourceCacheEnabled();

    /**
     * Возвращает корневой каталог хранилища ресурсов
     *
     * @return корневой каталог хранилища ресурсов
     */
    String getResourceCacheRoot();

    /**
     * Возвращает максимальный возраст элемента данных кеша
     *
     * @return максимальный возраст элемента данных кеша в миллисекундах
     */
    long getResourceItemMaxAge();

    /**
     * Возвращает максимальный размер элемента данных кеша
     *
     * @return максимальный размер элемента данных кеша в байтах
     */
    long getResourceItemMaxSize();

    /**
     * Возвращает максимальный размер кеша
     *
     * @return максимальный размер кеша
     */
    long getResourceCacheMaxSize();

    /**
     * Возвращает максимальное число ошибок внешнего процесса после которого он идет в баню
     *
     * @return максимальное количество ошибок
     */
    int getExternalProcessMaxFailCount();

    /**
     * Возвращает корневой каталог хранилища по умолчанию
     *
     * @return корневой каталог хранилища по умолчанию
     */
    String getDefaultStorageRoot();

    /**
     * Возвращает период хранения документов в хранилище по умолчанию
     *
     * @return период хранения документов в хранилище в миллисекундах
     */
    long getDefaultStoragePeriod();

    /**
     * Возвращает режим сортировки элементов в документе по дате
     *
     * @return true если от младших к старшим false если наоборот
     */
    boolean isFromNewToOld();

    /**
     * Возвращает количество предварительно кешируемых элементов
     *
     * @return количество предварительно кешируемых элементов
     */
    int getPrecachedItemsCount();

    /**
     * Возвращает контекст конвертации ресурсов
     *
     * @return контекст конвертации ресурсов
     */
    Fb2ResourceConversionContext getResourceConversionContext();

    /**
     * Возвращает корневой каталог для GoogleReader
     *
     * @return корневой каталог для GoogleReader
     */
    String getGoogleReaderRoot();

    int getGoogleReaderMaxItemsPerRequest();

    public class SettingsException extends Exception {

        public SettingsException() {
        }

        public SettingsException(String _s) {
            super(_s);
        }

        public SettingsException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public SettingsException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
