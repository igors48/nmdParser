package app.cli.parser;

import static app.cli.parser.OptionNameTable.CREATE_BRIEF_TEXT_TEMPLATES_OPTION_SHORT_NAME;
import app.templater.TemplateType;

/**
 * ������ ����� "������� �������� �������� ��� �������� RSS ����"
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