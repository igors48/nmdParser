package app.cli.blitz;

import app.api.ApiFacade;
import app.cli.blitz.request.BlitzRequest;

import java.util.List;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public interface BlitzRequestHandler {

    /**
     * ќбрабатывает блиц запрос
     *
     * @param _request    параметры запроса
     * @param _forcedDays количество дней дл€ принудительного обновлени€ -1 - нет такого; 0 - все; >0 - количество дней
     * @return —писок полных имен сформированных файлов
     * @throws app.api.ApiFacade.FatalException
     *          если не получилось
     */
    List<String> processBlitzRequest(BlitzRequest _request, int _forcedDays) throws ApiFacade.FatalException;

}
