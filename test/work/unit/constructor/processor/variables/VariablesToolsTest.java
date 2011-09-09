package work.unit.constructor.processor.variables;

import junit.framework.TestCase;
import variables.Variables;
import variables.Variable;
import variables.VariablesTools;

/**
 * @author Igor Usenko
 *         Date: 02.01.2011
 */
public class VariablesToolsTest extends TestCase {

    public VariablesToolsTest(final String _s) {
        super(_s);
    }

    public void testSingleEquals() {
        Variables variables = new Variables();

        variables.put("first", "value");
        variables.put("second", "value");

        Variable first = variables.getVariable("first");
        Variable second = variables.getVariable("second");

        assertTrue(VariablesTools.equals(first, second));
    }

    public void testSingleNotEquals() {
        Variables variables = new Variables();

        variables.put("first", "value");
        variables.put("second", "value1");

        Variable first = variables.getVariable("first");
        Variable second = variables.getVariable("second");

        assertFalse(VariablesTools.equals(first, second));
    }

    public void testMultipleEquals() {
        Variables variables = new Variables();

        variables.put("first", 0, "value0");
        variables.put("first", 1, "value1");
        variables.put("second", 0, "value0");
        variables.put("second", 1, "value1");

        Variable first = variables.getVariable("first");
        Variable second = variables.getVariable("second");

        assertTrue(VariablesTools.equals(first, second));
    }

    public void testMultipleNotEquals() {
        Variables variables = new Variables();

        variables.put("first", 0, "value0");
        variables.put("first", 1, "value1");
        variables.put("second", 0, "value0");
        variables.put("second", 1, "value2");

        Variable first = variables.getVariable("first");
        Variable second = variables.getVariable("second");

        assertFalse(VariablesTools.equals(first, second));
    }

    public void testDifferentSize() {
        Variables variables = new Variables();

        variables.put("first", 0, "value0");
        variables.put("first", 1, "value1");
        variables.put("second", 0, "value0");

        Variable first = variables.getVariable("first");
        Variable second = variables.getVariable("second");

        assertFalse(VariablesTools.equals(first, second));
    }
}
