package constructor.objects.processor.xpath;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.*;
import util.Assert;
import util.IOTools;
import util.TextTools;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 13.12.2011
 */
public final class XPathProcessorTools {

    public static List<String> evaluateXPath(final String _source, final String _expression, final XPathProcessorMode _mode) throws IOException, XPathExpressionException, ParserConfigurationException {
        Assert.isValidString(_source, "Source is not valid");
        Assert.isValidString(_expression, "Expression is not valid");
        Assert.notNull(_mode, "Mode is null");
        
        List<String> result = newArrayList();

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

    public static String deleteNodes(final String _source, final String _expression) throws XPathExpressionException, ParserConfigurationException, IOException {
        Assert.isValidString(_source, "Source is not valid");
        Assert.isValidString(_expression, "Expression is not valid");

        Document serialized = createDom(_source);

        NodeList nodes = getNodes(serialized, _expression);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node current = nodes.item(i);

            Node parent = current.getParentNode();
            parent.removeChild(current);
        }

        return nodeContentToString(serialized);
    }

    private static NodeList getNodes(final String _source, final String _expression) throws XPathExpressionException, ParserConfigurationException, IOException {
        return getNodes(createDom(_source), _expression);
    }

    private static String attrValueToString(final Attr _current) {
        return _current.getValue();
    }

    private static String nodeContentToString(final Node _node) throws IOException {
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

    private static String nodeTagToString(final Node _node) throws IOException {
        StringBuilder result = new StringBuilder("<");

        result.append(_node.getNodeName()).append(" ");

        NamedNodeMap attributes = _node.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node current = attributes.item(i);

            result.append(current.getNodeName()).append("=\"").append(current.getNodeValue()).append("\" ");
        }

        return TextTools.removeEscapedAmpersands(result.append("/>").toString());
    }

    private static NodeList getNodes(final Document _serialized, final String _expression) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        XPathExpression xPathExpression = xpath.compile(_expression);

        return (NodeList) xPathExpression.evaluate(_serialized, XPathConstants.NODESET);
    }

    private static Document createDom(final String _source) throws ParserConfigurationException {
        CleanerProperties cleanerProperties = new CleanerProperties();
        cleanerProperties.setNamespacesAware(false);

        HtmlCleaner cleaner = new HtmlCleaner(cleanerProperties);
        TagNode cleaned = cleaner.clean(_source);

        DomSerializer domSerializer = new DomSerializer(cleanerProperties);

        return domSerializer.createDOM(cleaned);
    }

    private XPathProcessorTools() {
        // empty
    }

}
