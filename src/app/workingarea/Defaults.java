package app.workingarea;

/**
 * ��������� ��������� ����� ��������� ��������� � ����� ��������
 * ������������, ������� ����� ������������ �� ���������
 *
 * @author Igor Usenko
 *         Date: 11.04.2009
 */
public interface Defaults {

    /**
     * ���������� ��� ���������� ��������� ���������
     *
     * @return ��� ��������� ��� ������ ������ ���� �� ����������
     */
    String getDefaultSettingsName();

    /**
     * ���������� ��� ���������� �������� ������������
     *
     * @return ��� �������� ������������ ��� ������ ������ ���� �� ����������
     */
    String getDefaultWorkspaceName();

    /**
     * ���������� ������� ��� ���������
     *
     * @return ��� �������� ��� ������ ������ ���� �� ����������
     */
    String getSettingsDirectory();

    /**
     * ���������� ������� ��� ������� �����������
     *
     * @return ��� �������� ��� ������ ������ ���� �� ����������
     */
    String getWorkspacesDirectory();

    /**
     * ���������� �������� ������� ��� ��������� �� ���������
     *
     * @return �������� ������� ��� ��������� �� ���������
     */
    String getDefaultStorageRoot();

    /**
     * ���������� ������������ ���� �������� ��������� � ��������� �� ���������
     *
     * @return ������������ ���� �������� ��������� � ��������� �� ��������� � �������������
     */
    long getDefaultStoragePeriod();

    /**
     * ���������� ��������� ���������� ��� Google Reader
     *
     * @return ��������� ���������� ��� Google Reader
     */
    String getGoogleReaderDirectory();
}
