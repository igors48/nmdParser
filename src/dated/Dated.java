package dated;

import java.util.Date;

/**
 * Интерфейс сущности связанной с датой
 *
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public interface Dated {

    /**
     * Возвращает дату с которой связана эта сущность
     *
     * @return дата
     */
    Date getDate();

}
