package app.cli.command;

import app.api.ApiFacade;
import app.controller.NullController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Команда обновления каналов
 *
 * @author Igor Usenko
 *         Date: 16.04.2009
 */
public class UpdateChannelsCommand extends AbstractListCommand {

    private final Log log;

    public UpdateChannelsCommand(final List<String> _list, final int _forcedDays, final ApiFacade _facade, final Map<String, String> _context) {
        super(_list, _forcedDays, _facade, _context);

        this.log = LogFactory.getLog(getClass());
    }

    protected void process(final String _name) throws ApiFacade.FatalException, ApiFacade.RecoverableException {
        this.log.info("Update channel [ " + _name + " ].");
        this.facade.updateChannel(_name, this.forcedDays, new NullController(), this.context);
        this.log.info("Channel [ " + _name + " ] successfully updated.");
    }

}
