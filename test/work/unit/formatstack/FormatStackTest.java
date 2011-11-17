package work.unit.formatstack;

import flowtext.FormatStack;
import html.parser.tag.format.TextFormat;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 25.01.2009
 */
public class FormatStackTest extends TestCase {

    public FormatStackTest(String s) {
        super(s);
    }

    public void testEmpty() {
        FormatStack stack = new FormatStack();

        assertEquals(TextFormat.SIMPLE, stack.pop());
        assertEquals(TextFormat.SIMPLE, stack.pop());
        assertEquals(TextFormat.SIMPLE, stack.pop());
    }

    public void testFirstFormatRemain() {
        FormatStack stack = new FormatStack();

        stack.push(TextFormat.EMPHASIS);
        stack.push(TextFormat.SIMPLE);

        assertEquals(TextFormat.SIMPLE, stack.pop());
        assertEquals(TextFormat.EMPHASIS, stack.pop());
        assertEquals(TextFormat.SIMPLE, stack.pop());
    }
}
