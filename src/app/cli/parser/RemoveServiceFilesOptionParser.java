package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import app.cli.command.LoadSettingsCommand;
import app.cli.command.LoadWorkspaceCommand;
import app.cli.command.RemoveServiceFilesCommand;
import constructor.dom.locator.Mask;
import constructor.dom.locator.MaskUtils;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static app.cli.parser.OptionNameTable.*;

/**
 * Парсер опции "удалить служебные файлы настроек"
 *
 * @author Igor Usenko
 *         Date: 16.11.2009
 */
public class RemoveServiceFilesOptionParser implements OptionParser {

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

        List<String> masks = OptionParserUtils.getOptionsList(_commandLine, REMOVE_SERVICE_FILES_OPTION_SHORT_NAME);
        Mask mask = MaskUtils.createMask(masks);

        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));
        result.add(new RemoveServiceFilesCommand(mask, _api));

        return result;
    }

}