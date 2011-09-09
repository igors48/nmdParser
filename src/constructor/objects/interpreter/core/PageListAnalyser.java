package constructor.objects.interpreter.core;

import java.util.List;

/**
 * ��������� ����������� ������ �������. ������ - �� ������ �����������
 * � ������ ��������, ���������� �������� ����� ��� �������
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface PageListAnalyser {

    /**
     * �� ��������� ����������� �������� ��������� ������ ������� ��� �������
     *
     * @param _page ��������
     * @return ������ ������� ��� �������
     */
    List<PageListItem> getPageList(Page _page);
}
