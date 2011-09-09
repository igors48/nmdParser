package variables;

import java.util.Iterator;

/**
 * ��������� ��������� ����������
 *
 * @author Igor Usenko
 *         Date: 19.07.2009
 */
public interface VariableIterator extends Iterator {

    /**
     * ���������� ������� ������ ���������
     *
     * @return �������� �������
     */
    int getIndex();

    /**
     * ���������� ������� ���� ���������
     *
     * @return �������� �����
     */
    int getKey();

    /**
     * ���������� ���������� ��������� � ���������
     *
     * @return ���������� ���������
     */
    int getSize();
}
