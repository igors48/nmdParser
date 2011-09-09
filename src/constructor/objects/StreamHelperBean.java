package constructor.objects;

/**
 * Интерфейс вспомогательного класса для
 * сериализации/десериализации объектов
 *
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public interface StreamHelperBean {

    /**
     * Сохраняет объект во вспомогательном классе
     *
     * @param _object сохраняемый объект
     */
    void store(Object _object);

    /**
     * Восстанавливает объект из вспомогательного класса
     *
     * @return восстановленный объект или null если не получилось
     */
    Object restore();
}
