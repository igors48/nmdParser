package app.cli.parser;

import app.cli.command.Command;
import util.Assert;

import java.util.List;

/**
 * Контейнер с результатами разбора командной строки
 *
 * @author Igor Usenko
 *         Date: 08.10.2009
 */
public class Script {

    private final List<Command> commands;

    public Script(final List<Command> _script) {
        Assert.notNull(_script, "Script is null");
        this.commands = _script;
    }

    public List<Command> getCommands() {
        return this.commands;
    }
    
}
