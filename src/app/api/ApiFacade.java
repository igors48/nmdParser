package app.api;

import app.cli.blitz.BlitzRequestHandler;
import app.controller.Controller;
import app.iui.command.ExternalConverterContext;
import app.metadata.ObjectMetaData;
import app.templater.TemplateParameters;
import constructor.dom.locator.Mask;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import timeservice.TimeService;

import java.util.List;
import java.util.Map;

/**
 * Фасад приложения. Все операции - однопроходны, т.е варианты попыток
 * и(или) задержек между ними - не их проблема
 *
 * @author Igor Usenko
 *         Date: 15.04.2009
 */
public interface ApiFacade extends BlitzRequestHandler {

    /**
     * Возвращает имя дефолтного комплекта установок
     *
     * @return имя комплекта или пустая строка если не получилось
     */
    String getDefaultSettingsName();

    /**
     * Возвращает имя дефолтного рабочего пространства
     *
     * @return имя рабочего пространства или пустая строка если не получилось
     */
    String getDefaultWorkspaceName();

    /**
     * Возвращает список доступных рабочих пространств
     *
     * @return список доступных рабочих пространств
     * @throws FatalException если не получилось
     */
    List<String> getWorkspacesNames() throws FatalException;

    /**
     * Загружает указанный комплект установок
     *
     * @param _id идентификатор комплекта
     * @throws FatalException если не получилось
     */
    void loadSettings(String _id) throws FatalException;

    /**
     * Загружает указанное рабочее пространство
     *
     * @param _id идентификатор рабочего пространства
     * @throws FatalException если не получилось
     */
    void loadWorkspace(String _id) throws FatalException;

    /**
     * Возвращает список метаданных всех объектов в рабочем пространстве
     * Метаданные это все что нужно ГУИ для работы с объектом
     *
     * @return список метаданных всех объектов в рабочем пространстве
     * @throws FatalException если не получилось
     */
    List<ObjectMetaData> getObjectsMetaData() throws FatalException;

    /**
     * Создает рабочее пространство с указанным идентификатором
     *
     * @param _id идентификатор
     * @throws FatalException если не получилось
     */
    void createWorkspace(String _id) throws FatalException;

    /**
     * Удаляет рабочее пространство с указанным идентификатором
     *
     * @param _id идентификатор
     * @throws FatalException если не получилось
     */
    void deleteWorkspace(String _id) throws FatalException;

    /**
     * Переименовывает рабочее пространство
     *
     * @param _oldId старый идентификатор
     * @param _newId новый идентификатор
     * @throws FatalException если не получилось
     */
    void renameWorkspace(String _oldId, String _newId) throws FatalException;

    /**
     * Обновляет источник, канал и выхлоп
     *
     * @param _sourceId                 идентификатор источника
     * @param _channelId                идентификатор канала
     * @param _outputId                 идентификатор формирователя
     * @param _forcedDays               количество дней для принудительного обновления -1 - нет такого; 0 - все; >0 - количество дней
     * @param _controller               контроллер для происходящего
     * @param _context                  контекст выполнения
     * @param _externalConverterContext контекст внешнего конвертора
     * @return Список полных имен сформированных файлов
     * @throws FatalException       если формирователь создать не удалось
     * @throws RecoverableException если не удалось сформировать документ
     */
    List<String> updateFullSet(String _sourceId, String _channelId, String _outputId, int _forcedDays, Controller _controller, Map<String, String> _context, ExternalConverterContext _externalConverterContext) throws FatalException, RecoverableException;

    /**
     * Возвращает список зарегистрированных в рабочем пространстве
     * источников модификаций. Возможно применение списка масок.
     *
     * @param _mask маска отбора
     * @return список источников
     * @throws FatalException если не получилось
     */
    List<String> getSourcesNames(Mask _mask) throws FatalException;

    /**
     * Обновляет указанный источник модификаций
     *
     * @param _id         идентификатор источника
     * @param _controller контроллер для происходящего
     * @param _context    контекст выполнения
     * @throws FatalException       если не удалось создать источник
     * @throws RecoverableException если источник не удалось обновить
     */
    void updateSource(String _id, Controller _controller, Map<String, String> _context) throws FatalException, RecoverableException;

    /**
     * Возвращает список зарегистрированных в рабочем пространстве
     * каналов. Возможно применение списка масок.
     *
     * @param _mask маска отбора
     * @return список каналов
     * @throws FatalException если не получилось
     */
    List<String> getChannelsNames(Mask _mask) throws FatalException;

    /**
     * Обновляет указанный канал
     *
     * @param _id         идентификатор канала
     * @param _forcedDays количество дней для принудительного обновления -1 - нет такого; 0 - все; >0 - количество дней
     * @param _controller контроллер для происходящего
     * @param _context    контекст выполнения
     * @throws FatalException       если не удалось создать канал
     * @throws RecoverableException если канал не удалось обновить
     */
    void updateChannel(String _id, int _forcedDays, Controller _controller, Map<String, String> _context) throws FatalException, RecoverableException;

