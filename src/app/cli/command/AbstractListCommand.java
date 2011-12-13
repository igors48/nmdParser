package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;
import java.util.Map;

/**
 * ����������� ������� ��������� ������ ����-����, �������������
 * ���������� ����������������
 *
 * @author Igor Usenko
 *         Date: 01.05.2009
 */
public abstract class AbstractListCommand implements Command {

    protected final ApiFacade facade;
    protected final Map<String, String> context;

    protected List<String> list;
    protected int forcedDays;

    private final Log log;

    public AbstractListCommand(final List<String> _list, final int _forcedDays, final ApiFacade _facade, final Map<String, String> _context) {
        Assert.notNull(_list, "Source list is null.");
        Assert.notNull(_facade, "Api facade is null.");
        Assert.notNull(_context, "Context is null.");

        this.list = _list;
        this.forcedDays = _forcedDays;
        this.facade = _facade;
        this.context = _context;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        for (String current : this.list) {
            processSilently(current);
        }
    }

    protected abstract void process(final String _name) throws ApiFacade.FatalException, ApiFacade.RecoverableException;

    private void processSilently(final String _current) {

        try {
            process(_current);
        } catch (ApiFacade.FatalException e) {
            this.log.error("Error processing command", e);
        } catch (ApiFacade.RecoverableException e) {
            this.log.error("Error processing command", e);
        }
    }

}
