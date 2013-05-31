package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.*;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static app.cli.parser.OptionNameTable.*;
import static util.CollectionUtils.newArrayList;

/**
 * Парсер опции "обновить список источников"
 *
 * @author Igor Usenko
 *         Date: 20.04.2009
 */
public class UpdateSourcesOptionParser implements OptionParser {

    public List<Command> parse(final CommandLine _commandLine, final ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = newArrayList();

        String settingsName = _api.getDefaultSettingsName();

        if (_commandLine.hasOption(SETTINGS_OPTION_SHORT_NAME)) {
            settingsName = _commandLine.getOptionValue(SETTINGS_OPTION_SHORT_NAME);
        }

        String workspaceName = _api.getDefaultWorkspaceName();

        if (_commandLine.hasOption(WORKSPACE_OPTION_SHORT_NAME)) {
            workspaceName = _commandLine.getOptionValue(WORKSPACE_OPTION_SHORT_NAME);
        }

        boolean debugMode = false;

        if (_commandLine.hasOption(DEBUG_MODE_OPTION_SHORT_NAME)) {
            debugMode = true;
        }

        List<String> names = Arrays.asList(_commandLine.getOptionValues(UPDATE_SOURCES_OPTION_SHORT_NAME));

        if (debugMode) {
            result.add(new EnableDebugConsoleCommand(_api));
        }
        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));
        result.add(new UpdateSourcesCommand(names, _api, _context));

        return result;
    }
}
