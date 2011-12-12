package constructor.objects.source.core.fetcher;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import constructor.objects.source.core.ModificationFetcher;
import dated.item.modification.Modification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;
import util.TimeoutTools;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static util.CollectionUtils.newArrayList;

/**
 * Получение списка модификаций из RSS канала
 *
 * @author Igor Usenko
 *         Date: 23.11.2008
 */
public class RssFeedFetcher implements ModificationFetcher {

    private String url;
    private final TimeService timeService;
    private final int tryCount;
    private final int timeOut;
    private final int minTimeOut;

    private final Log log;

    public RssFeedFetcher(final String _url, final TimeService _timeService, final int _tryCount, final int _timeOut, final int _minTimeOut) {
        Assert.notNull(_timeService, "Time service is null.");
        Assert.isValidString(_url, "URL is not valid.");
        Assert.greater(_tryCount, 0, "Try count <= 0");
        Assert.greater(_timeOut, 0, "Time out <= 0");
        Assert.greater(_minTimeOut, 0, "Minimum time out <= 0");

        this.url = _url;
        this.timeService = _timeService;
        this.tryCount = _tryCount;
        this.timeOut = _timeOut;
        this.minTimeOut = _minTimeOut;

        this.log = LogFactory.getLog(getClass());
    }

    public List<Modification> getModifications() {
        List<Modification> result = newArrayList();

        if (this.url.length() > 1) {
            this.log.debug("Load feeds from " + this.url);

            List<SyndEntry> entries = getEntries(this.url);
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
        String title = _entry.getTitle();

        // вот такое было в ЖЖ
        if ((title == null) || (title.isEmpty())) {
            title = feedLink;
        }

        String description = getDescription(_entry);

        if (feedLink.length() > 0) {
            result = new Modification(date, feedLink, title, description);
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

    private List<SyndEntry> getEntries(final String _url) {
        List<SyndEntry> result = newArrayList();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        int tryLeft = this.tryCount;
        boolean done = false;

        while (!done && tryLeft > 0) {

            Future<List<SyndEntry>> future = executor.submit(getCallableWrapper(_url));

            try {
                int timeout = TimeoutTools.getTimeout(this.minTimeOut, this.timeOut, this.tryCount - tryLeft, this.tryCount);
                this.log.debug(MessageFormat.format("Calculated feed fetcher timeout is [ {0} ] milliseconds", timeout));

                result = future.get(this.timeOut, TimeUnit.MILLISECONDS);
                done = true;
            } catch (InterruptedException e) {
                this.log.error("Error reading RSS feed", e);
                future.cancel(true);
                done = true;
            } catch (ExecutionException e) {
                this.log.error("Error reading RSS feed", e);
                future.cancel(true);
                done = true;
            } catch (TimeoutException e) {
                this.log.error("Timeout error reading RSS feed. Try left [ " + tryLeft + " ]", e);
                future.cancel(true);
            }

            --tryLeft;
        }

        executor.shutdown();

        return result;

    }

    private Callable<List<SyndEntry>> getCallableWrapper(final String _url) {

        return new Callable<List<SyndEntry>>() {

            public List<SyndEntry> call() {
                List<SyndEntry> result = newArrayList();

                try {
                    URL feedUrl = new URL(_url);

                    SyndFeedInput input = new SyndFeedInput();
                    SyndFeed feed = input.build(new XmlReader(feedUrl));

                    for (int i = 0; i < feed.getEntries().size(); i++) {
                        SyndEntry entry = (SyndEntry) feed.getEntries().get(i);
                        result.add(entry);
                    }
                } catch (Throwable e) {
                    log.error("Error reading RSS feed", e);
                }

                return result;
            }
        };
    }
}
