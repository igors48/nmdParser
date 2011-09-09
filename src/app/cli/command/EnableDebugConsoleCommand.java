package app.cli.command;

import app.api.ApiFacade;
import util.Assert;

/**
 *  оманда разрешени€ отладочной консоли
 *
 * @author Igor Usenko
 *         Date: 29.08.2009
 */
public class EnableDebugConsoleCommand implements Command {

    private final ApiFacade facade;

    public EnableDebugConsoleCommand(final ApiFacade _facade) {
        Assert.notNull(_facade, "Api facade is null.");

        this.facade = _facade;
    }

    public void execute() throws CommandExecutionException {
        this.facade.enableDebugConsole();
    }
}
