package research.heuristic;

import junit.framework.TestCase;
import org.htmlcleaner.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Igor Usenko
 *         Date: 31.01.2011
 */
public class HeuristicResearchTest extends TestCase {

    public HeuristicResearchTest(final String _s) {
        super(_s);
    }

    public void testSmoke() throws IOException, XPatherException {
        HtmlCleaner cleaner = new HtmlCleaner();

        TagNode node = cleaner.clean(new URL("http://www.drive.ru/mitsubishi/drive-test/2010/12/31/3816888/bolshaya_raznitsa.html"));
        TagNode body = (TagNode) node.evaluateXPath("//body")[0];

        //node.traverse(this);
        TagNode[] nodes = body.getAllElements(true);

        // имеет смысл работать со стандартным деревом см XPath процессор
        Object[] pars = node.evaluateXPath("//p/..");

        for (Object current : pars) {
            TagNode cnode = (TagNode) current;

            System.out.println(cnode.getText().length() + " : " + path(cnode));
        }
        /*
        for (TagNode currentNode : nodes) {
            System.out.println(path(currentNode) + " : " + currentNode.getText().length());
        }
        */
        /*
        List<Path> pathes = new ArrayList<Path>();

        for (TagNode currentNode : nodes) {
            pathes.add(PathTools.parse(currentNode));
        }

        Path maxPath = pathes.get(0);
        int maxWeigth = PathEvaluator.evaluate(maxPath);

        for (Path path : pathes) {
            System.out.println(PathTools.convertToXPath(path) + " : " + PathEvaluator.evaluate(path));
        }

        for (int j = 1; j < pathes.size(); ++j) {
            Path candidate = pathes.get(j);
            int candidateWeight = PathEvaluator.evaluate(candidate);

            if (candidateWeight > maxWeigth) {
                maxWeigth = candidateWeight;
                maxPath = candidate;
            }
        }

        System.out.println(PathTools.convertToXPath(maxPath));
        */
    }

    private String path(final TagNode _node) {
        List<String> elements = new ArrayList<String>();

        elements.add(getNodeId(_node));
        TagNode parent = _node.getParent();
        
        do {

            if (parent != null) {
                String nodeId = getNodeId(parent);

                elements.add(nodeId);

                parent = parent.getParent();
            }


        } while (parent != null);

        Collections.reverse(elements);

        StringBuilder result = new StringBuilder();

        for (String element : elements) {
            result.append(element).append("/");    
        }

        return result.toString();
    }

    private String getNodeId(final TagNode _parent) {
        String className = _parent.getAttributeByName("class");
        String id = _parent.getAttributeByName("id");

        String insert = "";

        if (id != null || className != null) {
            insert = "[";

            if (className != null) {
                insert += "@class='" + className + "'";
            }

            if (className != null && id != null) {
                insert += " and ";
            }

            if (id != null) {
                insert += "@id='" + id + "'";
            }

            insert += "]";
        }

        return _parent.getName() + insert;
    }
}
