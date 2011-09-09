package app.cli.command;

import util.Assert;

import java.util.List;

/**
 * Скрипт - последовательность выполняемых команд
 *
 * @author Igor Usenko
 *         Date: 15.04.2009
 */
public class Script implements Command {

    private final List<Command> commands;

    public Script(final List<Command> _commands) {
        Assert.notNull(_commands, "Command list is null.");
        this.commands = _commands;
    }

    public void execute() throws CommandExecutionException {

        for (Command command : this.commands) {
            command.execute();
        }
    }
}
