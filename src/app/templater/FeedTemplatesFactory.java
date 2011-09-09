package app.templater;

import util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Фабрика комплектка шаблонов для фида
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class FeedTemplatesFactory implements TemplatesFactory {

    private static Map<TemplateType, TemplatesFactory> factories;

    static {
        factories = new HashMap<TemplateType, TemplatesFactory>();

        factories.put(TemplateType.BRIEF_RSS_TEMPLATE, new BriefTextFeedTemplatesFactory(new TemplateContentFactory()));
        factories.put(TemplateType.FULL_RSS_TEMPLATE, new FullTextFeedTemplatesFactory(new TemplateContentFactory()));
    }

    public List<Template> createTemplates(final TemplateParameters _parameters) {
        Assert.notNull(_parameters, "Template creation parameters is null");

        return factories.get(_parameters.getType()).createTemplates(_parameters);
    }
}