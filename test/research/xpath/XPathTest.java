package research.xpath;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import junit.framework.TestCase;
import junit.framework.Assert;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import util.IOTools;

import javax.xml.xpath.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import constructor.objects.processor.xpath.XPathProcessorMode;


/**
 * @author Igor Usenko
 *         Date: 26.06.2009
 */
public class XPathTest extends TestCase {

    private static final String TEST_ROOT = "./work/researchTest/XPath/testSmoke/";
    private static final String TEST_SMOKE_01 = TEST_ROOT + "showthread.php.htm";

    public XPathTest(String s) {
        super(s);
    }

    public void testSmoke() {
        try {
            HtmlCleaner cleaner = new HtmlCleaner();

            //TagNode node = cleaner.clean(new File(TEST_SMOKE_01));
            TagNode node = cleaner.clean(new URL("http://www.business-magazine.ru/mech_new/experience/pub333379"));

            DomSerializer domSerializer = new DomSerializer(new CleanerProperties());
            Document document = domSerializer.createDOM(node);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            //XPathExpression expr = xpath.compile("/html/body/table[3]/tbody/tr/td[1]/div[last()-3]");
            XPathExpression expr = xpath.compile("//a[@href]");
            //XPathExpression expr = xpath.compile("/html/body");

            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);


            for (int i = 0; i < nodes.getLength(); i++) {
                Node current = nodes.item(i);
                //System.out.println(nodeContentToString(current));
                System.out.println(nodeTagToString(current));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<String> evaluateXPath(String _source, String _expression, XPathProcessorMode _mode) {
        List<String> result = new ArrayList<String>();

        try {
            NodeList nodes = getNodes(_source, _expression);

            for (int i = 0; i < nodes.getLength(); i++) {
                Node current = nodes.item(i);

                if (_mode == XPathProcessorMode.INNER) {
                    result.add(nodeContentToString(current));
                }

                if (_mode == XPathProcessorMode.INNER) {
                    result.add(nodeTagToString(current));
                }
            }
            
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result;
    }

    private NodeList getNodes(final String _source, final String _expression) throws XPathExpressionException, ParserConfigurationException, IOException {
        return (NodeList)  XPathFactory.newInstance().newXPath().compile(_expression).evaluate(
                new DomSerializer(new CleanerProperties()).createDOM(new HtmlCleaner().clean(_source)),
                XPathConstants.NODESET);
    }

    private String nodeContentToString(final Node _node) throws IOException {
        ByteArrayOutputStream outputStream = null;

        try {
            OutputFormat outputFormat = new OutputFormat();
            outputFormat.setOmitXMLDeclaration(true);
            outputFormat.setEncoding("UTF-8");
            outputFormat.setLineWidth(0);
            
            XMLSerializer serializer = new XMLSerializer(outputFormat);

            outputStream = new ByteArrayOutputStream();

            serializer.setOutputByteStream(outputStream);
            serializer.serialize(_node);

            outputStream.flush();

            String result = outputStream.toString("UTF-8");
            
            outputStream.close();

            return result;
        } finally {
            IOTools.close(outputStream);
        }
    }

    private String nodeTagToString(final Node _node) throws IOException {
        StringBuilder result = new StringBuilder("<");

        result.append(_node.getNodeName()).append(" ");

        NamedNodeMap attributes =_node.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node current = attributes.item(i);

            result.append(current.getNodeName()).append("=\"").append(current.getNodeValue()).append("\" ");
        }

        return result.append("/>").toString();
    }
    
    public void testAnotherSmoke() throws IOException {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean("<A href=sdfsfd>dfg</a><div><img src=\"angry.gif\" alt=\"Angry face\" width=\"32\" height=\"32\" />df<br/>g<pre>as\r\ndf\r\n</pre></div>sdhfalskdfkhsdf");
            System.out.print(node);
    }

    public void testWithJavaScript() throws IOException {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean(new URL("http://forum.ixbt.com/topic.cgi?id=47:1479-122"));
            System.out.print(node);
    }

    public void testJavaScriptFragment() throws IOException {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean("<b>BIGVLAD</b><br><br>581 это \"модель после обновлени€\", т.к. после того как ай–обот сделал узкую базу и отключил ма€ки у младших моделей. ћой оригинальный робот имел Charging Error 5, которую победить не удалось никак, включа€ несколько попыток зар€дки батарии внешним зар€дником. ')");
            System.out.print(node);
    }

}
