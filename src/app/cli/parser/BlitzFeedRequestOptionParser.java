package app.cli.parser;

import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.RequestSourceType;
import org.apache.commons.cli.CommandLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Парсер опции "обработать блиц запрос к фиду"
 *
 * @author Igor Usenko
 *         Date: 31.10.2009
 */
public class BlitzFeedRequestOptionParser extends AbstractBlitzRequestOptionParser {

    protected BlitzRequest createRequest(final CommandLine _commandLine) {
        List<String> urls = OptionParserUtils.getOptionsList(_commandLine, OptionNameTable.BLITZ_FEED_REQUEST_OPTION_SHORT_NAME);
        List<String> feeds = new ArrayList<String>();
        feeds.add(urls.get(0));

        return new BlitzRequest(RequestSourceType.RSS, feeds);
    }

}