package work.unit.channeladaptertools;

import app.cli.blitz.request.CriterionType;
import constructor.objects.ConfigurationException;
import constructor.objects.channel.core.ChannelAdapterTools;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.FirstOneProcessor;
import constructor.objects.processor.chain.adapter.FirstOneProcessorAdapter;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import constructor.objects.processor.get_group.GetGroupProcessor;
import constructor.objects.processor.xpath.XPathProcessor;
import debug.console.NullDebugConsole;
import junit.framework.TestCase;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 28.07.2011
 */
public class ChannelAdapterToolsCreateProcessorTest extends TestCase {

    public ChannelAdapterToolsCreateProcessorTest(final String _s) {
        super(_s);
    }

    public void testCreateOneExpressionXPath() throws ConfigurationException {
        List<String> expressions = newArrayList();
        expressions.add("e1");

        StandardChainProcessorAdapter result = ChannelAdapterTools.createChainProcessorAdapter(CriterionType.XPATH, expressions, "id", new NullDebugConsole());

        assertEquals("id", result.getId());

        assertHighLevelStructureValid(result);
        assertContentExtractionPartValid(result, 1, true);
    }

    public void testCreateOneExpressionRegExp() throws ConfigurationException {
        List<String> expressions = newArrayList();
        expressions.add("e1");

        StandardChainProcessorAdapter result = ChannelAdapterTools.createChainProcessorAdapter(CriterionType.REGEXP, expressions, "id", new NullDebugConsole());

        assertEquals("id", result.getId());

        assertHighLevelStructureValid(result);
        assertContentExtractionPartValid(result, 1, false);
    }

    public void testCreateTwoExpressionsXPath() throws ConfigurationException {
        List<String> expressions = newArrayList();
        expressions.add("e1");
        expressions.add("e2");

        StandardChainProcessorAdapter result = ChannelAdapterTools.createChainProcessorAdapter(CriterionType.XPATH, expressions, "id", new NullDebugConsole());

        assertEquals("id", result.getId());

        assertHighLevelStructureValid(result);
        assertContentExtractionPartValid(result, 2, true);
    }

    private void assertHighLevelStructureValid(final StandardChainProcessorAdapter _result) throws ConfigurationException {
        assertEquals(2, _result.getProcessors().size());

        assertTrue(_result.getProcessors().get(0) instanceof XPathProcessor);
        assertTrue(_result.getProcessors().get(1) instanceof FirstOneProcessor);
    }

    private void assertContentExtractionPartValid(final StandardChainProcessorAdapter _result, final int _inlineProcessorsCount, final boolean _xPath) throws ConfigurationException {
        FirstOneProcessor firstOneProcessor = (FirstOneProcessor) _result.getProcessors().get(1);
        FirstOneProcessorAdapter firstOneProcessorAdapter = firstOneProcessor.getAdapter();

        List<VariableProcessor> processors = firstOneProcessorAdapter.getProcessors();

        assertEquals(_inlineProcessorsCount, processors.size());

        for (VariableProcessor current : processors) {

            if (_xPath) {
                assertTrue(current instanceof XPathProcessor);
            } else {
                assertTrue(current instanceof GetGroupProcessor);
            }
        }
    }

}
