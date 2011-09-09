package work.unit.constructor.storage;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.storage.local.configuration.LocalStorageConfiguration;
import junit.framework.TestCase;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class LocalStorageConfigurationTest extends TestCase {

    private static final int TO_MILLIS = 24 * 60 * 60 * 1000;

    public LocalStorageConfigurationTest(String s) {
        super(s);
    }

    // первоначальный тест с дефолтным временем хранения
    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("storage", "<storage><root>E:/root/</root></storage>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        LocalStorageConfiguration configuration = (LocalStorageConfiguration) constructor.create("storage", ObjectType.STORAGE);

        assertTrue(configuration != null);
        assertEquals("storage", configuration.getId());
        assertEquals("E:/root/", configuration.getRoot());
        assertEquals(0, configuration.getAge());
    }
    
    // тест с указанным временем хранения
    public void testAgeSet() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("storage", "<storage days=\"3\"><root>E:/root/</root></storage>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        LocalStorageConfiguration configuration = (LocalStorageConfiguration) constructor.create("storage", ObjectType.STORAGE);

        assertTrue(configuration != null);
        assertEquals("storage", configuration.getId());
        assertEquals("E:/root/", configuration.getRoot());
        assertEquals(3 * TO_MILLIS, configuration.getAge());
    }

    // тест зависимостей
    public void testDependencies() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("storage", "<storage days=\"3\"><root>E:/root/</root></storage>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        LocalStorageConfiguration configuration = (LocalStorageConfiguration) constructor.create("storage", ObjectType.STORAGE);

        List<UsedObject> objects = configuration.getUsedObjects();

        assertTrue(objects.isEmpty());
    }
}
