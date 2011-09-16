package downloader;

import html.HttpData;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс загрузчика web данных
 *
 * @author Igor Usenko
 *         Date: 01.12.2008
 */

public interface BatchLoader {

    /**
     * Загружает данные от адресов указанных в списке. Блокирует до завершения.
     *
     * @param _urls                  список нужных адресов
     * @param _pauseBetweenRequests интервал между запросами
     * @return список образов загруженных данных. Если загрузка данных
     *         не удалась - возвращаются пустые данные
     */
    Map<String, HttpData> loadUrls(List<String> _urls, long _pauseBetweenRequests);

    
    /**
     * Обрабатывает список запросов
     *
     * @param _requests список запросов
     * @return результат обработки
     */
    //Map<RequestList, HttpData> load(List<RequestList> _requests);

    /**
     * Загружает данные с указанного урла, подставляя указанный реферер
     *
     * @param _url     адрес
     * @param _referer реферер
     * @return загруженные данные
     */
    HttpData loadUrlWithReferer(String _url, String _referer);

    /**
     * Возвращает информацию о том не был ли прерван процесс обработки
     *
     * @return true если был прерван false если небыл
     */
    boolean cancelled();
}
