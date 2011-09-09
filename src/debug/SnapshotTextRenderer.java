package debug;

import java.util.List;

/**
 * ��������������� ������� � ��������� �������������
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public interface SnapshotTextRenderer {

    /**
     * ��������� �� �������� ������ �����
     *
     * @param _snapshot ���������� �������
     * @return ������ �����
     */
    List<String> render(Snapshot _snapshot);

    /**
     * ���������� �������, ������� ����� ��� �����������
     *
     * @return �������
     */
    String getPrefix();
}
