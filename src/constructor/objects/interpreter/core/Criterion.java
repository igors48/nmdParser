package constructor.objects.interpreter.core;

import dated.DatedItem;

/**
 * �������� ������ ��������� ������
 *
 * @author Igor Usenko
 *         Date: 04.01.2009
 */
public interface Criterion {

    /**
     * ��������� �������� � ��������� ���������
     */
    void reset();

    /**
     * ��������� ������������ �������� ������ ��������
     *
     * @param _item ������� ������
     * @return true ���� ������� ������������� ��������, false ���� ���
     */
    boolean accept(DatedItem _item);
}
