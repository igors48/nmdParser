package constructor.objects.interpreter.core;

import java.util.List;

/**
 * Интерфейс анализатора списка страниц. Задача - на основе модификации
 * и образа страницы, определить перечень урлов для анализа
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface PageListAnalyser {

    /**
     * На основании содержимого страницы формирует список страниц для анализа
     *
     * @param _page страница
     * @return список страниц для анализа
     */
    List<PageListItem> getPageList(Page _page);
}
