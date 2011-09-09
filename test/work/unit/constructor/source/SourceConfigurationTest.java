package work.unit.constructor.source;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.source.configuration.SourceConfiguration;
import constructor.objects.source.configuration.FetcherType;
import junit.framework.TestCase;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 26.03.2009
 */
public class SourceConfigurationTest extends TestCase {

    public SourceConfigurationTest(String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("processor", "<processor><getGroup in=\"inp\" out=\"sample\"><occurrence>0</occurrence><pattern>abc(.+?)def</pattern></getGroup><getGroup in=\"sample\" out=\"out\"><occurrence>0</occurrence><pattern>z(.+?)z</pattern></getGroup></processor>");
        streams.put("source", "<source><store days=\"48\"/><update mode=\"auto\"/><rss>http://k.img.com.ua/rss/ru/news.xml</rss><processor id=\"processor\"/></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        assertEquals("source", configuration.getId());
        assertEquals("48", configuration.getStoreDays());
        assertTrue(configuration.isAutoUpdate());
        assertEquals(FetcherType.RSS, configuration.getFetcherType());
        assertEquals("http://k.img.com.ua/rss/ru/news.xml", configuration.getFetchedUrls().get(0));
        assertEquals("processor", configuration.getProcessor().getId());
    }

    // тест на простые урлы
    public void testSimpleUrls() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("source", "<source><store days=\"48\"/><update mode=\"auto\"/><url>url1</url><url>url2</url></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        assertEquals("source", configuration.getId());
        assertEquals("48", configuration.getStoreDays());
        assertTrue(configuration.isAutoUpdate());
        assertEquals(FetcherType.URL, configuration.getFetcherType());
        assertEquals("url1", configuration.getFetchedUrls().get(0));
        assertEquals("url2", configuration.getFetchedUrls().get(1));
    }

    // тест на генератор последовательности урлов
    public void testUrlsGeneration() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("source", "<source><store days=\"48\"/><update mode=\"auto\"/><url>url1</url><url from=\"1\" to=\"5\" step=\"2\">url*2</url></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        assertEquals("source", configuration.getId());
        assertEquals("48", configuration.getStoreDays());
        assertTrue(configuration.isAutoUpdate());
        assertEquals(FetcherType.URL, configuration.getFetcherType());

        List<String> urls =  configuration.getFetchedUrls();

        assertEquals(4, urls.size());
        
        assertEquals("url1", urls.get(0));
        assertEquals("url12", urls.get(1));
        assertEquals("url32", urls.get(2));
        assertEquals("url52", urls.get(3));
    }

    // тест на зависимости - зависимостей нет
    public void testNoDependencies() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("source", "<source><store days=\"48\"/><url>url1</url></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        List<UsedObject> objects =  configuration.getUsedObjects();

        assertEquals(0, objects.size());
    }

    // тест на зависимости - зависимость от кастом детектора модификаций
    public void testDependensCustomFetcher() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();

        streams.put("fetcher", "<processor></processor>");
        streams.put("source", "<source><store days=\"48\"/><custom id=\"fetcher\"/></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        List<UsedObject> urls =  configuration.getUsedObjects();

        assertEquals(1, urls.size());

        assertEquals("fetcher", urls.get(0).getId());
        assertEquals(ObjectType.PROCESSOR, urls.get(0).getType());
    }

    // тест на зависимости - зависимость от постпроцессора
    public void testDependensPostprocessor() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();

        streams.put("postprocessor", "<processor></processor>");
        streams.put("source", "<source><store days=\"48\"/><custom id=\"postprocessor\"/></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        List<UsedObject> urls =  configuration.getUsedObjects();

        assertEquals(1, urls.size());

        assertEquals("postprocessor", urls.get(0).getId());
        assertEquals(ObjectType.PROCESSOR, urls.get(0).getType());
    }

    // тест на зависимости - зависимость от кастом детектора модификаций и от постпроцессора
    public void testDependensFetcherPostprocessor() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();

        streams.put("fetcher", "<processor></processor>");
        streams.put("post", "<processor></processor>");
        streams.put("source", "<source><store days=\"48\"/><custom id=\"fetcher\"/><processor id=\"post\"/></source>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SourceConfiguration configuration = (SourceConfiguration) constructor.create("source", ObjectType.SOURCE);

        List<UsedObject> urls =  configuration.getUsedObjects();

        assertEquals(2, urls.size());

        assertEquals("fetcher", urls.get(0).getId());
        assertEquals(ObjectType.PROCESSOR, urls.get(0).getType());

        assertEquals("post", urls.get(1).getId());
        assertEquals(ObjectType.PROCESSOR, urls.get(1).getType());
    }
}
