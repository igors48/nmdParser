package constructor.objects.interpreter.core;

import java.util.List;

/**
 * Интерфейс анализатора страницы. Задача - разбить страницу на фрагменты
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface PageAnalyser {

    /**
     * Разбивает страницу на фрагменты
     *
     * @param _page исходная страница
     * @return список фрагментов
     */
    List<Fragment> getFragments(final Page _page);
}
