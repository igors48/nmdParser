package constructor.dom;

import org.w3c.dom.Element;

/**
 * ��������� ����������� DOM ��������
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public interface ElementHandler {

    /**
     * ������������ �������
     *
     * @param _element �������������� �������
     * @param _blank   ������ �� ����������� ������
     * @param _factory ������ �� ������� ������������� ��������. �����
     *                 ������������ ��� �������� ��������� �������� �� �� ��������������
     * @throws ElementHandlerException ���� �� ����������
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
