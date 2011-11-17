package work.unit.templater;

import app.templater.FeedTemplatesFactory;
import app.templater.Template;
import app.templater.TemplateParameters;
import app.templater.TemplateType;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public class FeedTemplatesFactoryTest extends TestCase {

    public FeedTemplatesFactoryTest(final String _s) {
        super(_s);
    }

    // ���� ��������� ��������� �������� ��� ������� ����

    public void testFullTemplates() {
        FeedTemplatesFactory factory = new FeedTemplatesFactory();
        TemplateParameters parameters = new TemplateParameters(TemplateType.FULL_RSS_TEMPLATE, "name", "url", "workspace");
        List<Template> result = factory.createTemplates(parameters);

        TemplaterTestUtils.assertFullTemplatesValid(result);
    }

    // ���� ��������� ��������� �������� ��� �������� ����

    public void testBriefTemplates() {
        FeedTemplatesFactory factory = new FeedTemplatesFactory();
        TemplateParameters parameters = new TemplateParameters(TemplateType.BRIEF_RSS_TEMPLATE, "name", "url", "workspace");
        List<Template> result = factory.createTemplates(parameters);

        TemplaterTestUtils.assertBriefTemplatesValid(result);
    }
}
