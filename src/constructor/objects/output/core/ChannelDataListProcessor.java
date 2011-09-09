package constructor.objects.output.core;

import constructor.objects.channel.core.stream.ChannelDataList;
import flowtext.Document;
import timeservice.TimeService;

/**
 * Интерфейс обработчика данных канала
 *
 * @author Igor Usenko
 *         Date: 25.04.2009
 */
public interface ChannelDataListProcessor {

    /**
     * Формирует документ из данных канала
     *
     * @param _data        данные канала
     * @param _title       заголовок документа (если он пустой - берется заголовок по умолчанию)
     * @param _timeService сервис времени
     * @return сформированный документ
     */
    Document process(ChannelDataList _data, String _title, TimeService _timeService);
}
