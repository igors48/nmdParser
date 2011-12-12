package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Команда обработки сниппетов
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public class ProcessSnippetsCommand extends AbstractListCommand {

    private final Log log;

    public ProcessSnippetsCommand(final List<String> _list, final int _forcedDays, final ApiFacade _facade, final Map<String, String> _context) {
        super(_list, _forcedDays, _facade, _context);

        this.log = LogFactory.getLog(getClass());
    }

    protected void process(final String _name) throws ApiFacade.FatalException, ApiFacade.RecoverableException {
        this.log.info("Process snippet [ " + _name + " ].");
        this.facade.processSnippet(_name, this.forcedDays, this.context);
        this.log.info("Snippet [ " + _name + " ] successfully processed.");
    }
    
}
