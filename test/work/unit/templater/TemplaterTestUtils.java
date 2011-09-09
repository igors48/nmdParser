package work.unit.templater;

import app.templater.Template;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public final class TemplaterTestUtils {

    public static void assertBriefTemplatesValid(final List<Template> _result) {
        TestCase.assertNotNull(_result);
        TestCase.assertEquals(5, _result.size());
    }

    public static void assertFullTemplatesValid(final List<Template> _result) {
        TestCase.assertNotNull(_result);
        TestCase.assertEquals(3, _result.size());
    }

    private TemplaterTestUtils() {
        // empty
    }
}
