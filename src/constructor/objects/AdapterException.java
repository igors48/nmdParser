package constructor.objects;

/**
 * ���������� �������� �������. ������������� � ��������� ��
 * �������������� �������� � ������������� ��� � ��������
 *
 * @author Igor Usenko
 *         Date: 22.03.2009
 */
public class AdapterException extends Exception {
    public AdapterException() {
    }

    public AdapterException(String _s) {
        super(_s);
    }

    public AdapterException(String _s, Throwable _throwable) {
        super(_s, _throwable);
    }

    public AdapterException(Throwable _throwable) {
        super(_throwable);
    }
}
