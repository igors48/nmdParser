package app.workingarea;

import converter.format.fb2.resource.Fb2ResourceConversionContext;

/**
 * ��������� � ������� ���������� ����������
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface Settings {

    /**
     * ���������� ������� ��� ��������� ������
     *
     * @return ���� � ��������
     */
    String getTempDirectory();

    /**
     * ��������� �������� ��� ���������������� / ����������� ��������
     *
     * @return ���� � ����� ��������
     */
    String getResourceDummy();

    /**
     * ��������� �������� ��� ���������������� / ����������� �������� � ���� ���� �������
     *
     * @return ���� ������ ���������� ����� ��������
     */
    byte[] getResourceDummyAsBytes();

    /**
     * ���������� ������� ��� ���������� ������
     *
     * @return ���� � ��������
     */
    String getDebugDirectory();

    /**
     * ���������� ���������� ������� ���������� ���������/������/���������
     *
     * @return ���������� �������
     */
    int getUpdateAttempts();

    /**
     * ���������� ������� ����� ��������� ���������� ���������/������/���������
     *
     * @return ������� � �������������
     */
    long getUpdateTimeout();

    /**
     * ���������� ����� ������������� ������
     *
     * @return true - ������ ������������ false - ������ �� ������������
     */
    boolean isProxyUsed();

    /**
     * ���������� ������������ ���������� ������ �����, ����� ������� �� ���� � ����
     *
     * @return ������������ ���������� ������
     */
    int getBannedListTreshold();

    /**
     * ���������� ������������ ������ ������ ���������� ������
     *
     * @return ������������ ������ ������
     */
    int getBannedListLimit();

    /**
     * ���������� ������ � ������ ������
     *
     * @return ������ � ������ ������
     */
    String getUserAgent();

    /**
     * ���������� ����� ������ �����
     *
     * @return ����� ������ �����
     */
    String getProxyHost();

    /**
     * ���������� ���� ������
     *
     * @return ���� ������
     */
    int getProxyPort();

    /**
     * ���������� ����� ��� ������
     *
     * @return ����� ��� ������
     */
    String getUserName();

    /**
     * ���������� ������ ��� ������
     *
     * @return ������ ��� ������
     */
    String getUserPassword();

    /**
     * ���������� ������������ ���������� ������� ��������� � �����
     * ����� ��� ��� ����� �������� ������
     *
     * @return ���������� �������
     */
    int getMaxTryCount();

    /**
     * ���������� ����� �������� �� ������ ����� ��� ��� ����� ������� �������
     *
     * @return ����� ��������
     */
    int getSocketTimeout();

    /**
     * ���������� ������� ����� ��������� ��������� � �����
     *
     * @return �������� ��������
     */
    int getErrorTimeout();

    /**
     * ���������� ����������� �������� ��������
     *
     * @return ����������� �������� ��������
     */
    int getMinTimeout();

    /**
     * ���������� ������������ ���������� �������� � ����� ��������� �����
     *
     * @return ������������ ���������� ��������
     */
    int getMaxFileNameLength();

    /**
     * ���������� ����� ������ ���� ��������
     *
     * @return true ���� ��� ������� false ���� ��������
     */
    boolean isResourceCacheEnabled();

    /**
     * ���������� �������� ������� ��������� ��������
     *
     * @return �������� ������� ��������� ��������
     */
    String getResourceCacheRoot();

    /**
     * ���������� ������������ ������� �������� ������ ����
     *
     * @return ������������ ������� �������� ������ ���� � �������������
     */
    long getResourceItemMaxAge();

    /**
     * ���������� ������������ ������ �������� ������ ����
     *
     * @return ������������ ������ �������� ������ ���� � ������
     */
    long getResourceItemMaxSize();

    /**
     * ���������� ������������ ������ ����
     *
     * @return ������������ ������ ����
     */
    long getResourceCacheMaxSize();

    /**
     * ���������� ������������ ����� ������ �������� �������� ����� �������� �� ���� � ����
     *
     * @return ������������ ���������� ������
     */
    int getExternalProcessMaxFailCount();

    /**
     * ���������� �������� ������� ��������� �� ���������
     *
     * @return �������� ������� ��������� �� ���������
     */
    String getDefaultStorageRoot();

    /**
     * ���������� ������ �������� ���������� � ��������� �� ���������
     *
     * @return ������ �������� ���������� � ��������� � �������������
     */
    long getDefaultStoragePeriod();

    /**
     * ���������� ����� ���������� ��������� � ��������� �� ����
     *
     * @return true ���� �� ������� � ������� false ���� ��������
     */
    boolean isFromNewToOld();

    /**
     * ���������� ���������� �������������� ���������� ���������
     *
     * @return ���������� �������������� ���������� ���������
     */
    int getPrecachedItemsCount();

    /**
     * ���������� �������� ����������� ��������
     *
     * @return �������� ����������� ��������
     */
    Fb2ResourceConversionContext getResourceConversionContext();

    public class SettingsException extends Exception {

        public SettingsException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
