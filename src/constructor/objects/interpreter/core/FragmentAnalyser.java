package constructor.objects.interpreter.core;

import dated.DatedItem;

/**
 * Интерфейс анализатора фрагмента. Задача - на основании фрагмента
 * сформировать элемент данных
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface FragmentAnalyser {

    /**
     * Возвращает элемент данных полученных из фрагмента
     *
     * @param _fragment исходный фрагмент
     * @return элемент данных или null если не получилось
     */
    DatedItem getItem(final Fragment _fragment);
}
