package constructor.objects.interpreter.core;

import java.util.List;

/**
 * ��������� ����������� ��������. ������ - ������� �������� �� ���������
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface PageAnalyser {

    /**
     * ��������� �������� �� ���������
     *
     * @param _page �������� ��������
     * @return ������ ����������
     */
    List<Fragment> getFragments(final Page _page);
}
