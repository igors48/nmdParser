package constructor.objects.source.core;

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

    ModificationFetcher createFetcher(FetcherType _type, List<String> _urls, TimeService _timeService) throws FetcherFactoryException;

    public class FetcherFactoryException extends Exception {

        public FetcherFactoryException(String _s) {
            super(_s);
        }
    }
}
