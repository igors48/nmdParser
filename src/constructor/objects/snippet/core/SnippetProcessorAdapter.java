package constructor.objects.snippet.core;

import constructor.objects.snippet.configuration.SnippetConfiguration;
import timeservice.TimeService;

/**
 * ��������� �������� ����������� ���������
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public interface SnippetProcessorAdapter {

    /**
     * ���������� ������������ ��������
     *
     * @return ������������ ��������
     * @throws SnippetProcessorAdapterException
     *          ���� �� ����������
     */
    SnippetConfiguration getSnippetConfiguration() throws SnippetProcessorAdapterException;

    /**
     * ���������� ����� ���������� ���������� ��������
     *
     * @return ����� ���������� ����������. -1 ���� ���������� �� �������
     * @throws SnippetProcessorAdapterException
     *          ���� �� ����������
     */
    long getLastUpdateTime() throws SnippetProcessorAdapterException;

    /**
     * ������������� ����� ���������� ���������� ��������
     *
     * @param _time ��������������� �����
     * @throws SnippetProcessorAdapterException
     *          ���� �� ����������
     */
    void setLastUpdateTime(long _time) throws SnippetProcessorAdapterException;

    /**
     * ���������� ������������ ���� ������
     *
     * @return ���� ������
     */
    TimeService getTimeService();

    public class SnippetProcessorAdapterException extends Exception {

        public SnippetProcessorAdapterException() {
        }

        public SnippetProcessorAdapterException(final String _s) {
            super(_s);
        }

        public SnippetProcessorAdapterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public SnippetProcessorAdapterException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
