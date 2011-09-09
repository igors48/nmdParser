package constructor.objects.output.core;

/**
 * ��������� ������������� ��������������� ���������
 *
 * @author Igor Usenko
 *         Date: 25.11.2009
 */
public interface ForEachPostProcessor {

    /**
     * �����-�� ������� ������������ �������� � �������������� ������� �����
     *
     * @param _dir  ������ ��� �������� � ������
     * @param _name ��� �����
     */
    void process(String _dir, String _name);
}
