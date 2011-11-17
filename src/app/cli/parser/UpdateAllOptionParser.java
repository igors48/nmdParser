package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.*;
import constructor.dom.locator.Mask;
import constructor.dom.locator.MaskUtils;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static app.cli.parser.OptionNameTable.*;

/**
 * Парсер опции "обновить все"
 *
 * @author Igor Usenko
 *         Date: 01.05.2009
 */
public class UpdateAllOptionParser implements OptionParser {

    public List<Command> parse(final CommandLine _commandLine, final ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = new ArrayList<Command>();

        String settingsName = _api.getDefaultSettingsName();

        if (_commandLine.hasOption(SETTINGS_OPTION_SHORT_NAME)) {
            settingsName = _commandLine.getOptionValue(SETTINGS_OPTION_SHORT_NAME);
        }

        String workspaceName = _api.getDefaultWorkspaceName();

        if (_commandLine.hasOption(WORKSPACE_OPTION_SHORT_NAME)) {
            workspaceName = _commandLine.getOptionValue(WORKSPACE_OPTION_SHORT_NAME);
        }

        int forcedDays = -1;

        if (_commandLine.hasOption(FORCED_MODE_OPTION_SHORT_NAME)) {
            forcedDays = OptionParserUtils.parseForcedDays(_commandLine);
        }

        boolean debugMode = false;

        if (_commandLine.hasOption(DEBUG_MODE_OPTION_SHORT_NAME)) {
            debugMode = true;
        }

        List<String> masks = OptionParserUtils.getOptionsList(_commandLine, UPDATE_ALL_OPTION_SHORT_NAME);
        Mask mask = MaskUtils.createMask(masks);

        if (debugMode) {
            result.add(new EnableDebugConsoleCommand(_api));
        }

        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));
        result.add(new UpdateAllSourcesCommand(mask, _api, _context));
        result.add(new UpdateAllChannelsCommand(mask, forcedDays, _api, _context));
        result.add(new UpdateAllOutputsCommand(mask, forcedDays, _api, _context));

        return result;
    }
}