package constructor.dom;

/**
 * ��������� ����������� �������� DOM ��������
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public interface AttributeHandler {

    /**
     * ������������ �������
     *
     * @param _id      ������������� ��������
     * @param _value   �������� ��������
     * @param _blank   ������ �� ����������� ������
     * @param _factory ������ �� ������� ������������� ��������. �����
     *                 ������������ ��� �������� ��������� �������� �� �� ��������������
     * @throws AttributeHandlerException ���� �� ����������
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
