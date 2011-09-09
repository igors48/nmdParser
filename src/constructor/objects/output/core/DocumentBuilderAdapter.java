package constructor.objects.output.core;

import app.VersionInfo;
import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DateSectionMode;
import flowtext.Document;
import timeservice.TimeService;

import java.util.List;

/**
 * »нтерфейс адаптера построител€ документа
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public interface DocumentBuilderAdapter extends Controller {

    String getId();

    /**
     * ¬озвращает список данных нужного канала
     *
     * @return список данных канада
     * @throws AdapterException если не получилось
     */
    ChannelDataList getChannelDatas() throws AdapterException;

    /**
     * ¬озвращает сервис времени
     *
     * @return сервис времени
     * @throws AdapterException если не получилось
     */
    TimeService getTimeService() throws AdapterException;

    /**
     * ѕостпроцессит документ - конвертит и сохран€ет
     *
     * @param _document собственно документ
     * @return список полных имен сформированных файлов
     * @throws AdapterException если не получилось
     */
    List<String> postprocessDocument(Document _document) throws AdapterException;

    /**
     * ¬озвращает режим композиции документа
     *
     * @return режим композиции
     * @throws AdapterException если не получилось
     */
    Composition getComposition() throws AdapterException;

    /**
     * ¬озвращает им€ документа дл€ режима композиции many-to-one
     *
     * @return им€ документа либо пуста€ строка, если им€ не определено
     * @throws AdapterException если не получилось
     */
    String getDocumentName() throws AdapterException;

    /**
     * ¬озвращает дату последнего обновлени€ источника.
     * ≈сли источник новый или ни разу до этого не обновл€лс€ -
     * возвращаетс€ начало эпохи.
     *
     * @return дата последнего обновлени€
     * @throws AdapterException если не получилось
     */
    long getLatestItemTime() throws AdapterException;

    /**
     * ”станавливает дату последнего обновлени€ равной указанной дате
     *
     * @param _time указанна€ дата/врем€
     * @throws AdapterException если не получилось
     */
    void setLatestItemTime(long _time) throws AdapterException;

    /**
     * ¬озвращает режим принудительного обновлени€ генератора
     *
     * @return true - если режим принудительный false - если режим обычный
     * @throws AdapterException если не получилось
     */
    boolean isForcedMode() throws AdapterException;

    /**
     * ¬озвращает режим обработки ссылок на изображение
     *
     * @return true пытатьс€ подкачать оригинал false вставл€ть то, что есть
     */
    boolean resolveImageLinks();

    /**
     * ¬озвращает информацию о версии приложени€
     *
     * @return информаци€ о версии
     */
    VersionInfo getVersionInfo();

    /**
     * ¬озвращает режим формировани€ секции даты
     *
     * @return режим формировани€ секции даты
     */
    DateSectionMode getDateSectionMode();

    /**
     * ¬озвращает режим сортировки элементов в документе по дате
     *
     * @return true если от младших к старшим false если наоборот
     */
    boolean isFromNewToOld();

}
