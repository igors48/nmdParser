package work.unit.constructor.interpreter;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.interpreter.configuration.InterpreterConfiguration;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newHashMap;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public class InterpreterConfigurationTest extends TestCase {

    public InterpreterConfigurationTest(String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("interpreter", "<interpreter><fragment></fragment></interpreter>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        InterpreterConfiguration configuration = (InterpreterConfiguration) constructor.create("interpreter", ObjectType.INTERPRETER);

        assertTrue(configuration != null);
        assertEquals("interpreter", configuration.getId());
    }

    // тест с процессорами

    public void testWithProcessor() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("interpreter", "<interpreter><fragment content=\"processor\"/></interpreter>");
        streams.put("processor", "<processor><getGroup><occurrence>0</occurrence><pattern>abc(.+?)def</pattern></getGroup></processor>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        InterpreterConfiguration configuration = (InterpreterConfiguration) constructor.create("interpreter", ObjectType.INTERPRETER);

        assertTrue(configuration != null);
        assertEquals("interpreter", configuration.getId());
    }

    // тест определения зависимостей

    public void testDependencies() throws Constructor.ConstructorException {
        Map<String, String> streams = newHashMap();
        streams.put("interpreter", "<interpreter pages=\"pages\" fragments=\"fragments\"><fragment nick=\"nick\" info=\"info\" avatar=\"avatar\" title=\"title\" date=\"date\" content=\"content\" dateParser=\"parser\"/></interpreter>");

        streams.put("pages", "<processor></processor>");
        streams.put("fragments", "<processor></processor>");

        streams.put("nick", "<processor></processor>");
        streams.put("info", "<processor></processor>");
        streams.put("avatar", "<processor></processor>");
        streams.put("title", "<processor></processor>");
        streams.put("date", "<processor></processor>");
        streams.put("content", "<processor></processor>");

        streams.put("parser", "<dateParser></dateParser>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        InterpreterConfiguration configuration = (InterpreterConfiguration) constructor.create("interpreter", ObjectType.INTERPRETER);

        List<UsedObject> result = configuration.getUsedObjects();

        assertEquals(9, result.size());

        assertEquals("pages", result.get(0).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(0).getType());

        assertEquals("fragments", result.get(1).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(1).getType());

        assertEquals("nick", result.get(2).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(2).getType());

        assertEquals("info", result.get(3).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(3).getType());

        assertEquals("avatar", result.get(4).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(4).getType());

        assertEquals("title", result.get(5).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(5).getType());

        assertEquals("date", result.get(6).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(6).getType());

        assertEquals("content", result.get(7).getId());
        assertEquals(ObjectType.PROCESSOR, result.get(7).getType());

        assertEquals("parser", result.get(8).getId());
        assertEquals(ObjectType.DATEPARSER, result.get(8).getType());
    }

}
