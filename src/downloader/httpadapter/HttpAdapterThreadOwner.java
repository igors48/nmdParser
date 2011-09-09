package downloader.httpadapter;

/**
 * ��������� ��������� �����
 *
 * @author Igor Usenko
 *         Date: 03.04.2010
 */
public interface HttpAdapterThreadOwner {

    /**
     * ���������� ���������� � ������ ������ ���������
     *
     * @return true ���� �������� � ������ ������ �������� false ���� �������� � ������� ������
     */
    boolean cancelling();
}
