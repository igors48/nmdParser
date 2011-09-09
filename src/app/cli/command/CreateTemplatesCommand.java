package app.cli.command;

import app.api.ApiFacade;
import app.templater.TemplateParameters;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Команда создания комплекта шаблонов для работы с фидом
 *
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public class CreateTemplatesCommand implements Command {
    private final TemplateParameters parameters;
    private final ApiFacade facade;

    private final Log log;

    public CreateTemplatesCommand(final TemplateParameters parameters, final ApiFacade _facade) {
        Assert.notNull(parameters, "Template parameters is null");
        Assert.notNull(_facade, "Api facade is null");

        this.parameters = parameters;
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {
        try {
            this.facade.createTemplates(this.parameters);
            this.log.info("Templates for RSS feed [ " + this.parameters.getRssUrl() + " ] created successfully in workspace [ " + this.parameters.getWorkspace() + " ]");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}
