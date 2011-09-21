package http;

import app.controller.Controller;
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
     * @param _controller контроллер процесса
     * @return список образов загруженных данных. Если загрузка данных
     *         не удалась - возвращаются пустые данные
     */
    Map<String, HttpData> loadUrls(List<String> _urls, long _pauseBetweenRequests, Controller _controller);

    /**
     * Загружает данные с указанного урла, подставляя указанный реферер
     *
     * @param _url     адрес
     * @param _referer реферер
     * @return загруженные данные
     */
    HttpData loadUrlWithReferer(String _url, String _referer);

    HttpData loadUrl(String _url);
    
}
