package constructor.objects.processor.xpath;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.*;
import util.Assert;
import util.IOTools;
import util.TextTools;
import variables.Variables;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Процессор обработки упрощенных XPath запросов
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

            List<String> nodes = evaluateXPath(inputValue, this.expression, this.mode);

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

    public List<String> evaluateXPath(String _source, String _expression, XPathProcessorMode _mode) throws IOException, XPathExpressionException, ParserConfigurationException {
        List<String> result = new ArrayList<String>();

        NodeList nodes = getNodes(_source, _expression);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node current = nodes.item(i);

            if (_mode == XPathProcessorMode.INNER) {

                if (current instanceof Attr) {
                    result.add(attrValueToString((Attr) current));
                } else {
                    result.add(nodeContentToString(current));
                }
            }

            if (_mode == XPathProcessorMode.TAG) {
                result.add(nodeTagToString(current));
            }
        }

        return result;
    }

    private NodeList getNodes(final String _source, final String _expression) throws XPathExpressionException, ParserConfigurationException, IOException {
        TagNode cleaned = new HtmlCleaner().clean(_source);
        CleanerProperties cleanerProperties = new CleanerProperties();
        cleanerProperties.setNamespacesAware(false);
        
        Document serialized = new DomSerializer(cleanerProperties).createDOM(cleaned);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        XPathExpression xPathExpression = xpath.compile(_expression);

        return (NodeList) xPathExpression.evaluate(serialized, XPathConstants.NODESET);
    }

    private String attrValueToString(final Attr _current) {
        return _current.getValue();
    }

    private String nodeContentToString(final Node _node) throws IOException {
        ByteArrayOutputStream outputStream = null;

        try {
            OutputFormat outputFormat = new OutputFormat();
            outputFormat.setOmitXMLDeclaration(true);
            outputFormat.setEncoding("UTF-8");
            outputFormat.setLineWidth(0);
            outputFormat.setNonEscapingElements(new String[]{"&"});

            XMLSerializer serializer = new XMLSerializer(outputFormat);

            outputStream = new ByteArrayOutputStream();

            serializer.setOutputByteStream(outputStream);
            serializer.serialize(_node);

            outputStream.flush();

            String result = outputStream.toString("UTF-8");

            outputStream.close();

            return TextTools.removeEscapedAmpersands(result);
        } finally {
            IOTools.close(outputStream);
        }
    }

    private String nodeTagToString(final Node _node) throws IOException {
        StringBuilder result = new StringBuilder("<");

        result.append(_node.getNodeName()).append(" ");

        NamedNodeMap attributes = _node.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node current = attributes.item(i);

            result.append(current.getNodeName()).append("=\"").append(current.getNodeValue()).append("\" ");
        }

        return TextTools.removeEscapedAmpersands(result.append("/>").toString());
    }
}
