package work.unit.constructor.dateparser;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.dateparser.configuration.DateParserConfiguration;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class DateParserConfigurationTest extends TestCase {

    public DateParserConfigurationTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("dateParser", "<dateParser><secondsFromEpoch/></dateParser>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        DateParserConfiguration configuration = (DateParserConfiguration) constructor.create("dateParser", ObjectType.DATEPARSER);

        assertTrue(configuration != null);
        assertEquals("dateParser", configuration.getId());
        assertEquals(1, configuration.getParsers().size());
    }

    // тест с параметрами стандартной стратегии

    public void testWithStandardParms() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("dateParser", "<dateParser><secondsFromEpoch/><standard><pattern>pattern</pattern><months>months</months></standard></dateParser>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        DateParserConfiguration configuration = (DateParserConfiguration) constructor.create("dateParser", ObjectType.DATEPARSER);

        assertTrue(configuration != null);
        assertEquals("dateParser", configuration.getId());
        assertEquals(1, configuration.getParsers().size());
        assertEquals("pattern", configuration.getPattern());
        assertEquals("months", configuration.getMonths());
    }

    // тест зависимостей

    public void testDependencies() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("dateParser", "<dateParser><secondsFromEpoch/><standard><pattern>pattern</pattern><months>months</months></standard></dateParser>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        DateParserConfiguration configuration = (DateParserConfiguration) constructor.create("dateParser", ObjectType.DATEPARSER);

        List<UsedObject> objects = configuration.getUsedObjects();

        assertTrue(objects.isEmpty());
    }

}
