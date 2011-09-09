package constructor.objects.storage.local.core;

/**
 * ��������� ������� ������ ��� ���������
 *
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public interface StoreItem {

    /**
     * ���������� ������������� ����� ���������
     *
     * @return ������������� �����
     */
    String getBranch();

    /**
     * ���������� ��� ������� ������
     *
     * @return ��� �������
     */
    String getName();

    /**
     * ���������� ������ ������� ������
     *
     * @return ������
     */
    long getSize();

    /**
     * ���������� ������ �� ���� ������� ��� ������� ����� ��������� ���������� ������
     *
     * @return true ���� ������ false
     */
    boolean isRemove();

    /**
     * ������� ������� ������
     *
     * @return true false
     */
    boolean remove();

    /**
     * ���������� ��������� � ������ ���� ����� ������� ��� ����������
     *
     * @return true ������� ��������� false ������������� ���������
     */
    boolean isRemoveExists();
}
