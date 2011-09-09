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
     * ���������� ������������ ���������� ���������� � ����� ������
     *
     * @return ������������ ���������� ����������
     */
    int getMaxConnectionsPerHost();

    /**
     * ���������� ������������ ���������� ����������
     *
     * @return ������������ ���������� ����������
     */
    int getMaxTotalConnections();

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
     * ���������� ���������� �������� ������ � ����
     *
     * @return ���������� �������� ������ � ����
     */
    int getCorePoolSize();

    /**
     * ���������� ����������� ���������� ������ � ����
     *
     * @return ����������� ���������� ������ � ����
     */
    int getMaxPoolSize();

    /**
     * ���������� ����� ����� ����������� �����
     *
     * @return ����� ����� ����������� �����
     */
    int getKeepAliveTime();

    /**
     * ���������� ����� �������� ������ � ���� ����������
     *
     * @return ����� �������� ������ � ���� ����������
     */
    long getCacheStorageTime();

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
     * ���������� ������������ ������ ��� ������������ � �������� �����������
     *
     * @return ������������ ������
     */
    int getResourceImageMaximumWidth();

    /**
     * ���������� ������������ ������ ��� ������������ � �������� �����������
     *
     * @return ������������ ������
     */
    int getResourceImageMaximumHeight();

    /**
     * ���������� ������ �� ���� ����������� � �������� ����������� �����-������
     *
     * @return true ���� ������ ���� �����-������ false ���� ��� �� �����������
     */
    boolean isResourceImageGrayscale();

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

    /**
     * ���������� �������� ������� ��� GoogleReader
     *
     * @return �������� ������� ��� GoogleReader
     */
    String getGoogleReaderRoot();

    /**
     * ���������� ������� ���������
     *
     * @throws SettingsException ���� ��������� �� �������
     */
    void valid() throws SettingsException;

    public class SettingsException extends Exception {

        public SettingsException() {
        }

        public SettingsException(String _s) {
            super(_s);
        }

        public SettingsException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public SettingsException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
