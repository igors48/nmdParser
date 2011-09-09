package constructor.objects.simpler.core;

import app.controller.Controller;
import cloud.PropertiesCloud;
import constructor.objects.AdapterException;
import constructor.objects.source.core.AbstractSourceAdapter;
import constructor.objects.source.core.ModificationFetcher;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.strategies.StoreStrategy;
import timeservice.TimeService;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 07.07.2010
 */
public class SimplerSourceAdapter extends AbstractSourceAdapter {

    private final String id;
    private final int storeDays;
    private final ModificationFetcher fetcher;

    public SimplerSourceAdapter(final String _id, final ModificationFetcher _fetcher, final int _storeDays, final TimeService _timeService, final Controller _controller, final PropertiesCloud _cloud, final ModificationListStorage _storage) {
        super(_timeService, _controller, _cloud, _storage);

        Assert.isValidString(_id, "Id is not valid");
        this.id = _id;

        Assert.greaterOrEqual(_storeDays, 0, "Store days < 0");
        this.storeDays = _storeDays;

        Assert.notNull(_fetcher, "Fetcher is null");
        this.fetcher = _fetcher;
    }

    public String getId() throws AdapterException {
        return this.id;
    }

    public ModificationFetcher getFetcher() throws AdapterException {
        return this.fetcher;
    }

    public StoreStrategy getStoreStrategy() throws AdapterException {
        return createStoreStrategy(this.storeDays);
    }
}
