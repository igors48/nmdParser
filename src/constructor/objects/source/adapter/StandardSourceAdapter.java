package constructor.objects.source.adapter;

import app.controller.Controller;
import cloud.PropertiesCloud;
import constructor.objects.AdapterException;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.source.configuration.FetcherType;
import constructor.objects.source.configuration.SourceConfiguration;
import constructor.objects.source.core.*;
import constructor.objects.source.core.fetcher.CustomFetcher;
import constructor.objects.source.core.processor.NoopModificationProcessor;
import constructor.objects.source.core.processor.UrlModificator;
import constructor.objects.strategies.StoreStrategy;
import timeservice.TimeService;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 23.03.2009
 */
public class StandardSourceAdapter extends AbstractSourceAdapter {

    private final SourceConfiguration configuration;
    private final FetcherFactory factory;

    public StandardSourceAdapter(final PropertiesCloud _cloud, final SourceConfiguration _configuration, final FetcherFactory _factory, final ModificationListStorage _storage, final TimeService _timeService, final Controller _controller) {
        super(_timeService, _controller, _cloud, _storage);

        Assert.notNull(_configuration, "Configuration is null.");
        this.configuration = _configuration;

        Assert.notNull(_factory, "Factory is null.");
        this.factory = _factory;
    }

    public String getId() throws AdapterException {
        return this.configuration.getId();
    }

    public ModificationFetcher getFetcher() throws AdapterException {

        try {
            ModificationFetcher result;

            if (this.configuration.getFetcherType() == FetcherType.CUSTOM) {
                result = new CustomFetcher(new ChainProcessor(this.configuration.getCustomAdapter()), this.timeService);
            } else {
                result = this.factory.createFetcher(this.configuration.getFetcherType(), this.configuration.getFetchedUrls(), this.timeService);
            }

            return result;
        } catch (FetcherFactory.FetcherFactoryException e) {
            throw new AdapterException(e);
        }
    }

    public ModificationProcessor getProcessor() throws AdapterException {
        ModificationProcessor result = new NoopModificationProcessor();

        if (this.configuration.getProcessor() != null) {
            result = new UrlModificator(new ChainProcessor(this.configuration.getProcessor()));
        }

        return result;
    }

    public StoreStrategy getStoreStrategy() throws AdapterException {

        try {
            return createStoreStrategy(Integer.valueOf(this.configuration.getStoreDays()));
        } catch (RuntimeException e) {
            throw new AdapterException("Can`t create store strategy for source [ " + getId() + " ].", e);
        }
    }

}
