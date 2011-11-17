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
public class CliParserResult {

    private final List<Command> script;

    public CliParserResult(final List<Command> _script) {
        Assert.notNull(_script, "Script is null");
        this.script = _script;
    }

    public List<Command> getScript() {
        return this.script;
    }
}
