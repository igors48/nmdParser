package constructor.objects.interpreter.core;

import dated.DatedItem;

/**
 * Критерий отбора элементов данных
 *
 * @author Igor Usenko
 *         Date: 04.01.2009
 */
public interface Criterion {

    /**
     * Переводит критерий в начальное состояние
     */
    void reset();

    /**
     * Проверяет соответствие элемента данных критерию
     *
     * @param _item элемент данных
     * @return true если елемент соответствует критерию, false если нет
     */
    boolean accept(DatedItem _item);
}
