package constructor.objects.dateparser.adapter;

import constructor.objects.dateparser.configuration.DateParserConfiguration;
import constructor.objects.dateparser.core.DateParser;
import constructor.objects.dateparser.core.DateParserAdapter;
import constructor.objects.dateparser.core.StandardStrategy;
import util.Assert;

import java.text.DateFormatSymbols;
import java.util.regex.Pattern;

/**
 * Стандратный адаптер парсера даты
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class StandardDateParserAdapter implements DateParserAdapter {

    private final DateParserConfiguration configuration;
    private static final String STANDARD_STRATEGY_PATTERN = ".*";

    public StandardDateParserAdapter(final DateParserConfiguration _configuration) {
        Assert.notNull(_configuration, "Configuration is null.");

        this.configuration = _configuration;
    }

    public DateParser getDateParser() {
        StandardStrategy standard = createStandardStrategy();

        if (standard != null) {
            this.configuration.getParsers().put(Pattern.compile(STANDARD_STRATEGY_PATTERN, Pattern.CASE_INSENSITIVE), standard);
        }

        return new DateParser(this.configuration.getParsers());
    }

    private StandardStrategy createStandardStrategy() {
        StandardStrategy result = null;

        if (this.configuration.getPattern() != null && this.configuration.getMonths() != null) {
            DateFormatSymbols symbols = new DateFormatSymbols();
            String[] monthes = parseMonths(this.configuration.getMonths());
            symbols.setMonths(monthes);

            result = new StandardStrategy(this.configuration.getPattern(), symbols);
        }

        return result;
    }

    private String[] parseMonths(final String _months) {
        return _months.split(" ");
    }
}
