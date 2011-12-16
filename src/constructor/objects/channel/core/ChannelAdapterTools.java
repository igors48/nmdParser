package constructor.objects.channel.core;

import app.cli.blitz.request.CriterionType;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.append.adapter.AppendProcessorAdapter;
import constructor.objects.processor.chain.adapter.FirstOneProcessorAdapter;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import constructor.objects.processor.filter.adapter.FilterProcessorAdapter;
import constructor.objects.processor.get_group.adapter.GetGroupProcessorAdapter;
import constructor.objects.processor.xpath.XPathProcessorMode;
import constructor.objects.processor.xpath.adapter.XPathProcessorAdapter;
import debug.DebugConsole;
import util.Assert;
import variables.Variables;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 10.07.2010
 */
public final class ChannelAdapterTools {

    private static final String SEMICOLON = ";";
    private static final String SCRIPT_XPATH = "//script";

    public static FragmentAnalyserConfiguration createFragmentAnalyserConfiguration(final CriterionType _criterionType, final String _expressions, final String _id, final DebugConsole _debugConsole) {
        Assert.notNull(_criterionType, "Criterion type is null");
        Assert.isValidString(_expressions, "Criterion expressions list is not valid");
        Assert.isValidString(_id, "Interpreter id is not valid");
        Assert.notNull(_debugConsole, "Debug console is not valid");

        FragmentAnalyserConfiguration result = new FragmentAnalyserConfiguration();

        result.setContentProcessor(createChainProcessorAdapter(_criterionType, parseExpressionsList(_expressions), _id, _debugConsole));

        return result;
    }

    public static FragmentAnalyserConfiguration createContentFilterConfiguration(final String _id, final DebugConsole _debugConsole) {
        Assert.isValidString(_id, "Interpreter id is not valid");
        Assert.notNull(_debugConsole, "Debug console is not valid");

        FragmentAnalyserConfiguration result = new FragmentAnalyserConfiguration();

        StandardChainProcessorAdapter adapter = new StandardChainProcessorAdapter(_debugConsole);

        adapter.setId(_id);
        adapter.addAdapter(new FilterProcessorAdapter());

        result.setContentProcessor(adapter);

        return result;
    }

    public static List<String> parseExpressionsList(String _expressions) {
        Assert.isValidString(_expressions, "Criterion expressions list is not valid");

        List<String> expressions = newArrayList();

        String[] parts = _expressions.split(SEMICOLON);

        for (String part : parts) {

            if (!part.isEmpty()) {
                expressions.add(part.trim());
            }
        }

        return expressions;
    }

    public static StandardChainProcessorAdapter createChainProcessorAdapter(CriterionType _criterionType, List<String> _expressions, String _id, DebugConsole _debugConsole) {
        StandardChainProcessorAdapter adapter = new StandardChainProcessorAdapter(_debugConsole);

        adapter.setId(_id);

        adapter.addAdapter(createScriptsRemover());

        FirstOneProcessorAdapter firstOneProcessorAdapter = new FirstOneProcessorAdapter(_debugConsole);
        
        for (String expression : _expressions) {
            firstOneProcessorAdapter.addAdapter(_criterionType == CriterionType.REGEXP ?
                    createGetGroupProcessorAdapter(expression, Variables.DEFAULT_OUTPUT_VARIABLE_NAME) :
                    createXPathProcessorAdapter(expression, Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        }

        adapter.addAdapter(firstOneProcessorAdapter);
        
        return adapter;
    }

    private static XPathProcessorAdapter createScriptsRemover() {
        XPathProcessorAdapter scriptsRemover = new XPathProcessorAdapter();

        scriptsRemover.setElementExpression(SCRIPT_XPATH);
        scriptsRemover.setMode(XPathProcessorMode.DELETE);
        scriptsRemover.setAttributeOut(Variables.DEFAULT_INPUT_VARIABLE_NAME);

        return scriptsRemover;
    }

    private static VariableProcessorAdapter createXPathProcessorAdapter(final String _expression, final String _outputVariableName) {
        XPathProcessorAdapter result = new XPathProcessorAdapter();

        result.setElementExpression(_expression);

        if (!_outputVariableName.isEmpty()) {
            result.setAttributeOut(_outputVariableName);
        }

        return result;
    }

    private static VariableProcessorAdapter createGetGroupProcessorAdapter(final String _expression, final String _outputVariableName) {
        GetGroupProcessorAdapter result = new GetGroupProcessorAdapter();

        result.setElementPattern(_expression);

        if (!_outputVariableName.isEmpty()) {
            result.setAttributeOut(_outputVariableName);
        }

        return result;
    }

    private ChannelAdapterTools() {
        // empty
    }

}
