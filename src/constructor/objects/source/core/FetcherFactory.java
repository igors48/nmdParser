package constructor.objects.source.core;

import app.workingarea.Settings;
import constructor.objects.source.configuration.FetcherType;
import timeservice.TimeService;

import java.util.List;

/**
 * ������� �������� �����������
 *
 * @author Igor Usenko
 *         Date: 23.03.2009
 */
public interface FetcherFactory {

    /**
     * ���������� ������� � ������ ��������� ������� �����������
     *
     * @param _type        ��� �������
     * @param _urls        ������ ������� ������� ��� �������
     * @param _timeService ������ �������
     * @param _settings    ���������
     * @return ��������� �������
     * @throws FetcherFactoryException ���� �� ����������
     */
    ModificationFetcher createFetcher(FetcherType _type, List<String> _urls, TimeService _timeService, final Settings _settings) throws FetcherFactoryException;

    public class FetcherFactoryException extends Exception {

        public FetcherFactoryException() {
        }

        public FetcherFactoryException(String _s) {
            super(_s);
        }

        public FetcherFactoryException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public FetcherFactoryException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
