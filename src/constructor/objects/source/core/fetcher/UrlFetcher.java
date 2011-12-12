package constructor.objects.source.core.fetcher;

import constructor.objects.source.core.ModificationFetcher;
import dated.item.modification.Modification;
import timeservice.TimeService;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Формирует список модификаций из списка содержащихся в нем урлов
 *
 * @author Igor Usenko
 *         Date: 26.05.2009
 */
public class UrlFetcher implements ModificationFetcher {

    private final List<String> urls;
    private final TimeService timeService;

    public UrlFetcher(final List<String> _urls, final TimeService _timeService) {
        Assert.notNull(_urls, "Url list is null.");
        Assert.isFalse(_urls.isEmpty(), "Url list is empty.");
        Assert.notNull(_timeService, "Time service is null.");

        this.urls = _urls;
        this.timeService = _timeService;
    }

    public List<Modification> getModifications() throws ModificationFetcherException {
        List<Modification> result = newArrayList();

        for (String current : this.urls) {
            result.add(new Modification(this.timeService.getCurrentDate(), current));
        }

        return result;
    }
}
