package work.unit.templater;

import junit.framework.TestCase;
import app.templater.*;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public class BriefTextFeedTemplatesFactoryTest extends TestCase {

    public BriefTextFeedTemplatesFactoryTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke(){
        TemplateContentFactory contentFactory = new TemplateContentFactory();
        BriefTextFeedTemplatesFactory templatesFactory = new BriefTextFeedTemplatesFactory(contentFactory);
        TemplateParameters parameters = new TemplateParameters(TemplateType.BRIEF_RSS_TEMPLATE, "name", "url", "workspace");

        List<Template> result = templatesFactory.createTemplates(parameters);

        TemplaterTestUtils.assertBriefTemplatesValid(result);
    }
}
