package constructor.objects.strategies;

/**
 * ��������� ���������� ����������
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public interface UpdateStrategy {

    /**
     * ���������� "�������������" ����������
     *
     * @return true ���� ���������� ���������� false ���� ���
     */
    boolean needsUpdate();
}
