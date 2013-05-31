package constructor.objects.source.core.fetcher;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import constructor.objects.source.core.ModificationFetcher;
import dated.item.modification.Modification;
import html.HttpData;
import http.BatchLoader;
import http.Result;
import http.data.DataUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;

import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import static constructor.objects.source.core.fetcher.RssFeedFetcherTools.*;
import static http.data.DataUtil.convertToUtf8;
import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.11.2008
 */
public class RssFeedFetcher implements ModificationFetcher {

    private final String url;
    private final TimeService timeService;
    private final BatchLoader batchLoader;

    private static final Log LOG = LogFactory.getLog(RssFeedFetcher.class);

    public RssFeedFetcher(final String _url, final TimeService _timeService, final BatchLoader _batchLoader) {
        Assert.isValidString(_url, "URL is not valid.");
        this.url = _url;

        Assert.notNull(_timeService, "Time service is null.");
        this.timeService = _timeService;

        Assert.notNull(_batchLoader, "Batch loader is null");
        this.batchLoader = _batchLoader;
    }

    public List<Modification> getModifications() {
        List<Modification> result = newArrayList();

        if (this.url.length() > 1) {
            LOG.debug("Load feeds from " + this.url);

            List<SyndEntry> entries = loadEntries(this.url);
            result = mapEntries(entries);
        }

        return result;
    }

    private List<Modification> mapEntries(final List<SyndEntry> _entries) {
        List<Modification> result = newArrayList();

        for (SyndEntry entry : _entries) {
            Modification modification = mapEntry(entry);

            if (modification != null) {
                result.add(modification);
            }
        }

        return result;
    }

    private Modification mapEntry(final SyndEntry _entry) {
        String feedLink = _entry.getLink();

        if (!urlValid(feedLink)) {
            return null;
        }

        Date date = createDate(_entry.getPublishedDate(), this.timeService.getCurrentDate());
        String title = createTitle(_entry.getTitle(), feedLink);
        String description = createDescription(_entry);

        String titleInUtf8 = convertToUtf8(title);
        String descriptionInUtf8 = convertToUtf8(description);

        return new Modification(date, feedLink, titleInUtf8, descriptionInUtf8);
    }

    private List<SyndEntry> loadEntries(final String _url) {

        List<SyndEntry> result = newArrayList();

        try {
            HttpData feedData = this.batchLoader.loadUrl(_url);

            if (feedData.getResult() != Result.OK) {
                return result;
            }

            String feedAsString = DataUtil.getString(feedData.getData());
            Reader feedAsReader = new StringReader(feedAsString);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(feedAsReader);

            for (int i = 0; i < feed.getEntries().size(); i++) {
                SyndEntry entry = (SyndEntry) feed.getEntries().get(i);
                result.add(entry);
            }
        } catch (Exception e) {
            LOG.error("Error reading RSS feed", e);
        }

        return result;
    }
}
