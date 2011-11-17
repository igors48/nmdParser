package work.unit.dom;

import app.workingarea.service.NullServiceManager;
import constructor.dom.*;
import constructor.dom.constructor.StandardConstructorFactory;
import constructor.dom.loader.DomStreamLoader;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public class ConstructorTest extends TestCase {

    public ConstructorTest(String s) {
        super(s);
    }

    // тест на установку одного атрибута

    public void testSingleAttributeSetting() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId\"></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals("sample01", object.getId());
        assertNull(object.getName());
    }

    // тест на установку двух атрибутов

    public void testDualAttributeSetting() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId\" name=\"sampleName\"></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals("sample01", object.getId());
        assertEquals("sampleName", object.getName());
    }

    // тест обработки единичного элемента

    public void testSingleElementHandling() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01><inner>48</inner></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals("48", object.getInner());
    }

    // тест обработки единичного элемента и двух аттрибутов

    public void testDualAttributesSingleElementHandling() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId\" name=\"sampleName\"><inner>48</inner></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals("48", object.getInner());
        assertEquals("sample01", object.getId());
        assertEquals("sampleName", object.getName());
    }

    // тест обработки загрузки вложенного объекта по его идентификатору

    public void testNestedObject() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId1\"><nested>sample02</nested></sample01>");
        streams.put("sample02", "<sample01 id=\"sampleId2\" name=\"sampleName2\"/>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals("sample01", object.getId());
        assertNotNull(object.getObject());
        assertEquals("sample02", ((SampleObject01) object.getObject()).getId());
        assertEquals("sampleName2", ((SampleObject01) object.getObject()).getName());
    }

    // тест обработки единичного элемента переданного через CDATA и двух аттрибутов

    public void testDualAttributesSingleCDataElementHandling() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId\" name=\"sampleName\"><inner><![CDATA[<48.+?//>]]></inner></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals("<48.+?//>", object.getInner());
        assertEquals("sample01", object.getId());
        assertEquals("sampleName", object.getName());
    }

    // тест обработки единичного элемента с пустой строкой и двух аттрибутов

    public void testDualAttributesSingleEmptyElementHandling() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId\" name=\"sampleName\"><inner></inner></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals(" ", object.getInner());
        assertEquals("sample01", object.getId());
        assertEquals("sampleName", object.getName());
    }

    // тест обработки единичного элемента в сокращенной форме с пустой строкой и двух аттрибутов

    public void testDualAttributesSingleEmptyShortFormElementHandling() throws Constructor.ConstructorException {
        ComponentFactory componentFactory = new SampleComponentFactory();

        Loader loader = new DomStreamLoader(componentFactory);

        Map<String, String> streams = new HashMap<String, String>();
        streams.put("sample01", "<sample01 id=\"sampleId\" name=\"sampleName\"><inner/></sample01>");
        Locator locator = new LocatorMock(streams);

        ConstructorFactory constructorFactory = new StandardConstructorFactory(locator, loader, new NullServiceManager());

        Constructor constructor = constructorFactory.getConstructor();
        SampleObject01 object = (SampleObject01) constructor.create("sample01", ObjectType.SAMPLE_OBJECT_01);

        assertEquals(" ", object.getInner());
        assertEquals("sample01", object.getId());
        assertEquals("sampleName", object.getName());
    }


}
