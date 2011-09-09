package constructor.dom;

import org.w3c.dom.Element;

/**
 * Интерфейс обработчика DOM элемента
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public interface ElementHandler {

    /**
     * Обрабатывает элемент
     *
     * @param _element обрабатываемый элемент
     * @param _blank   ссылка на формируемый объект
     * @param _factory ссылка на фабрику конструкторов объектов. Может
     *                 понадобиться при загрузке вложенных объектов по их идентификатору
     * @throws ElementHandlerException если не получилось
     */
    void handle(final Element _element, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException;

    public class ElementHandlerException extends Exception {

        public ElementHandlerException() {
        }

        public ElementHandlerException(String s) {
            super(s);
        }

        public ElementHandlerException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ElementHandlerException(Throwable throwable) {
            super(throwable);
        }
    }
}
