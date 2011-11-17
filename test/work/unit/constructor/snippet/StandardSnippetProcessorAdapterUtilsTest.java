package work.unit.constructor.snippet;

import constructor.objects.snippet.adapter.StandardSnippetProcessorAdapterUtils;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public class StandardSnippetProcessorAdapterUtilsTest extends TestCase {

    public StandardSnippetProcessorAdapterUtilsTest(final String _s) {
        super(_s);
    }

    // �������� ����� � �����������

    public void testWithExtension() {
        String result = StandardSnippetProcessorAdapterUtils.getPropertiesFileName("C:/test/test/sample.xml");

        assertTrue(result.matches("C:.test.test.sample\\.timestamp"));
    }

    // �������� ����� ��� ����������

    public void testWithoutExtension() {
        String result = StandardSnippetProcessorAdapterUtils.getPropertiesFileName("C:/test/test/sample");

        assertTrue(result.matches("C:.test.test.sample\\.timestamp"));
    }
}
