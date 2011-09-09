package constructor.objects.channel.core.analyser;

import util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Usenko
 *         Date: 05.12.2008
 */
public class GetGroup {

    //todo пахнет утилитными статиками
    private final Pattern pattern;
    private final int group;

    public GetGroup(String _pattern, int _group) {
        Assert.isValidString(_pattern);

        this.pattern = Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE);
        this.group = _group;
    }

    public String process(String _string) {
        String result = "";

        Matcher matcher = this.pattern.matcher(_string);

        if (matcher.find()) {
            result = matcher.group(this.group);
        }

        return result;
    }

}