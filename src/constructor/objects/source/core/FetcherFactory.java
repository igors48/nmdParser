package constructor.objects.source.core;

import app.workingarea.Settings;
import constructor.objects.source.configuration.FetcherType;
import timeservice.TimeService;

import java.util.List;

/**
 * Фабрика фетчеров модификаций
 *
 * @author Igor Usenko
 *         Date: 23.03.2009
 */
public interface FetcherFactory {

    /**
     * Возвращает готовый к работе экземпляр фетчера модификаций
     *
     * @param _type        тип фетчера
     * @param _urls        список рабочих адресов для фетчера
     * @param _timeService сервис времени
     * @param _settings    установки
     * @return экземпляр фетчера
     * @throws FetcherFactoryException если не получилось
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
