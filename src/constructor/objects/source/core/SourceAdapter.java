package constructor.objects.source.core;

import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.strategies.StoreStrategy;
import constructor.objects.strategies.UpdateStrategy;
import dated.item.modification.stream.ModificationList;
import timeservice.TimeService;

import java.util.Date;

/**
 * Интерфейс адаптера источника модификаций. По соглашению
 * ни один из методов не возвращает null. Если возврат
 * адекватного результата по каким-либо причинам невозможен -
 * выбрасывается исключение.
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public interface SourceAdapter extends Controller {
    /**
     * Возвращает идентификатор источника
     *
     * @return идентификатор
     * @throws AdapterException если не получилось
     */
    String getId() throws AdapterException;

    /**
     * Возвращает фетчер модификаций
     *
     * @return фетчер
     * @throws AdapterException если не получилось
     */
    ModificationFetcher getFetcher() throws AdapterException;

    /**
     * Возвращает предыдущий (сохраненный в предыдущем сеансе)
     * список модификаций
     *
     * @return хранилище
     * @throws AdapterException если не получилось
     */
    ModificationList getList() throws AdapterException;

    /**
     * Сохраняет список модификаций для последующего использования
     *
     * @param _list список
     * @throws AdapterException если не получилось
     */
    void storeList(ModificationList _list) throws AdapterException;

    /**
     * Возвращает процессор обработки модификаций
     *
     * @return процессор
     * @throws AdapterException если не получилось
     */
    ModificationProcessor getProcessor() throws AdapterException;

    /**
     * Возвращает стратегию хранения модификаций в списке
     *
     * @return стратегия хранения
     * @throws AdapterException если не получилось
     */
    StoreStrategy getStoreStrategy() throws AdapterException;

    /**
     * Возвращает стратегию обновления источника модификаций
     *
     * @return стратегия обновления
     * @throws AdapterException если не получилось
     */
    UpdateStrategy getUpdateStrategy() throws AdapterException;

    /**
     * Возвращает дату последнего обновления источника.
     * Если источник новый или ни разу до этого не обновлялся -
     * возвращается начало эпохи.
     *
     * @return дата последнего обновления
     * @throws AdapterException если не получилось
     */
    Date getLastUpdateTime() throws AdapterException;

    /**
     * Устанавливает дату последнего обновления равной текущей дате
     *
     * @throws AdapterException если не получилось
     */
    void setLastUpdateTimeToCurrent() throws AdapterException;

    /**
     * Возвращает системный сервис времени
     *
     * @return сервис времени
     */
    TimeService getTimeService();
}
