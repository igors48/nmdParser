package constructor.dom;

/**
 * ��������� ��������, ������� �� ���������� ��������������
 * ����� ������� ������ ������� � (���) ���������� ��������
 * ����� �������
 *
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public interface ComponentFactory {

    /**
     * ���������� ������ ������� �� ��� ��������������
     *
     * @param _id �������������
     * @return ������ ��� null ���� �� ����������
     */
    Blank getBlank(final String _id);

    /**
     * ���������� ���������� ��� �������� ������� �� ��������� ��������������
     *
     * @param _id _id �������������
     * @return ���������� ��� null ���� �� ����������
     */
    ElementHandler getHandler(final String _id);
}
