package constructor.objects.dateparser.configuration;

import constructor.dom.Blank;
import constructor.dom.UsedObject;
import constructor.objects.dateparser.core.ParsingStrategy;
import util.Assert;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static util.CollectionUtils.newArrayList;
import static util.CollectionUtils.newHashMap;

/**
 * ������������ ������� ����
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class DateParserConfiguration implements Blank {

    private final Map<Pattern, ParsingStrategy> parsers;

    private String id;

    private String pattern;
    private String month;

    public DateParserConfiguration() {
        this.parsers = newHashMap();
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Id is not valid.");
        this.id = _id;
    }

    public String getId() {
        return this.id;
    }

    public List<UsedObject> getUsedObjects() {
        return newArrayList();
    }

    public void addStrategy(final String _pattern, final ParsingStrategy _strategy) {
        Assert.isValidString(_pattern, "Pattern is not valid.");
        Assert.notNull(_strategy, "Strategy is null");

        this.parsers.put(Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE), _strategy);
    }

    public Map<Pattern, ParsingStrategy> getParsers() {
        return this.parsers;
    }

    public void setPattern(final String _value) {
        Assert.isValidString(_value, "Value is null.");

        this.pattern = _value;
    }

    public void setMonth(final String _value) {
        Assert.isValidString(_value, "Value is null.");

        this.month = _value;
    }

    public String getMonths() {
        return this.month;
    }

    public String getPattern() {
        return this.pattern;
    }
}
