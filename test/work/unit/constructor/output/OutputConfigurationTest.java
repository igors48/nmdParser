package work.unit.constructor.output;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.OutputConfiguration;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newHashMap;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class OutputConfigurationTest extends TestCase {

    public OutputConfigurationTest(String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        assertTrue(configuration != null);
        assertEquals("output", configuration.getId());
        assertEquals("branch", configuration.getBranch());
        assertEquals("channel", configuration.getChannelId());
        assertEquals("format", configuration.getFormat());
        assertEquals("storage", configuration.getStorageId());
        assertFalse(configuration.isLinksAsFootnotes());
    }

    // тест режима обработки линков - обработка отключена

    public void testLinkModeOff() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\" linksAsFootnotes=\"no\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        assertTrue(configuration != null);
        assertFalse(configuration.isLinksAsFootnotes());
    }

    // тест режима обработки линков - обработка включена

    public void testLinkModeOn() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\" linksAsFootnotes=\"yEs\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        assertTrue(configuration != null);
        assertTrue(configuration.isLinksAsFootnotes());
    }

    // тест на конфигурирование one-to-one по умолчанию

    public void testOneToOne() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        assertTrue(configuration != null);
        assertEquals(Composition.ONE_TO_ONE, configuration.getComposition());
    }

    // тест на прямое конфигурирование one-to-one

    public void testOneToOneDirect() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\"><one-to-one/></output>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        assertTrue(configuration != null);
        assertEquals(Composition.ONE_TO_ONE, configuration.getComposition());
    }

    // тест на прямое конфигурирование many-to-one

    public void testManyToOneDirect() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\"><many-to-one name=\"docname\"/></output>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        assertTrue(configuration != null);
        assertEquals(Composition.MANY_TO_ONE, configuration.getComposition());
        assertEquals("docname", configuration.getName());
    }

    // тест определения зависимостей

    public void testDependencies() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("output", "<output format=\"format\" storage=\"storage\" branch=\"branch\" channel=\"channel\"><many-to-one name=\"docname\"/></output>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        OutputConfiguration configuration = (OutputConfiguration) constructor.create("output", ObjectType.OUTPUT);

        List<UsedObject> result = configuration.getUsedObjects();

        assertEquals(1, result.size());
        assertEquals("channel", result.get(0).getId());
        assertEquals(ObjectType.CHANNEL, result.get(0).getType());
    }
}
