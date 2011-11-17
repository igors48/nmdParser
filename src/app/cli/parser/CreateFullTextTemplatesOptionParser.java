package app.cli.parser;

import app.templater.TemplateType;

import static app.cli.parser.OptionNameTable.CREATE_FULL_TEXT_TEMPLATES_OPTION_SHORT_NAME;

/**
 * Парсер опции "Создать комплект шаблонов для полнотектового RSS фида"
 *
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public class CreateFullTextTemplatesOptionParser extends AbstractCreateTextTemplateOptionParser {

    protected TemplateType getType() {
        return TemplateType.FULL_RSS_TEMPLATE;
    }

    protected String getOptionsName() {
        return CREATE_FULL_TEXT_TEMPLATES_OPTION_SHORT_NAME;
    }

}
