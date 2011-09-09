package app.cli.command;

import app.api.ApiFacade;
import app.controller.NullController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Команда обновления источников
 *
 * @author Igor Usenko
 *         Date: 16.04.2009
 */
public class UpdateSourcesCommand extends AbstractListCommand {

    private final Log log;

    public UpdateSourcesCommand(final List<String> _list, final ApiFacade _facade, final Map<String, String> _context) {
        super(_list, 0, _facade, _context);

        this.log = LogFactory.getLog(getClass());
    }

    protected void process(final String _name) throws ApiFacade.FatalException, ApiFacade.RecoverableException {
        this.log.info("Update source [ " + _name + " ].");
        this.facade.updateSource(_name, new NullController(), this.context);
        this.log.info("Source [ " + _name + " ] successfully updated.");
    }

}