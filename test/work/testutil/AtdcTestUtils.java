package work.testutil;

import dated.item.atdc.AtdcItem;
import dated.item.atdc.Author;
import dated.item.atdc.HtmlContent;
import http.Data;
import timeservice.TimeService;
import util.Assert;

import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 27.12.2008
 */
public final class AtdcTestUtils {
    private static final String NICK = "nick";
    private static final String INFO = "info";
    private static final String AVATAR = "avatar";
    private static final String URL = "url<div></div>";
    private static final String DATA = "data";
    private static final String TITLE = "title";

    public static AtdcItem getAtdcNoTitleItem(TimeService _timeService, int _number) {
        Assert.notNull(_timeService);

        Author author = new Author(NICK + _number, INFO + _number, AVATAR + _number);
        HtmlContent htmlContent = new HtmlContent(URL + _number, DATA + _number, URL + _number);

        return new AtdcItem(author, new Date(_timeService.getCurrentTime()), htmlContent);
    }

    public static AtdcItem getAtdcFullItem(TimeService _timeService, int _number) {
        Assert.notNull(_timeService);

        Author author = new Author(NICK + _number, INFO + _number, AVATAR + _number);
        HtmlContent htmlContent = new HtmlContent(URL + _number, DATA + _number, URL + _number);

        return new AtdcItem(author, TITLE + _number, new Date(_timeService.getCurrentTime()), htmlContent);
    }

    public static AtdcItem getAtdcFullItem(int _number) {
        Author author = new Author(NICK + _number, INFO + _number, AVATAR + _number);
        HtmlContent htmlContent = new HtmlContent(URL + _number, DATA + _number, URL + _number);

        return new AtdcItem(author, TITLE + _number, new Date(System.currentTimeMillis() + _number * 1000), htmlContent);
    }

    public static String getContentString(AtdcItem _item) throws Data.DataException {
        return _item.getContent().getContent().trim();
    }

    public static boolean atdcItemEquals(AtdcItem _first, AtdcItem _second) {
        Assert.notNull(_first);
        Assert.notNull(_second);

        boolean result = true;

        if (!_first.getAuthorNick().equalsIgnoreCase(_second.getAuthorNick())) {
            result = false;
        }

        if (!_first.getAuthorInfo().equalsIgnoreCase(_second.getAuthorInfo())) {
            result = false;
        }

        if (!_first.getAuthorAvatar().equalsIgnoreCase(_second.getAuthorAvatar())) {
            result = false;
        }

        if (!_first.getTitle().equalsIgnoreCase(_second.getTitle())) {
            result = false;
        }

        if (!_first.getDate().equals(_second.getDate())) {
            result = false;
        }

        if (!_first.getContent().getContent().equalsIgnoreCase(_second.getContent().getContent())) {
            result = false;
        }

        if (!_first.getContent().getUrl().equalsIgnoreCase(_second.getContent().getUrl())) {
            result = false;
        }

        return result;
    }

    private AtdcTestUtils() {
        // empty
    }
}
