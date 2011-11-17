package app.cli.parser;

import app.templater.TemplateType;

import static app.cli.parser.OptionNameTable.CREATE_BRIEF_TEXT_TEMPLATES_OPTION_SHORT_NAME;

/**
 * Парсер опции "Создать комплект шаблонов для краткого RSS фида"
 *
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public class CreateBriefTextTemplatesOptionParser extends AbstractCreateTextTemplateOptionParser {

    protected TemplateType getType() {
        return TemplateType.BRIEF_RSS_TEMPLATE;
    }

    protected String getOptionsName() {
        return CREATE_BRIEF_TEXT_TEMPLATES_OPTION_SHORT_NAME;
    }
}