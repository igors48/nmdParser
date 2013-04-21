package work.unit.fetcher;

import constructor.objects.source.core.fetcher.RssFeedFetcher;
import dated.item.modification.Modification;
import http.BatchLoader;
import org.junit.Test;
import timeservice.StillTimeService;
import timeservice.TimeService;
import work.unit.constructor.processor.mock.PageLoaderMock;

import java.util.List;

/**
 * Author : Igor Usenko ( igors48@gmail.com )
 * Date : 21.04.13
 */
public class RssFeedFetcherTest {

    @Test
    public void allFeedsReaded() throws Exception {
        TimeService timeService = new StillTimeService();
        BatchLoader batchLoader = new PageLoaderMock("p");

        RssFeedFetcher fetcher = new RssFeedFetcher("url", timeService, batchLoader);
        List<Modification> modifications = fetcher.getModifications();

    }

}
