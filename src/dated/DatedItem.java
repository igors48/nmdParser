package dated;

import constructor.objects.StreamHelperBean;

/**
 * Интерфейс сущности связывающей какую-то информацию с какой-то датой
 *
 * @author Igor Usenko
 *         Date: 07.11.2008
 */
public interface DatedItem extends Dated {

    /**
     * Возвращает объект способный сконвертировать эту сущность в секцию документа
     *
     * @return объект-конвертер или null если такового нет
     */
    DatedItemConverter getSectionConverter();

    /**
     * Возвращает экземпляр вспомогательного бина для сериализации/десериализации
     *
     * @return экземпляр вспомогательного бина
     */
    StreamHelperBean getHelperBean();
}
