package constructor.objects.interpreter.core;

import dated.DatedItem;

/**
 * ��������� ����������� ���������. ������ - �� ��������� ���������
 * ������������ ������� ������
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface FragmentAnalyser {

    /**
     * ���������� ������� ������ ���������� �� ���������
     *
     * @param _fragment �������� ��������
     * @return ������� ������ ��� null ���� �� ����������
     */
    DatedItem getItem(final Fragment _fragment);
}
