package constructor.objects.processor;

import debug.Snapshot;

/**
 * ��������� ������ � ���������� �����������
 *
 * @author Igor Usenko
 *         Date: 27.06.2010
 */
public interface Debug {
    /**
     * ��������� ���������� �������
     *
     * @param _snapshot �������
     */
    void appendSnapshot(Snapshot _snapshot);
}
