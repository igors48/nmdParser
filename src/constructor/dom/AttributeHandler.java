package constructor.dom;

/**
 * Интерфейс обработчика атрибута DOM элемента
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public interface AttributeHandler {

    /**
     * Обрабатывает атрибут
     *
     * @param _id      идентификатор атрибута
     * @param _value   значение атрибута
     * @param _blank   ссылка на формируемый объект
     * @param _factory ссылка на фабрику конструкторов объектов. Может
     *                 понадобиться при загрузке вложенных объектов по их идентификатору
     * @throws AttributeHandlerException если не получилось
     */
    void handle(final String _id, final String _value, final Object _blank, final ConstructorFactory _factory) throws AttributeHandlerException;

    public class AttributeHandlerException extends Exception {

        public AttributeHandlerException() {
        }

        public AttributeHandlerException(String s) {
            super(s);
        }

        public AttributeHandlerException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public AttributeHandlerException(Throwable throwable) {
            super(throwable);
        }
    }
}
