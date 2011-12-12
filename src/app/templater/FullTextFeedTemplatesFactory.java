package app.templater;

import util.Assert;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * Фабрика комплектка насетапленных шаблонов для полнотекстового фида
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class FullTextFeedTemplatesFactory implements TemplatesFactory {

    private final TemplateContentFactory contentFactory;

    public FullTextFeedTemplatesFactory(final TemplateContentFactory contentFactory) {
        Assert.notNull(contentFactory, "Template content factory is null");
        this.contentFactory = contentFactory;
    }

    public List<Template> createTemplates(final TemplateParameters _parameters) {
        Assert.notNull(_parameters, "Template creation parameters is null");

        List<Template> result = newArrayList();
        Map<String, String> parameters = TemplatesFactoryUtils.createParametersMap(_parameters);

        List<String> sourceContent = PlaceHolderUtils.replace(this.contentFactory.getTemplateContent(TemplateContentType.SOURCE), parameters);
        result.add(new Template(TemplatesFactory.SOURCE_NAME, sourceContent));

        List<String> channelContent = PlaceHolderUtils.replace(this.contentFactory.getTemplateContent(TemplateContentType.BRIEF_CHANNEL), parameters);
        result.add(new Template(TemplatesFactory.CHANNEL_NAME, channelContent));

        List<String> outputContent = PlaceHolderUtils.replace(this.contentFactory.getTemplateContent(TemplateContentType.OUTPUT), parameters);
        result.add(new Template(TemplatesFactory.OUTPUT_NAME, outputContent));

        return result;
    }

}