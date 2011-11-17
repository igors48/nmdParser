package work.unit.constructor.channel;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.channel.configuration.ChannelConfiguration;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public class ChannelConfigurationTest extends TestCase {

    public ChannelConfigurationTest(String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("channel", "<channel source=\"sourceId\" interpreter=\"ixbt-thread-interpreter\" lastitems=\"100\" forced=\"yes\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        ChannelConfiguration configuration = (ChannelConfiguration) constructor.create("channel", ObjectType.CHANNEL);

        assertTrue(configuration != null);
        assertEquals("channel", configuration.getId());
        assertEquals("sourceId", configuration.getSourceId());
        assertEquals(100, configuration.getLastItemCount());
        assertEquals(0, configuration.getPauseBetweenRequests());
        assertTrue(configuration.isForced());
    }

    // тест конфигурирования паузы между запросами

    public void testPauseBetweenRequests() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("channel", "<channel source=\"sourceId\" interpreter=\"ixbt-thread-interpreter\" lastitems=\"100\" forced=\"yes\" pauseBetweenRequests=\"48\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        ChannelConfiguration configuration = (ChannelConfiguration) constructor.create("channel", ObjectType.CHANNEL);

        assertEquals(48, configuration.getPauseBetweenRequests());
    }

    // тест режима обновления по умолчанию

    public void testDefaultUpdateMode() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("channel", "<channel source=\"sourceId\" interpreter=\"ixbt-thread-interpreter\" lastitems=\"100\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        ChannelConfiguration configuration = (ChannelConfiguration) constructor.create("channel", ObjectType.CHANNEL);

        assertTrue(configuration != null);
        assertEquals("channel", configuration.getId());
        assertEquals("sourceId", configuration.getSourceId());
        assertEquals(100, configuration.getLastItemCount());
        assertFalse(configuration.isForced());
    }

    // тест определения зависимостей

    public void testDependencies() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("channel", "<channel source=\"sourceId\" interpreter=\"ixbt-thread-interpreter\" header=\"header\" lastitems=\"100\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        ChannelConfiguration configuration = (ChannelConfiguration) constructor.create("channel", ObjectType.CHANNEL);

        List<UsedObject> result = configuration.getUsedObjects();

        assertEquals(3, result.size());
        assertEquals("sourceId", result.get(0).getId());
        assertEquals(ObjectType.SOURCE, result.get(0).getType());
        assertEquals("ixbt-thread-interpreter", result.get(1).getId());
        assertEquals(ObjectType.INTERPRETER, result.get(1).getType());
        assertEquals("header", result.get(2).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(2).getType());
    }

    // тест определения зависимостей без процессора и интерпретатора

    public void testDependenciesWithoutInterpreterAndProcessor() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("channel", "<channel source=\"sourceId\"/>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        ChannelConfiguration configuration = (ChannelConfiguration) constructor.create("channel", ObjectType.CHANNEL);

        List<UsedObject> result = configuration.getUsedObjects();

        assertEquals(1, result.size());
        assertEquals("sourceId", result.get(0).getId());
        assertEquals(ObjectType.SOURCE, result.get(0).getType());
    }
}
