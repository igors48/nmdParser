package constructor.objects.source.core.fetcher;

import com.sun.syndication.feed.synd.SyndContentImpl;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

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
        Modification result = null;

        Date date = _entry.getPublishedDate();

        if (date == null) {
            date = this.timeService.getCurrentDate();
        }

        String feedLink = _entry.getLink();

        try {
            URL feedUrl = new URL(feedLink);
            String host = feedUrl.getHost();

            if (host.isEmpty()) {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }

        String title = _entry.getTitle();

        if ((title == null) || (title.isEmpty())) {
            title = feedLink;
        }

        String description = getDescription(_entry);

        if (feedLink.length() > 0) {
            String titleInUtf8 = DataUtil.convertToUtf8(title);
            String descriptionInUtf8 = DataUtil.convertToUtf8(description);

            result = new Modification(date, feedLink, titleInUtf8, descriptionInUtf8);
        }

        return result;
    }

    private String getDescription(final SyndEntry _entry) {
        String title = _entry.getTitle();
        List contentsList = _entry.getContents();
        StringBuilder contents = new StringBuilder();

        if (contentsList != null && (!contentsList.isEmpty())) {

            for (Object current : contentsList) {
                contents.append(((SyndContentImpl) current).getValue());
            }
        }

        String description = _entry.getDescription() == null ? "" : _entry.getDescription().getValue();

        String result = contents.length() != 0 ? contents.toString() : description;

        return result.isEmpty() ? title : result;
    }

    private List<SyndEntry> loadEntries(final String _url) {

        List<SyndEntry> result = newArrayList();

        try {
            HttpData feedData = batchLoader.loadUrl(_url);

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
