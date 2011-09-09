package constructor.objects.interpreter.core;

import constructor.objects.AdapterException;
import dated.item.modification.Modification;
import downloader.BatchLoader;

/**
 * Интерфейс адаптера интерпретатора
 *
 * @author Igor Usenko
 *         Date: 09.04.2009
 */
public interface InterpreterAdapter {

    /**
     * Возвращает анализатор списка страниц
     *
     * @return анализатор списка страниц
     * @throws AdapterException если не получилось
     */
    PageListAnalyser getPageListAnalyser() throws AdapterException;

    /**
     * Возвращает загрузчик страниц
     *
     * @return загрузчик страниц
     * @throws AdapterException если не получилось
     */
    BatchLoader getWebPageLoader() throws AdapterException;

    /**
     * Возвращает анализатор фрагмента
     *
     * @return анализатор фрагмента
     * @throws AdapterException если не получилось
     */
    FragmentAnalyser getFragmentAnalyser() throws AdapterException;

    /**
     * Возвращает анализатор страницы
     *
     * @return анализатор страницы
     * @throws AdapterException если не получилось
     */
    PageAnalyser getPageAnalyser() throws AdapterException;

    /**
     * Возвращает модификацию, которую нужно интерпретировать
     *
     * @return модификация
     * @throws AdapterException если не получилось
     */
    Modification getModification() throws AdapterException;

    /**
     * Возвращает идентификатор интерпретатора
     *
     * @return идентификатор
     * @throws AdapterException если не получилось
     */
    String getId() throws AdapterException;

    /**
     * Возвращает максимальное количество датированных элементов в канале
     *
     * @return максимальное количество датированных элементов в канале. 0 - если нужны все элементы
     */
    int getLastItemCount();

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
     * Возвращает флаг отмены работы
     *
     * @return true не продолжать false - продолжать
     */
    boolean isCancelled();
}
