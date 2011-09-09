package constructor.objects.strategies;

import dated.Dated;

/**
 * ��������� ���������� ����� �������� ������������ ���������
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public interface StoreStrategy {

    /**
     * ���������� ������������� ����������� �������� ������������� ��������
     *
     * @param _dated ������������ �������
     * @return true ���� ����� ������� � ������ false ���� ����� �� �������
     */
    boolean accepted(Dated _dated);
}
