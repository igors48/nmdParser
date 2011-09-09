package work.unit.templater;

import junit.framework.TestCase;
import app.templater.*;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public class FullTextFeedTemplatesFactoryTest extends TestCase {

    public FullTextFeedTemplatesFactoryTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke(){
        TemplateContentFactory contentFactory = new TemplateContentFactory();
        FullTextFeedTemplatesFactory templatesFactory = new FullTextFeedTemplatesFactory(contentFactory);
        TemplateParameters parameters = new TemplateParameters(TemplateType.FULL_RSS_TEMPLATE, "name", "url", "workspace");

        List<Template> result = templatesFactory.createTemplates(parameters);

        TemplaterTestUtils.assertFullTemplatesValid(result);
    }

}