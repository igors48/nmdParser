package constructor.dom;

/**
 * ��������� ������������ ��������. ������ : �� ����� � ����
 * ������� ������ ������� � ������
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Constructor {

    /**
     * ������� ������ �� ����� � ����.
     *
     * @param _id   - ���
     * @param _type - ���
     * @return ��������� ������� ��� null ���� �������� ������ �� ������
     * @throws ConstructorException - ���� ��������� ������
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
