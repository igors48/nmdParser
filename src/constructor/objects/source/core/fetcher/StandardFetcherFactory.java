package constructor.objects.source.core.fetcher;

import constructor.objects.source.configuration.FetcherType;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.source.core.ModificationFetcher;
import http.BatchLoader;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

/**
 * ����������� ������� ��������
 *
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class StandardFetcherFactory implements FetcherFactory {

    private final BatchLoader batchLoader;

    public StandardFetcherFactory(final BatchLoader _batchLoader) {
        Assert.notNull(_batchLoader, "Batch loader is null.");
        this.batchLoader = _batchLoader;
    }

    public ModificationFetcher createFetcher(final FetcherType _type, final List<String> _urls, final TimeService _timeService) throws FetcherFactoryException {
        Assert.notNull(_type, "Fetcher type is null.");
        Assert.notNull(_urls, "Url list is null.");
        Assert.notNull(_timeService, "Time service is null.");

        ModificationFetcher result = null;

        if (_type == FetcherType.RSS) {
            result = new RssFeedFetcher(_urls.get(0), _timeService, this.batchLoader);
        }

        if (_type == FetcherType.URL) {
            result = new UrlFetcher(_urls, _timeService);
        }

        if (result == null) {
            throw new FetcherFactoryException("Can not create fetcher for type [ " + _type + " ].");
        }

        return result;
    }
}
