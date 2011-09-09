package constructor.objects.interpreter.core;

import dated.item.modification.Modification;
import util.Assert;
import util.html.HtmlTagTools;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Веб страница
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public class Page {

    // Популярная механика этот тэг решила не закрывать
    private static final Pattern BASE_PATTERN = Pattern.compile("(<base\\s+?href\\s*?=\\s*?.+?\\s*?>)", Pattern.CASE_INSENSITIVE);

    private final Modification modification;
    private final String url;
    private final String image;
    private final String base;

    public Page(final Modification _modification, final String _url, final String _image) {
        Assert.notNull(_modification);
        Assert.notNull(_url);
        Assert.notNull(_image);

        this.modification = _modification;
        this.url = _url;
        this.image = _image;

        this.base = searchBase();
    }

    public String getImage() {
        return this.image;
    }

    public String getUrl() {
        return this.url;
    }

    public Modification getModification() {
        return this.modification;
    }

    public String getBase() {
        return this.base;
    }

    private String searchBase() {
        String result = this.url;

        Matcher matcher = BASE_PATTERN.matcher(this.image);

        if (matcher.find()) {
            String tagImage = matcher.group(1);
            Map<String, String> attributes = HtmlTagTools.parseAttributes(tagImage);

            String lower = attributes.get("href");
            String upper = attributes.get("HREF");

            if (lower != null) {
                result = lower;
            }

            if (upper != null) {
                result = upper;
            }
        }

        return result;
    }
}
