package app.cli.command;

import app.api.ApiFacade;
import app.cli.timetable.TimeTable;
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
    private TimeTable timeTable;

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

        try {
            createTimeTable();
            processTimeTable();
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }

    protected abstract void process(final String _name) throws ApiFacade.FatalException, ApiFacade.RecoverableException;

    private void createTimeTable() throws ApiFacade.FatalException {
        this.timeTable = new TimeTable(this.facade.getTimeService());

        for (String name : this.list) {
            this.timeTable.add(name, this.facade.getUpdateAttempts(), this.facade.getUpdateTimeout());
        }
    }

    private void processTimeTable() throws CommandExecutionException {
        long timeout;

        while ((timeout = this.timeTable.getTimeout()) != -1) {

            if (timeout != 0) {
                sleep(timeout);
            } else {
                String name = this.timeTable.getCurrentId();

                if (name.length() != 0) {
                    handle(name);
                }
            }
        }
    }

    private void handle(final String _name) {

        try {
            process(_name);
            this.timeTable.remove(_name);
        } catch (ApiFacade.FatalException e) {
            this.timeTable.remove(_name);
            this.log.info("Item [ " + _name + " ] removed from timetable. Cause : ", e);
        } catch (ApiFacade.RecoverableException e) {
            //TODO remove logic from catch block
            int remain = this.timeTable.touch(_name);

            if (remain == 0) {
                this.timeTable.remove(_name);
                this.log.info("Item [ " + _name + " ] removed from timetable. No attempts left.", e);
            } else {
                this.log.info("Item [ " + _name + " ] waiting for another attempt. Cause : ", e);
            }
        }
    }

    private void sleep(final long _millis) throws CommandExecutionException {

        try {
            Thread.sleep(_millis);
        } catch (InterruptedException e) {
            throw new CommandExecutionException("Command interrupted.");
        }
    }
}