    /**
     * Возвращает список зарегистрированных в рабочем пространстве
     * генераторов выходных документов. Возможно применение списка масок.
     *
     * @param _mask маска отбора
     * @return список генераторов
     * @throws FatalException если не получилось
     */
    List<String> getOutputsNames(Mask _mask) throws FatalException;

    /**
     * Формирует выходной документ
     *
     * @param _id         идентификатор формирователя
     * @param _forcedDays количество дней для принудительного обновления -1 - нет такого; 0 - все; >0 - количество дней
     * @param _controller контроллер для происходящего
     * @param _context    контекст выполнения
     * @return Список полных имен сформированных файлов
     * @throws FatalException       если формирователь создать не удалось
     * @throws RecoverableException если не удалось сформировать документ
     */
    List<String> updateOutput(String _id, int _forcedDays, Controller _controller, Map<String, String> _context) throws FatalException, RecoverableException;

    /**
     * Возвращает список зарегистрированных в рабочем пространстве
     * симплеров. Возможно применение списка масок.
     *
     * @param _mask маска отбора
     * @return список симплеров
     * @throws FatalException если не получилось
     */
    List<String> getSimplersNames(Mask _mask) throws FatalException;

    /**
     * Сохраняет (обновляет) конфигурацию симплера
     *
     * @param _configuration конфигурация
     * @throws FatalException есди не получилось
     */
    void storeSimplerConfiguration(SimplerConfiguration _configuration) throws FatalException;

    /**
     * Удаляет конфигурацию симплера
     *
     * @param _configuration конфигурация
     * @throws FatalException есди не получилось
     */
    void removeSimplerConfiguration(SimplerConfiguration _configuration) throws FatalException;

    /**
     * Разрешает работу отладочной консоли
     */
    void enableDebugConsole();

    /**
     * Создает набор шаблонов для работы с фидом
     *
     * @param _parameters - параметры шаблона
     * @throws FatalException если не получилось
     */
    void createTemplates(TemplateParameters _parameters) throws FatalException;

    /**
     * Обрабатывает сниппет
     *
     * @param _name       полный путь к файлу сниппета
     * @param _forcedDays количество дней для принудительного обновления -1 - нет такого; 0 - все; >0 - количество дней
     * @param _context    контекст выполнения
     * @return Список полных имен сформированных файлов
     * @throws FatalException если не получилось
     */
    List<String> processSnippet(String _name, int _forcedDays, Map<String, String> _context) throws FatalException;

    /**
     * Возвращает количество попыток обновления источника/канала/документа
     *
     * @return количество попыток
     * @throws FatalException если не получилось
     */
    int getUpdateAttempts() throws FatalException;

    /**
     * Возвращает таймаут между попытками обновления источника/канала/документа
     *
     * @return таймаут в миллисекундах
     * @throws FatalException если не получилось
     */
    long getUpdateTimeout() throws FatalException;

    /**
     * Возвращает системный сервис времени
     *
     * @return сервис времени
     * @throws FatalException если не получилось
     */
    TimeService getTimeService() throws FatalException;

    /**
     * Удаляет служебные файлы созданные настройками
     *
     * @param _mask маска отбора настроек
     * @throws FatalException если не получилось
     */
    void removeServiceFiles(Mask _mask) throws FatalException;

    /**
     * Отменяет все запрошенные на данный момент загрузки
     *
     * @throws FatalException если не получилось
     */
    void cancelAllDownloads() throws FatalException;

    /**
     * Создает Google Reader профайл
     *
     * @param _email адрес эл. почты
     * @param _password пароль
     * @throws FatalException если не получилось
     */
    void createGoogleReaderProfile(String _email, String _password) throws FatalException;

    /**
     * Удаляет Google Reader профайл
     *
     * @param _email адрес эл. почты
     * @throws FatalException если не получилось
     */
    void deleteGoogleReaderProfile(String _email) throws FatalException;

    /**
     * Обновляет фиды Google Reader профайла
     *
     * @param _email адрес эл. почты
     * @throws FatalException если не получилось
     */
    void updateGoogleReaderProfile(String _email) throws FatalException;

    /**
     * Изменяет пароль Google Reader профайла
     *
     * @param _email адрес эл. почты
     * @param _newPassword новый пароль
     * @throws FatalException если не получилось
     */
    void changeGoogleReaderProfilePassword(String _email, String _newPassword) throws FatalException;

    /**
     * Тестирует фиды Google Reader профайла
     *
     * @param _email адрес эл. почты
     * @param _feed фид из профиля подпадающий под тестирование
     * @throws FatalException если не получилось
     */
    void testGoogleReaderProfile(String _email, String _feed) throws FatalException;

    /**
     * Завершает работу с API
     */
    void cleanup();

    /**
     * Исключение после получения которого продолжать смысла нет
     */
    public class FatalException extends Exception {

        public FatalException() {
        }

        public FatalException(String _s) {
            super(_s);
        }

        public FatalException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public FatalException(Throwable _throwable) {
            super(_throwable);
        }
    }

    /**
     * Исключение уровня обработки - можно сделать еще одну попытку
     */
    public class RecoverableException extends Exception {

        public RecoverableException() {
        }

        public RecoverableException(String _s) {
            super(_s);
        }

        public RecoverableException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public RecoverableException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
