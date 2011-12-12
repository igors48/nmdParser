package app.templater;

import util.Assert;
import util.TextTools;

/**
 * Комплект параметров для создания комплекта шаблонов
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class TemplateParameters {

    private final TemplateType type;
    private final String name;
    private final String rssUrl;
    private final String workspace;

    public TemplateParameters(TemplateType _type, String _name, String _rssUrl, String _workspace) {
        Assert.notNull(_type, "Template type is null");
        this.type = _type;

        Assert.isValidString(_name, "Template name is not valid");
        this.name = TextTools.escapeAmpersands(_name);

        Assert.isValidString(_rssUrl, "RSS URL is not valid");
        this.rssUrl = TextTools.escapeAmpersands(_rssUrl);

        Assert.isValidString(_workspace, "Workspace name is not valid");
        this.workspace = TextTools.escapeAmpersands(_workspace);
    }

    public String getName() {
        return this.name;
    }

    public TemplateType getType() {
        return this.type;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public String getRssUrl() {
        return this.rssUrl;
    }
    
}
