package work.unit.validator;

import junit.framework.TestCase;
import app.iui.validator.Rule;
import app.iui.validator.rules.IntegerInRange;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class IntegerInRangeTest extends TestCase {

    public IntegerInRangeTest(final String _s) {
        super(_s);
    }

    public void testNull() {
        Rule rule = new IntegerInRange(0, 1);

        assertTrue(rule.valid(null));
    }

    public void testEmpty() {
        Rule rule = new IntegerInRange(0, 1);

        assertTrue(rule.valid(""));
    }

    public void testInRange() {
        Rule rule = new IntegerInRange(0, 2);

        assertTrue(rule.valid("1"));
    }

    public void testMin() {
        Rule rule = new IntegerInRange(0, 2);

        assertTrue(rule.valid("0"));
    }

    public void testMax() {
        Rule rule = new IntegerInRange(0, 2);

        assertTrue(rule.valid("0"));
    }

    public void testLesserThanMin() {
        Rule rule = new IntegerInRange(0, 2);

        assertFalse(rule.valid("-1"));
    }

    public void testGreaterThanMax() {
        Rule rule = new IntegerInRange(0, 2);

        assertFalse(rule.valid("48"));
    }
}
