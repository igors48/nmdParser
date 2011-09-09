package work.unit.constructor.snippet;

import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.RequestSourceType;
import app.cli.blitz.request.CriterionType;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import constructor.objects.snippet.core.SnippetConverter;
import constructor.objects.output.configuration.Composition;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 12.12.2009
 */
public class SnippetConverterTest extends TestCase {

    public SnippetConverterTest(final String _s) {
        super(_s);
    }

    // первоначальный тест - тип источника не задан
    public void testSmoke() {

        try {
            SnippetConfiguration configuration = new SnippetConfiguration();
            BlitzRequest request = SnippetConverter.convert(configuration);

            fail();
        } catch (SnippetConverter.SnippetConverterException e) {
            // ignore
        }
    }

    // тест для источника RSS + REGEXP
    public void testRssRegExpSourceType() throws SnippetConverter.SnippetConverterException {
        SnippetConfiguration configuration = new SnippetConfiguration();
        configuration.setRssUrl("rssurl");

        BlitzRequest request = SnippetConverter.convert(configuration);

        assertEquals(RequestSourceType.RSS, request.getSourceType());
        assertEquals(1, request.getAddresses().size());
        assertEquals("rssurl", request.getAddresses().get(0));

        assertEquals(CriterionType.REGEXP, request.getCriterionType());
        assertEquals("<body(.+?)</body>", request.getCriterionExpression());

        assertEquals(Composition.ONE_TO_ONE, request.getComposition());
    }

    // тест для источника RSS + XPATH
    public void testRssXPathSourceType() throws SnippetConverter.SnippetConverterException {
        SnippetConfiguration configuration = new SnippetConfiguration();
        configuration.setRssUrl("rssurl");
        configuration.setXPath("xpath");
        
        BlitzRequest request = SnippetConverter.convert(configuration);

        assertEquals(RequestSourceType.RSS, request.getSourceType());
        assertEquals(1, request.getAddresses().size());
        assertEquals("rssurl", request.getAddresses().get(0));

        assertEquals(CriterionType.XPATH, request.getCriterionType());
        assertEquals("xpath", request.getCriterionExpression());

        assertEquals(Composition.ONE_TO_ONE, request.getComposition());
    }

    // тест для источника URLs without base + REGEXP
    public void testUrlsWithoutBaseRegExpSourceType() throws SnippetConverter.SnippetConverterException {
        SnippetConfiguration configuration = new SnippetConfiguration();
        configuration.addUrl("url1");
        configuration.addUrl("url2");
        configuration.addUrl("url3");

        configuration.setRegExp("regexp");
        BlitzRequest request = SnippetConverter.convert(configuration);

        assertEquals(RequestSourceType.URLS, request.getSourceType());
        assertEquals(3, request.getAddresses().size());
        assertEquals("url1", request.getAddresses().get(0));
        assertEquals("url2", request.getAddresses().get(1));
        assertEquals("url3", request.getAddresses().get(2));

        assertEquals(CriterionType.REGEXP, request.getCriterionType());
        assertEquals("regexp", request.getCriterionExpression());

        assertEquals(Composition.ONE_TO_ONE, request.getComposition());
    }
    
    // тест для источника URLs with base + REGEXP
    public void testUrlsWithBaseRegExpSourceType() throws SnippetConverter.SnippetConverterException {
        SnippetConfiguration configuration = new SnippetConfiguration();
        configuration.addUrl("url1");
        configuration.addUrl("url2");
        configuration.addUrl("url3");
        configuration.setBase("base");
        
        configuration.setRegExp("regexp");
        BlitzRequest request = SnippetConverter.convert(configuration);

        assertEquals(RequestSourceType.URLS, request.getSourceType());
        assertEquals(3, request.getAddresses().size());
        assertEquals("baseurl1", request.getAddresses().get(0));
        assertEquals("baseurl2", request.getAddresses().get(1));
        assertEquals("baseurl3", request.getAddresses().get(2));

        assertEquals(CriterionType.REGEXP, request.getCriterionType());
        assertEquals("regexp", request.getCriterionExpression());

        assertEquals(Composition.ONE_TO_ONE, request.getComposition());
    }

    // тест для источника URLs without base + REGEXP + ManyToOne
    public void testUrlsWithoutBaseRegExpSourceTypeManyToOne() throws SnippetConverter.SnippetConverterException {
        SnippetConfiguration configuration = new SnippetConfiguration();
        configuration.addUrl("url1");
        configuration.addUrl("url2");
        configuration.addUrl("url3");

        configuration.setRegExp("regexp");

        configuration.setManyToOne("mto");

        BlitzRequest request = SnippetConverter.convert(configuration);

        assertEquals(RequestSourceType.URLS, request.getSourceType());
        assertEquals(3, request.getAddresses().size());
        assertEquals("url1", request.getAddresses().get(0));
        assertEquals("url2", request.getAddresses().get(1));
        assertEquals("url3", request.getAddresses().get(2));

        assertEquals(CriterionType.REGEXP, request.getCriterionType());
        assertEquals("regexp", request.getCriterionExpression());

        assertEquals(Composition.MANY_TO_ONE, request.getComposition());
        assertEquals("mto", request.getOutName());
    }

    // тест на установку coverUrl
    public void testCoverUrlSet() throws SnippetConverter.SnippetConverterException {
        SnippetConfiguration configuration = new SnippetConfiguration();
        configuration.setRssUrl("rssurl");
        configuration.setCoverUrl("cover");
        
        BlitzRequest request = SnippetConverter.convert(configuration);

        assertEquals("cover", request.getCoverUrl());
    }

}
