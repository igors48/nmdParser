package constructor.objects.source.core.fetcher;

import app.workingarea.Settings;
import constructor.objects.source.configuration.FetcherType;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.source.core.ModificationFetcher;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

/**
 * Стандартная фабрика фетчеров
 *
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class StandardFetcherFactory implements FetcherFactory {

    public ModificationFetcher createFetcher(final FetcherType _type, final List<String> _urls, final TimeService _timeService, final Settings _settings) throws FetcherFactoryException {
        Assert.notNull(_type, "Fetcher type is null.");
        Assert.notNull(_urls, "Url list is null.");
        Assert.notNull(_timeService, "Time service is null.");

        ModificationFetcher result = null;

        if (_type == FetcherType.RSS) {
            result = new RssFeedFetcher(_urls.get(0), _timeService, _settings.getMaxTryCount(), _settings.getErrorTimeout(), _settings.getMinTimeout());
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
