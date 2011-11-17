package work.unit.constructor.snippet;

import app.cli.blitz.request.CriterionType;
import app.cli.blitz.request.RequestSourceType;
import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.output.configuration.Composition;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import constructor.objects.storage.local.core.LocalStorage;
import junit.framework.TestCase;
import util.sequense.PatternListSequencer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 09.12.2009
 */
public class SnippetConfigurationTest extends TestCase {

    public SnippetConfigurationTest(final String _s) {
        super(_s);
    }

    // первоначальный тест - конфигурация для RSS + XPath

    public void testRssXPathConfiguration() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("snippet", "<snippet><rss>rss</rss><xPath>xPath</xPath><forced/><base>base</base><genre>genre1</genre><genre>genre2</genre><storage name=\"storage\"/><many-to-one name=\"mto\"/><branch name=\"branch\"/><noLinksAsFootnotes/><noRemoveExists/><noResolveImageLinks/><cover>cover</cover></snippet>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SnippetConfiguration configuration = (SnippetConfiguration) constructor.create("snippet", ObjectType.SNIPPET);

        assertNotNull(configuration);
        assertEquals("snippet", configuration.getId());

        assertFalse(configuration.isResolveImageLinks());
        assertFalse(configuration.isLinksAsFootnotes());
        assertFalse(configuration.isRemoveExists());

        assertEquals("storage", configuration.getStorage());
        assertEquals(Composition.MANY_TO_ONE, configuration.getComposition());
        assertEquals("mto", configuration.getOutName());
        assertEquals("branch", configuration.getBranch());

        assertEquals(RequestSourceType.RSS, configuration.getRequestSourceType());
        assertEquals(1, configuration.getUrlGenerationPatterns().size());
        assertEquals("rss", PatternListSequencer.getSequence(configuration.getUrlGenerationPatterns()).get(0));

        assertEquals(2, configuration.getGenres().size());
        assertEquals("genre1", configuration.getGenres().get(0));
        assertEquals("genre2", configuration.getGenres().get(1));

        assertEquals("base", configuration.getBase());

        assertTrue(configuration.isForced());

        assertEquals(CriterionType.XPATH, configuration.getCriterionType());
        assertEquals("xPath", configuration.getCriterionExpression());

        assertEquals("cover", configuration.getCoverUrl());
    }

    // тест - конфигурация для URLS + REGEXP

    public void testUrlsRegExpConfiguration() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("snippet", "<snippet><url>url1</url><url>url2</url><url>url3</url><regExp>regExp</regExp><forced/><base>base</base><genre>genre1</genre><genre>genre2</genre><storage name=\"storage\"/><many-to-one name=\"mto\"/><branch name=\"branch\"/><noLinksAsFootnotes/><noRemoveExists/><noResolveImageLinks/></snippet>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SnippetConfiguration configuration = (SnippetConfiguration) constructor.create("snippet", ObjectType.SNIPPET);

        assertNotNull(configuration);
        assertEquals("snippet", configuration.getId());

        assertFalse(configuration.isResolveImageLinks());
        assertFalse(configuration.isLinksAsFootnotes());
        assertFalse(configuration.isRemoveExists());

        assertEquals("storage", configuration.getStorage());
        assertEquals(Composition.MANY_TO_ONE, configuration.getComposition());
        assertEquals("mto", configuration.getOutName());
        assertEquals("branch", configuration.getBranch());

        assertEquals(RequestSourceType.URLS, configuration.getRequestSourceType());
        assertEquals(3, configuration.getUrlGenerationPatterns().size());
        assertEquals("url1", PatternListSequencer.getSequence(configuration.getUrlGenerationPatterns()).get(0));
        assertEquals("url2", PatternListSequencer.getSequence(configuration.getUrlGenerationPatterns()).get(1));
        assertEquals("url3", PatternListSequencer.getSequence(configuration.getUrlGenerationPatterns()).get(2));

        assertEquals(2, configuration.getGenres().size());
        assertEquals("genre1", configuration.getGenres().get(0));
        assertEquals("genre2", configuration.getGenres().get(1));

        assertEquals("base", configuration.getBase());

        assertTrue(configuration.isForced());

        assertEquals(CriterionType.REGEXP, configuration.getCriterionType());
        assertEquals("regExp", configuration.getCriterionExpression());
    }

