package work.unit.constructor.simpler;

import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.simpler.configuration.SimplerConfigurationTools;
import junit.framework.TestCase;
import work.testutil.SimplerConfigurationTestUtils;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 14.09.2010
 */
public class SimplerConfigurationToolsTest extends TestCase {

    public SimplerConfigurationToolsTest(final String _s) {
        super(_s);
    }

    public void testSmoke() {
        SimplerConfiguration fixture = SimplerConfigurationTestUtils.createSimplerConfiguration("id", "yes");

        List<String> result = SimplerConfigurationTools.render(fixture);

        assertEquals(3, result.size());
        assertEquals("<simpler feedUrl=\"feedUrl\" coverUrl=\"coverUrl\" storeDays=\"7\" branch=\"branch\" outName=\"outName\" fromNewToOld=\"yes\">", result.get(0));
        assertEquals("<xPath>xPath</xPath>", result.get(1));
        assertEquals("</simpler>", result.get(2));
    }

    // ���� ���������� ������ fromNewToOld="no"

    public void testFromNewToOldNo() {
        SimplerConfiguration fixture = SimplerConfigurationTestUtils.createSimplerConfiguration("id", "no");

        List<String> result = SimplerConfigurationTools.render(fixture);

        assertEquals(3, result.size());
        assertEquals("<simpler feedUrl=\"feedUrl\" coverUrl=\"coverUrl\" storeDays=\"7\" branch=\"branch\" outName=\"outName\" fromNewToOld=\"no\">", result.get(0));
        assertEquals("<xPath>xPath</xPath>", result.get(1));
        assertEquals("</simpler>", result.get(2));
    }

    // ���� ���������� ���������� ������ fromNewToOld

    public void testFromNewToOldDefault() {
        SimplerConfiguration fixture = SimplerConfigurationTestUtils.createSimplerConfiguration("id", "");

        List<String> result = SimplerConfigurationTools.render(fixture);

        assertEquals(3, result.size());
        assertEquals("<simpler feedUrl=\"feedUrl\" coverUrl=\"coverUrl\" storeDays=\"7\" branch=\"branch\" outName=\"outName\" >", result.get(0));
        assertEquals("<xPath>xPath</xPath>", result.get(1));
        assertEquals("</simpler>", result.get(2));
    }

    // ���� ���������� � �������� ��������

    public void testWithContentFilter() {
        SimplerConfiguration fixture = SimplerConfigurationTestUtils.createSimplerConfiguration("id", "");
        fixture.setAutoContentFiltering(true);

        List<String> result = SimplerConfigurationTools.render(fixture);

        assertEquals(4, result.size());
        assertEquals("<simpler feedUrl=\"feedUrl\" coverUrl=\"coverUrl\" storeDays=\"7\" branch=\"branch\" outName=\"outName\" >", result.get(0));
        assertEquals("<xPath>xPath</xPath>", result.get(1));
        assertEquals("<content-filter/>", result.get(2));
        assertEquals("</simpler>", result.get(3));
    }
}
