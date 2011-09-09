package work.unit.constructor.snippet;

import junit.framework.TestCase;
import constructor.objects.snippet.adapter.StandardSnippetProcessorAdapterUtils;

/**
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public class StandardSnippetProcessorAdapterUtilsTest extends TestCase {

    public StandardSnippetProcessorAdapterUtilsTest(final String _s) {
        super(_s);
    }

    // проверка имени с расширением
    public void testWithExtension(){
        String result = StandardSnippetProcessorAdapterUtils.getPropertiesFileName("C:/test/test/sample.xml");

        assertTrue(result.matches("C:.test.test.sample\\.timestamp"));
    }

    // проверка имени без расширения
    public void testWithoutExtension(){
        String result = StandardSnippetProcessorAdapterUtils.getPropertiesFileName("C:/test/test/sample");

        assertTrue(result.matches("C:.test.test.sample\\.timestamp"));
    }
}
