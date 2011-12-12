package app.templater;

import util.Assert;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * ������� ���������� �������� ��� ����
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class FeedTemplatesFactory implements TemplatesFactory {

    private static Map<TemplateType, TemplatesFactory> factories;

    static {
        factories = newHashMap();

        factories.put(TemplateType.BRIEF_RSS_TEMPLATE, new BriefTextFeedTemplatesFactory(new TemplateContentFactory()));
        factories.put(TemplateType.FULL_RSS_TEMPLATE, new FullTextFeedTemplatesFactory(new TemplateContentFactory()));
    }

    public List<Template> createTemplates(final TemplateParameters _parameters) {
        Assert.notNull(_parameters, "Template creation parameters is null");

        return factories.get(_parameters.getType()).createTemplates(_parameters);
    }
    
}