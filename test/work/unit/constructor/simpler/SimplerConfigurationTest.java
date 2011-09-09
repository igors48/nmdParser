package work.unit.constructor.simpler;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

import constructor.dom.ConstructorFactory;
import constructor.dom.Constructor;
import constructor.dom.ObjectType;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 06.07.2010
 */
public class SimplerConfigurationTest extends TestCase {

    public SimplerConfigurationTest(final String _s) {
        super(_s);
    }

    public void testWithDefaultFromNewToOld() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("simpler", "<simpler feedUrl=\"feedUrl\" coverUrl=\"coverUrl\" storeDays=\"storeDays\" branch=\"branch\" outName=\"outName\"><xPath>xp01</xPath></simpler>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SimplerConfiguration configuration = (SimplerConfiguration) constructor.create("simpler", ObjectType.SIMPLER);

        assertEquals("simpler", configuration.getId());
        assertEquals("feedUrl", configuration.getFeedUrl());
        assertEquals("storeDays", configuration.getStoreDays());
        assertEquals("branch", configuration.getBranch());
        assertEquals("outName", configuration.getOutName());
        assertEquals("coverUrl", configuration.getCoverUrl());
        assertEquals(0, configuration.getPauseBetweenRequests());
        assertEquals(DocumentItemsSortMode.DEFAULT, configuration.getFromNewToOld());
        assertEquals("xp01", configuration.getCriterions());
    }

    public void testPauseBetweenRequests() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("simpler", "<simpler feedUrl=\"feedUrl\" coverUrl=\"coverUrl\" storeDays=\"storeDays\" branch=\"branch\" outName=\"outName\" pauseBetweenRequests=\"48\"><xPath>xp01</xPath><xPath>xp02</xPath><xPath>xp03</xPath></simpler>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SimplerConfiguration configuration = (SimplerConfiguration) constructor.create("simpler", ObjectType.SIMPLER);

        assertEquals(48, configuration.getPauseBetweenRequests());
    }

    public void testSettedFromNewToOld() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("simpler", "<simpler feedUrl=\"feedUrl\" storeDays=\"storeDays\" branch=\"branch\" outName=\"outName\" fromNewToOld=\"no\"><xPath>xp01</xPath><xPath>xp02</xPath><xPath>xp03</xPath></simpler>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SimplerConfiguration configuration = (SimplerConfiguration) constructor.create("simpler", ObjectType.SIMPLER);

        assertEquals(DocumentItemsSortMode.FROM_OLD_TO_NEW, configuration.getFromNewToOld());
    }
}
