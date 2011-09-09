package html.parser;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import util.Assert;

import java.io.IOException;

/**
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public final class SimpleHtmlConverterUtil {

    private static final String[] ignoredTags = new String[]{
            "<!DOCTYPE.*?>",
            "<html.*?>", "</html>",
            "<body.*?>", "</body>",
            "<!--.*?-->",
            "<font.*?>", "</font>",
            "<small.*?>", "</small>",
            "<bdo.*?>", "</bdo>",
            "<u>", "</u>",
            "<pre>", "</pre>",
            "<var>", "</var>",
            "<dfn>", "</dfn>",
            "<xmp>", "</xmp>",
            "<acronym.*?>", "</acronym>",
            "<abbr.*?>", "</abbr>",
            "<address.*?>", "</address>",
            // "<blockquote>", "</blockquote>",
            "<center.*?>", "</center>",
            //"<q>", "</q>",
            "<cite>", "</cite>",
            "<ins>", "</ins>",
            "<del>", "</del>",
            "<s>", "</s>",
            "<strike>", "</strike>",
            //"<a.*?>", "</a>",
            "<link.*?>",
            "<frame.*?>",
            "<frameset.*?>", "/frameset",
            "<noframes>", "</noframes>",
            "<iframe.*?>", "</iframe>",
            "<form.*?/form>",
            "<input.*?>",
            "<textarea.*?</textarea>",
            "<button.*?</button>",
            "<select>.*?</select>",
            "<optgroup.*?>", "</optgroup>",
            "<option.*?</option>",
            "<label.*?</label>",
            "<fieldset.*?</fieldset>",
            "<legend.*?</legend>",
            "<isindex.*?>",
            "<ul.*?>", "</ul>",
            "<ol.*?>", "</ol>",
            //"<li>", "</li>",
            "<dir>", "</dir>",
            "<sup.*?>", "</sup>",
            //"<dl>", "</dl>",
            //"<dt>, </dt>",
            //"<dd>, </dd>",
            "<menu>", "</menu>",
            "<map.*?>", "</map>",
            "<noindex>", "</noindex>",
            "<area.*?>",
            "<table.*?>", "</table>",
            "<caption>", "</caption>",
            "<th.*?>", "</th>",
            "<tr.*?>", "</tr>",
            "<td.*?>", "</td>",
            "<thead.*?>", "</thead>",
            "<tbody.*?>", "</tbody>",
            "<tfoot.*?>", "</tfoot>",
            "<col.*?>", "</col>",
            "<colgroup.*?>", "</colgroup>",
            "<style.*?>", "</style>",
            "<span.*?>", "</span>",
            "<title.*?>", "</title>",
            "<head.*?>", "</head>",
            "<meta.*?>",
            "<base.*?>",
            "<basefont.*?>",
            // "<script.*?/script>", "</script>",
            "<script.*?>", "</script>",
            "<noscript.*?>", "</noscript>",
            "<applet.*?>", "</applet>",
            "<object.*?>", "</object>",
            "<param.*?>",
            "<wbr\\s*/>",
            "<nobr>", "</nobr>"};

    public static String cleanupHtml(final String _data) {
        Assert.isValidString(_data, "Data is not valid");

        String result = _data;

        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean(_data);
            Object[] nodes = node.evaluateXPath("/body");

            if (nodes.length > 0) {
                result = cleaner.getInnerHtml((TagNode) nodes[0]);
            }
        } catch (XPatherException e) {
            // empty
        }

        return result;
    }

    public static String clearEmptyLines(final String _data) {
        Assert.isValidString(_data, "Data is not valid");

        String[] lines = _data.split("##@#@##");
        StringBuffer clear = new StringBuffer();

        for (String line : lines) {
            line = line.trim();

            if (line.length() != 0) {
                clear.append(line);
            }
        }

        return clear.toString();
    }

    public static String processIgnoredTags(final String _data) {
        Assert.isValidString(_data, "Data is not valid");

        String current = _data;

        for (String tag : ignoredTags) {
            current = processIgnoredTag(current, tag);

            if (current.length() == 0) {
                break;
            }
        }
        return current;
    }

    public static String removeUnneededSpaces(final String _data) {
        Assert.notNull(_data, "Data is null");

        String result = _data.trim();
        String parts[] = result.split(" ");

        if (parts.length > 0) {
            StringBuffer buffer = new StringBuffer();

            for (String part : parts) {

                if (part.length() > 0) {
                    buffer.append(part).append(' ');
                }
            }

            result = buffer.toString().trim();
        }

        return result;
    }

    private static String processIgnoredTag(final String _data, final String _tag) {
        String result = _data.replaceAll(_tag, "");

        return result.replaceAll(_tag.toUpperCase(), "");
    }

    private SimpleHtmlConverterUtil() {
        //empty
    }
}
