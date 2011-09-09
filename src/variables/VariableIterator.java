package variables;

import java.util.Iterator;

/**
 * Интерфейс итератора переменной
 *
 * @author Igor Usenko
 *         Date: 19.07.2009
 */
public interface VariableIterator extends Iterator {

    /**
     * Возвращает текущий индекс итератора
     *
     * @return значение индекса
     */
    int getIndex();

    /**
     * Возвращает текущий ключ переменой
     *
     * @return значение ключа
     */
    int getKey();

    /**
     * Возвращает количество элементов в итераторе
     *
     * @return количество элементов
     */
    int getSize();
}
