package constructor.objects.channel.core;

import app.cli.blitz.request.CriterionType;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.append.adapter.AppendProcessorAdapter;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import constructor.objects.processor.filter.adapter.FilterProcessorAdapter;
import constructor.objects.processor.get_group.adapter.GetGroupProcessorAdapter;
import constructor.objects.processor.xpath.adapter.XPathProcessorAdapter;
import debug.DebugConsole;
import util.Assert;
import variables.Variables;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 10.07.2010
 */
public final class ChannelAdapterTools {

    private static final String SEMICOLON = ";";
    private static final String OUTPUT_VARIABLE_NAME_PREFIX = "output";

    public static FragmentAnalyserConfiguration createFragmentAnalyserConfiguration(final CriterionType _criterionType, final String _expressions, final String _id, final DebugConsole _debugConsole) {
        Assert.notNull(_criterionType, "Criterion type is null");
        Assert.isValidString(_expressions, "Criterion expressions list is not valid");
        Assert.isValidString(_id, "Interpreter id is not valid");
        Assert.notNull(_debugConsole, "Debug console is not valid");

        FragmentAnalyserConfiguration result = new FragmentAnalyserConfiguration();

        result.setContentProcessor(createChainProcessorAdapter(_criterionType, parseExpressionsList(_expressions), _id, _debugConsole));

        return result;
    }

    public static List<String> parseExpressionsList(String _expressions) {
        Assert.isValidString(_expressions, "Criterion expressions list is not valid");

        List<String> expressions = new ArrayList<String>();

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

        if (_criterionType == CriterionType.FILTER) {
            adapter.addAdapter(new FilterProcessorAdapter());
        } else {
            int index = 0;

            for (String expression : _expressions) {
                String outputVariableName = createOutputVariableName(index);

                adapter.addAdapter(_criterionType == CriterionType.REGEXP ? createGetGroupProcessorAdapter(expression, outputVariableName) : createXPathProcessorAdapter(expression, outputVariableName));

                if (index > 0) {
                    adapter.addAdapter(createAppendProcessorAdapter(outputVariableName));
                }

                ++index;
            }
        }

        return adapter;
    }

    private static String createOutputVariableName(int index) {
        return index == 0 ? "" : OUTPUT_VARIABLE_NAME_PREFIX + String.valueOf(index);
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

    private static VariableProcessorAdapter createAppendProcessorAdapter(final String _variableName) {
        AppendProcessorAdapter result = new AppendProcessorAdapter();

        result.setAttributeFirst(_variableName);
        result.setAttributeSecond(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);

        return result;
    }

    private ChannelAdapterTools() {
        // empty
    }
}
