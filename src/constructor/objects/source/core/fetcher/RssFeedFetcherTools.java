package constructor.objects.source.core.fetcher;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import util.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Author : Igor Usenko ( igors48@gmail.com )
 * Date : 23.04.13
 */
public class RssFeedFetcherTools {

    public static Date createDate(final Date _date, final Date _currentDate) {
        Assert.notNull(_currentDate, "Current date is null");

        return _date == null ? _currentDate : _date;
    }

    public static String createTitle(final String _title, final String _feedLink) {
        Assert.isValidString(_feedLink, "Feed link is invalid");

        return ((_title == null) || (_title.isEmpty())) ? _feedLink : _title;
    }

    public static String createDescription(final SyndEntry _entry) {
        Assert.notNull(_entry, "Synd entry is null");

        String title = _entry.getTitle();
        List contentsList = _entry.getContents();
        StringBuilder contents = new StringBuilder();

        if (contentsList != null && (!contentsList.isEmpty())) {

            for (Object current : contentsList) {
                contents.append(((SyndContent) current).getValue());
            }
        }

        String description = _entry.getDescription() == null ? "" : _entry.getDescription().getValue();

        String result = contents.length() != 0 ? contents.toString() : description;

        return result.isEmpty() ? title : result;
    }

    public static boolean urlValid(final String _url) {

        try {

            if (_url == null) {
                return false;
            }

            URL feedUrl = new URL(_url);
            String host = feedUrl.getHost();

            return !host.isEmpty();
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private RssFeedFetcherTools() {
        // empty
    }

}
