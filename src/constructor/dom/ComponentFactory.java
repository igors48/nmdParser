package constructor.dom;

/**
 * Интерфейс сущности, которая по строковому идентификатору
 * может вернуть шаблон объекта и (или) обработчик загрузки
 * этого объекта
 *
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public interface ComponentFactory {

    /**
     * Возвращает шаблон объекта по его идентификатору
     *
     * @param _id идентификатор
     * @return шаблон или null если не получилось
     */
    Blank getBlank(final String _id);

    /**
     * Возвращает обработчик для загрузки объекта на основании идентификаторв
     *
     * @param _id _id идентификатор
     * @return обработчик или null если не получилось
     */
    ElementHandler getHandler(final String _id);
}
