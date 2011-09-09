package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import app.cli.command.CreateWorkspaceCommand;
import app.cli.command.LoadSettingsCommand;
import static app.cli.parser.OptionNameTable.CREATE_WORKSPACE_OPTION_SHORT_NAME;
import static app.cli.parser.OptionNameTable.SETTINGS_OPTION_SHORT_NAME;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Парсер опции "создать рабочее пространство"
 *
 * @author Igor Usenko
 *         Date: 20.04.2009
 */
public class CreateWorkspaceOptionParser implements OptionParser {

    public List<Command> parse(final CommandLine _commandLine, final ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = new ArrayList<Command>();

        String settingsName = _api.getDefaultSettingsName();

        if (_commandLine.hasOption(SETTINGS_OPTION_SHORT_NAME)) {
            settingsName = _commandLine.getOptionValue(SETTINGS_OPTION_SHORT_NAME);
        }

        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new CreateWorkspaceCommand(_commandLine.getOptionValue(CREATE_WORKSPACE_OPTION_SHORT_NAME), _api));

        return result;
    }
}
