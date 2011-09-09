package constructor.objects.channel.core;

import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.core.InterpreterEx;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import downloader.BatchLoader;
import timeservice.TimeService;

import java.util.List;

/**
 * Интерфейс адаптера канала обработки модификаций. По соглашению
 * ни один из методов не возвращает null. Если возврат
 * адекватного результата по каким-либо причинам невозможен -
 * выбрасывается исключение.
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public interface ChannelAdapter extends Controller {

    String getId();

    /**
     * Возвращает список модификаций для этого канала
     *
     * @return список модификаций
     * @throws AdapterException если не получилось
     */
    ModificationList getModificationsList() throws AdapterException;

    /**
     * Возвращает сохраненный ранее список данных этого канала
     *
     * @return список данных
     * @throws AdapterException если не получилось
     */
    ChannelDataList getChannelDataList() throws AdapterException;

    /**
     * Удаляет список данных канала
     *
     * @throws AdapterException если не получилось
     */
    void removeChannelDataList() throws AdapterException;

    /**
     * Сохраняет список данных канала
     *
     * @param _datas список данных
     * @throws AdapterException если не получилось
     */
    void storeChannelDataList(ChannelDataList _datas) throws AdapterException;

    /**
     * Возвращает анализатор канала
     *
     * @return анализатор канала
     * @throws AdapterException если не получилось
     */
    ChannelAnalyser getAnalyser() throws AdapterException;

    /**
     * Возвращает загрузчик страниц
     *
     * @return загрузчик страниц
     * @throws AdapterException если не получилось
     */
    BatchLoader getPageLoader() throws AdapterException;

    /**
     * Возвращает комплект интерпретаторов для данной модификации
     *
     * @param _modification модификация
     * @return комплект интерпретаторов
     * @throws AdapterException если не получилось
     */
    List<InterpreterEx> getInterpreters(Modification _modification) throws AdapterException;

    /**
     * Возвращает возраст элементов данных канала (в миллисекундах) подлежащих принудительному обновлению
     *
     * @return true - -1 - принудительного нет
     *         0 - полное принудительное,
     *         >0 - принудительно обновляются все кто моложе указанного возраста
     * @throws AdapterException если не получилось
     */
    long getForcedAge() throws AdapterException;

    /**
     * Возвращает список жанров канала
     *
     * @return список жанров
     * @throws AdapterException если не получилось
     */
    List<String> getGenres() throws AdapterException;

    /**
     * Возвращает язык канала
     *
     * @return идентификатор языка
     * @throws AdapterException если не получилось
     */
    String getLang() throws AdapterException;

    /**
     * Возвращает адрес откуда может быть загружена обложка для канала
     *
     * @return адрес
     * @throws AdapterException если не получилось
     */
    String getCoverUrl() throws AdapterException;

    /**
     * Возвращает сервис системного времени
     *
     * @return сервис времени
     * @throws AdapterException если не получилось
     */
    TimeService getSystemTimeService() throws AdapterException;

    /**
     * Возвращает количество предварительно кешируемых элементов
     *
     * @return количество предварительно кешируемых элементов
     */
    int getPrecachedItemsCount();

    /**
     * Возвращает время ожидания между отсылкой последовательных запросов. Anti-DDoS
     *
     * @return время ожидания в миллисекундах
     */
    long getPauseBetweenRequests();

    /**
     * Возвращает режим обработки
     *
     * @return true если информация просто копируется из источника, false если для
     *         получения информации используется более продвинутая обработка - интерпретаторы и т.п.
     */
    boolean isSimpleHandling();
}
