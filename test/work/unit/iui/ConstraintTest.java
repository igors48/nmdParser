package work.unit.iui;

import junit.framework.TestCase;
import app.iui.flow.Constraint;

/**
 * @author Igor Usenko
 *         Date: 07.12.2010
 */
public class ConstraintTest extends TestCase {

    public ConstraintTest(final String _s) {
        super(_s);
    }

    public void testWrap() {
        Constraint constraint = createConstraint();

        constraint.wrap();

        assertEquals("wrap", constraint.toString());
    }

    public void testConcat() {
        Constraint constraint = createConstraint();

        constraint.wrap();
        constraint.largeButton();

        assertEquals("wrap, w 200!", constraint.toString());
    }

    private Constraint createConstraint() {
        return new Constraint(500);
    }
}
