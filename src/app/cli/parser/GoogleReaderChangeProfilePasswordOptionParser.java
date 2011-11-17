package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import app.cli.command.GoogleReaderChangeProfilePasswordCommand;
import app.cli.command.LoadSettingsCommand;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderChangeProfilePasswordOptionParser implements OptionParser {

    public List<Command> parse(final CommandLine _commandLine, final ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = newArrayList();

        String settingsName = _api.getDefaultSettingsName();

        if (_commandLine.hasOption(OptionNameTable.SETTINGS_OPTION_SHORT_NAME)) {
            settingsName = _commandLine.getOptionValue(OptionNameTable.SETTINGS_OPTION_SHORT_NAME);
        }

        result.add(new LoadSettingsCommand(settingsName, _api));

        List<String> cliParms = OptionParserUtils.getOptionsList(_commandLine, OptionNameTable.GOOGLE_READER_CHANGE_PROFILE_PASSWORD_OPTION_SHORT_NAME);

        result.add(new GoogleReaderChangeProfilePasswordCommand(cliParms.get(0), cliParms.get(1), _api));

        return result;
    }
}