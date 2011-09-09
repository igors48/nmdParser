package work.unit.channeladaptertools;

import app.cli.blitz.request.CriterionType;
import constructor.objects.ConfigurationException;
import constructor.objects.channel.core.ChannelAdapterTools;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import constructor.objects.processor.xpath.XPathProcessor;
import constructor.objects.processor.get_group.GetGroupProcessor;
import constructor.objects.processor.append.AppendProcessor;
import debug.console.NullDebugConsole;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.07.2011
 */
public class ChannelAdapterToolsCreateProcessorTest extends TestCase {

    public ChannelAdapterToolsCreateProcessorTest(final String _s) {
        super(_s);
    }

    public void testCreateOneExpressionXPath() throws ConfigurationException {
        List<String> expressions = new ArrayList<String>();
        expressions.add("e1");

        StandardChainProcessorAdapter result = ChannelAdapterTools.createChainProcessorAdapter(CriterionType.XPATH, expressions, "id", new NullDebugConsole());

        assertEquals("id", result.getId());

        assertEquals(1, result.getProcessors().size());
        assertTrue(result.getProcessors().get(0) instanceof XPathProcessor);
    }

    public void testCreateOneExpressionRegExp() throws ConfigurationException {
        List<String> expressions = new ArrayList<String>();
        expressions.add("e1");

        StandardChainProcessorAdapter result = ChannelAdapterTools.createChainProcessorAdapter(CriterionType.REGEXP, expressions, "id", new NullDebugConsole());

        assertEquals("id", result.getId());

        assertEquals(1, result.getProcessors().size());
        assertTrue(result.getProcessors().get(0) instanceof GetGroupProcessor);
    }

    public void testCreateTwoExpressionsXPath() throws ConfigurationException {
        List<String> expressions = new ArrayList<String>();
        expressions.add("e1");
        expressions.add("e2");

        StandardChainProcessorAdapter result = ChannelAdapterTools.createChainProcessorAdapter(CriterionType.XPATH, expressions, "id", new NullDebugConsole());

        assertEquals("id", result.getId());

        assertEquals(3, result.getProcessors().size());
        assertTrue(result.getProcessors().get(0) instanceof XPathProcessor);
        assertTrue(result.getProcessors().get(1) instanceof XPathProcessor);
        assertTrue(result.getProcessors().get(2) instanceof AppendProcessor);
    }

}