    // тест обработки параметров генерации последовательностей

    public void testUrlsWithSequenceParametersConfiguration() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("snippet", "<snippet><url from=\"1\" to=\"2\" step=\"3\" mult=\"4\" len=\"5\" padd=\"6\">url1</url><regExp>regExp</regExp><forced/><base>base</base><genre>genre1</genre><genre>genre2</genre><storage name=\"storage\"/><many-to-one name=\"mto\"/><branch name=\"branch\"/><noLinksAsFootnotes/><noRemoveExists/><noResolveImageLinks/></snippet>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SnippetConfiguration configuration = (SnippetConfiguration) constructor.create("snippet", ObjectType.SNIPPET);

        assertNotNull(configuration);
        assertEquals(1, configuration.getUrlGenerationPatterns().size());

        assertEquals(1, configuration.getUrlGenerationPatterns().get(0).getFrom());
        assertEquals(2, configuration.getUrlGenerationPatterns().get(0).getTo());
        assertEquals(3, configuration.getUrlGenerationPatterns().get(0).getStep());
        assertEquals(4, configuration.getUrlGenerationPatterns().get(0).getMult());
        assertEquals(5, configuration.getUrlGenerationPatterns().get(0).getLen());
        assertEquals("6", configuration.getUrlGenerationPatterns().get(0).getPadding());
    }

    // тест определения зависимостей

    public void testExistentDependencyDetection() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("snippet", "<snippet><url from=\"1\" to=\"2\" step=\"3\" mult=\"4\" len=\"5\" padd=\"6\">url1</url><regExp>regExp</regExp><forced/><base>base</base><genre>genre1</genre><genre>genre2</genre><storage name=\"storage\"/><many-to-one name=\"mto\"/><branch name=\"branch\"/><noLinksAsFootnotes/><noRemoveExists/><noResolveImageLinks/></snippet>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SnippetConfiguration configuration = (SnippetConfiguration) constructor.create("snippet", ObjectType.SNIPPET);

        List<UsedObject> usedObjects = configuration.getUsedObjects();

        assertEquals(1, usedObjects.size());
        assertEquals("storage", usedObjects.get(0).getId());
        assertEquals(ObjectType.STORAGE, usedObjects.get(0).getType());
    }

    // тест отсутствия зависимостей

    public void testNoDependencyDetection() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("snippet", "<snippet><url from=\"1\" to=\"2\" step=\"3\" mult=\"4\" len=\"5\" padd=\"6\">url1</url><regExp>regExp</regExp><forced/><base>base</base><genre>genre1</genre><genre>genre2</genre><many-to-one name=\"mto\"/><branch name=\"branch\"/><noLinksAsFootnotes/><noRemoveExists/><noResolveImageLinks/></snippet>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SnippetConfiguration configuration = (SnippetConfiguration) constructor.create("snippet", ObjectType.SNIPPET);

        List<UsedObject> usedObjects = configuration.getUsedObjects();

        assertEquals(1, usedObjects.size());
        assertEquals(LocalStorage.DEFAULT_STORAGE_ID, usedObjects.get(0).getId());
        assertEquals(ObjectType.STORAGE, usedObjects.get(0).getType());
    }

    // тест подлючения фильтра контента

    public void testRssWithFilterConfiguration() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("snippet", "<snippet><rss>rss</rss><content-filter/><forced/><many-to-one name=\"mto\"/><branch name=\"branch\"/></snippet>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        SnippetConfiguration configuration = (SnippetConfiguration) constructor.create("snippet", ObjectType.SNIPPET);

        assertNotNull(configuration);
        assertEquals("snippet", configuration.getId());

        assertEquals(CriterionType.FILTER, configuration.getCriterionType());
    }

}
