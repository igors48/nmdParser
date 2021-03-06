package work.unit.javastylelocator;

import constructor.dom.locator.MaskUtils;
import junit.framework.TestCase;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 11.11.2009
 */
public class MaskUtilsTest extends TestCase {

    public MaskUtilsTest(final String _s) {
        super(_s);
    }

    // ���� �������� ����������

    public void testParseAccepted() {
        List<String> fixture = newArrayList();
        fixture.add("first");
        fixture.add("^second");

        List<String> result = MaskUtils.parseAccepted(fixture);

        assertEquals(1, result.size());
        assertEquals("first", result.get(0));
    }

    // ���� �������� �����������

    public void testParseExcepted() {
        List<String> fixture = newArrayList();
        fixture.add("first");
        fixture.add("^second");

        List<String> result = MaskUtils.parseExcepted(fixture);

        assertEquals(1, result.size());
        assertEquals("second", result.get(0));
    }
}
