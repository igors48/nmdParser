package work.unit.fetcher;

import constructor.objects.source.core.fetcher.RssFeedFetcher;
import dated.item.modification.Modification;
import http.BatchLoader;
import junit.framework.Assert;
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

    private static final String SAMPLE_FEED = "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:media=\"http://search.yahoo.com/mrss/\" xmlns:content=\"http://purl.org/rss/1.0/modules/content/\" xmlns:inosmi=\"http://inosmi.ru\" version=\"2.0\">\n" +
            "<channel>\n" +
            "<title>channel title</title>\n" +
            "<link>http://www.inosmi.ru</link>\n" +
            "<description>channel description</description>\n" +
            "<language>ru</language>\n" +
            "<copyright>InoSMI</copyright>\n" +
            "<item>\n" +
            "<title>item title 1</title>\n" +
            "<link>http://inosmi.ru/world/20130421/208313192.html</link>\n" +
            "<dc:subject>dc-subject</dc:subject>\n" +
            "<pubDate>, 21 Apr 2013 13:45:00 +0400</pubDate>\n" +
            "<description>\n" +
            "item description 1\n" +
            "</description>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>item title 2</title>\n" +
            "<link>http://inosmi.ru/world/20130421/208313192.html</link>\n" +
            "<dc:subject>dc-subject</dc:subject>\n" +
            "<pubDate>, 21 Apr 2013 13:45:00 +0400</pubDate>\n" +
            "<description>\n" +
            "item description 2\n" +
            "</description>\n" +
            "</item>\n" +
            "</channel>\n" +
            "</rss>";

    @Test
    public void allFeedsRead() throws Exception {
        TimeService timeService = new StillTimeService();
        BatchLoader batchLoader = new PageLoaderMock(SAMPLE_FEED);

        RssFeedFetcher fetcher = new RssFeedFetcher("url", timeService, batchLoader);
        List<Modification> modifications = fetcher.getModifications();

        Assert.assertEquals(2, modifications.size());
    }

}
