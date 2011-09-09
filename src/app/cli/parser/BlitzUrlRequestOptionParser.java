package app.cli.parser;

import app.cli.blitz.BlitzRequestUtils;
import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.RequestSourceType;
import static app.cli.parser.OptionNameTable.BLITZ_PAGE_REQUEST_OPTION_SHORT_NAME;
import org.apache.commons.cli.CommandLine;

import java.util.List;

/**
 * Парсер опции "обработать блиц запрос к странице"
 *
 * @author Igor Usenko
 *         Date: 31.10.2009
 */
public class BlitzUrlRequestOptionParser extends AbstractBlitzRequestOptionParser {

    protected BlitzRequest createRequest(final CommandLine _commandLine) {
        List<String> urls = OptionParserUtils.getOptionsList(_commandLine, BLITZ_PAGE_REQUEST_OPTION_SHORT_NAME);

        if (_commandLine.hasOption(OptionNameTable.BASE_URL_OPTION_SHORT_NAME)) {
            String base = _commandLine.getOptionValue(OptionNameTable.BASE_URL_OPTION_SHORT_NAME);
            urls = BlitzRequestUtils.appendBase(base, urls);
        }

        return new BlitzRequest(RequestSourceType.URLS, urls);
    }

}
