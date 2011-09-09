package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.*;
import static app.cli.parser.OptionNameTable.WORKSPACE_OPTION_SHORT_NAME;
import org.apache.commons.cli.CommandLine;
import util.Assert;
import static util.CollectionUtils.newArrayList;

import java.util.List;
import java.util.Map;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderUpdateProfileOptionParser implements OptionParser {

    public List<Command> parse(final CommandLine _commandLine, final ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = newArrayList();

        String settingsName = _api.getDefaultSettingsName();

        if (_commandLine.hasOption(OptionNameTable.SETTINGS_OPTION_SHORT_NAME)) {
            settingsName = _commandLine.getOptionValue(OptionNameTable.SETTINGS_OPTION_SHORT_NAME);
        }

        String workspaceName = _api.getDefaultWorkspaceName();

        if (_commandLine.hasOption(WORKSPACE_OPTION_SHORT_NAME)) {
            workspaceName = _commandLine.getOptionValue(WORKSPACE_OPTION_SHORT_NAME);
        }
        
        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));

        List<String> cliParms = OptionParserUtils.getOptionsList(_commandLine, OptionNameTable.GOOGLE_READER_UPDATE_PROFILE_OPTION_SHORT_NAME);

        result.add(new GoogleReaderUpdateProfileCommand(cliParms.get(0), _api));

        return result;
    }
}