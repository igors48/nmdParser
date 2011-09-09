package app.workingarea;

import constructor.dom.Preprocessor;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.storage.Storage;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import debug.DebugConsole;
import downloader.Downloader;
import resource.ConverterFactory;
import timeservice.TimeService;

import java.util.Map;

import greader.GoogleReaderAdapter;

/**
 * �������� ��������� ��������. ��������� ������� �� ���������
 * �� � ������ �� ������� �����������. ��� ������� ������ ��
 * ��������� ���������
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public interface ServiceManager {

    /**
     * ������������� ������� ������� ��������
     *
     * @param _context ������� ��������
     */
    void setExternalContext(final Map<String, String> _context);

    /**
     * ���������� ������ �������
     *
     * @return ��������� ������� �������
     * @throws ServiceManagerException ���� �� ����������
     */
    TimeService getTimeService() throws ServiceManagerException;

    //todo ����� �� ��� � ������ ���������?
    /**
     * ���������� ������� �������� �����������
     *
     * @return ��������� �������
     * @throws ServiceManagerException ���� �� ����������
     */
    FetcherFactory getFetcherFactory() throws ServiceManagerException;

    /**
     * ���������� HTTP ���������
     *
     * @return ��������� ����������
     * @throws ServiceManagerException ���� �� ����������
     */
    Downloader getDownloader() throws ServiceManagerException;

    /**
     * ���������� ������� ����������� ������� �������� ��������
     *
     * @return ��������� �������
     * @throws ServiceManagerException ���� �� ����������
     */
    ConverterFactory getConverterFactory() throws ServiceManagerException;

    /**
     * ���������� ���������� �������
     *
     * @return ���������� �������
     */
    DebugConsole getDebugConsole();

    /**
     * ���������� ������� ��������
     *
     * @return ������� ��������
     */
    Map<String, String> getExternalContext();

    /**
     * ���������� ��� ��������
     *
     * @return ��� ��������
     */
    ResourceCache getResourceCache();

    /**
     * ���������� �������� ��� ������� ������� ���������
     *
     * @return �������� ��� ������� ������� ���������
     */
    ProcessWrapper getProcessWrapper();

    /**
     * ���������� ��������� �� ���������
     *
     * @return ��������� �� ���������
     * @throws ServiceManagerException ���� �� ����������
     */
    Storage getDefaultStorage() throws ServiceManagerException;

    /**
     * ���������� ����� ��������� (�������������� ��������� �������������, �� �� ��������������)
     */
    void activateReflectionMode();

    /**
     * ������������ ����� ��������� (�������������� ��������� ������������� � ��������������)
     */
    void deactivateReflectionMode();

    /**
     * ���������� ������������ �������� ������
     *
     * @return ������������ �������� ������
     */
    Preprocessor getPreprocessor();

    /**
     * ���������� ������� ��� ������ � Google Reader
     *
     * @return ������� ��� ������ � Google Reader
     * @throws ServiceManagerException ���� �� ����������
     */
    GoogleReaderAdapter getGoogleReaderAdapter() throws ServiceManagerException;

    /**
     * ��������� ������ ��������� ��������
     */
    void cleanup();

    public class ServiceManagerException extends Exception {

        public ServiceManagerException() {
        }

        public ServiceManagerException(String _s) {
            super(_s);
        }

        public ServiceManagerException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public ServiceManagerException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
