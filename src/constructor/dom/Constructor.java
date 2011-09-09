package constructor.dom;

/**
 * Интерфейс конструктора объектов. Задача : по имени и типу
 * создать объект готовый к работе
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Constructor {

    /**
     * Создает объект по имени и типу.
     *
     * @param _id   - имя
     * @param _type - тип
     * @return экземпляр объекта или null если указаный объект не найден
     * @throws ConstructorException - если произошла ошибка
     */
    Blank create(final String _id, final ObjectType _type) throws ConstructorException;

    public class ConstructorException extends Exception {

        public ConstructorException() {
        }

        public ConstructorException(String s) {
            super(s);
        }

        public ConstructorException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ConstructorException(Throwable throwable) {
            super(throwable);
        }
    }
}
