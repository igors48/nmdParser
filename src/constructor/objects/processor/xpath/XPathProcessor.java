package constructor.objects.processor.xpath;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.Variables;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static constructor.objects.processor.xpath.XPathProcessorTools.deleteNodes;
import static constructor.objects.processor.xpath.XPathProcessorTools.evaluateXPath;

/**
 * ��������� ��������� ���������� XPath ��������
 *
 * @author Igor Usenko
 *         Date: 27.06.2009
 */
public class XPathProcessor extends AbstractVariableProcessor {

    public static final String XPATH_PROCESSOR_NAME = "xPath";
    public static final String EXPRESSION_PARAMETER_NAME = "expression";
    public static final String MODE_PARAMETER_NAME = "mode";

    private final String expression;
    private final XPathProcessorMode mode;

    public XPathProcessor(final String _in, final String _expression, final XPathProcessorMode _mode, final String _out) {
        super(_in, _out);

        Assert.isValidString(_expression, "XPath string is not valid.");
        this.expression = _expression;

        Assert.notNull(_mode, "XPath processor mode is null.");
        this.mode = _mode;
    }

    public void process(final Variables _variables) throws VariableProcessorException {

        Assert.notNull(_variables, "Variables is null.");

        try {
            String inputValue = _variables.get(this.input);

            List<String> nodes = this.mode == XPathProcessorMode.DELETE ?
                    Arrays.asList(deleteNodes(inputValue, this.expression)) :
                    evaluateXPath(inputValue, this.expression, this.mode);

            int index = 0;

            for (String node : nodes) {
                _variables.put(this.output, index++, node);
            }
        } catch (IOException e) {
            throw new VariableProcessorException(e);
        } catch (XPathExpressionException e) {
            throw new VariableProcessorException(e);
        } catch (ParserConfigurationException e) {
            throw new VariableProcessorException(e);
        }
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(XPATH_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(EXPRESSION_PARAMETER_NAME, this.expression);
        result.addParameter(MODE_PARAMETER_NAME, this.mode.toString());
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }

}
