package constructor.objects;

/**
 * ��������� ���������������� ������ ���
 * ������������/�������������� ��������
 *
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public interface StreamHelperBean {

    /**
     * ��������� ������ �� ��������������� ������
     *
     * @param _object ����������� ������
     */
    void store(Object _object);

    /**
     * ��������������� ������ �� ���������������� ������
     *
     * @return ��������������� ������ ��� null ���� �� ����������
     */
    Object restore();
}
