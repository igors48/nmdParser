package constructor.dom;

import java.util.List;

/**
 * Шаблон адаптера создаваемого объекта с идентификатором
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public interface Blank {

    /**
     * Устанавливает идентификатор объекта
     *
     * @param _id идентификатор
     */
    void setId(String _id);

    /**
     * Возвращает список идентификаторов используемых объектов
     *
     * @return список идентификаторов
     */
    List<UsedObject> getUsedObjects();
}
