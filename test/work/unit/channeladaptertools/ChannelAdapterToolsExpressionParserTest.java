package work.unit.channeladaptertools;

import constructor.objects.channel.core.ChannelAdapterTools;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.07.2011
 */
public class ChannelAdapterToolsExpressionParserTest extends TestCase {

    public ChannelAdapterToolsExpressionParserTest(final String _s) {
        super(_s);
    }

    public void testParseExpressionsList() {
        String fixture = "a;b";

        List<String> result = ChannelAdapterTools.parseExpressionsList(fixture);

        assertEquals(2, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
    }

    public void testParseUncompletedExpressionsList() {
        String fixture = "a;";

        List<String> result = ChannelAdapterTools.parseExpressionsList(fixture);

        assertEquals(1, result.size());
        assertEquals("a", result.get(0));
    }

    public void testParseEmptyExpressionsList() {
        String fixture = ";";

        List<String> result = ChannelAdapterTools.parseExpressionsList(fixture);

        assertEquals(0, result.size());
    }
}
