package dated;

import constructor.objects.StreamHelperBean;

/**
 * ��������� �������� ����������� �����-�� ���������� � �����-�� �����
 *
 * @author Igor Usenko
 *         Date: 07.11.2008
 */
public interface DatedItem extends Dated {

    /**
     * ���������� ������ ��������� ��������������� ��� �������� � ������ ���������
     *
     * @return ������-��������� ��� null ���� �������� ���
     */
    DatedItemConverter getSectionConverter();

    /**
     * ���������� ��������� ���������������� ���� ��� ������������/��������������
     *
     * @return ��������� ���������������� ����
     */
    StreamHelperBean getHelperBean();
}
