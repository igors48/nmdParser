package app.cli.command;

import app.api.ApiFacade;
import constructor.dom.locator.Mask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.ArrayList;
import java.util.Map;

/**
 *  оманда обновлени€ всех доступных источников
 *
 * @author Igor Usenko
 *         Date: 01.05.2009
 */
public class UpdateAllSourcesCommand extends UpdateSourcesCommand {

    private final Mask mask;
    private final Log log;

    public UpdateAllSourcesCommand(final Mask _mask, final ApiFacade _facade, final Map<String, String> _context) {
        super(new ArrayList<String>(), _facade, _context);

        Assert.notNull(_mask, "Mask is null.");
        this.mask = _mask;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.list = this.facade.getSourcesNames(this.mask);
            this.list.addAll(this.facade.getSimplersNames(this.mask));

            this.log.info("There are [ " + this.list.size() + " ] source(s) found.");

            super.execute();
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
    
}
