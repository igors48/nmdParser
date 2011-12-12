package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.*;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static app.cli.parser.OptionNameTable.*;
import static util.CollectionUtils.newArrayList;

/**
 * ������ ����� ��������� ���������
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public class ProcessSnippetsOptionParser implements OptionParser {

    public List<Command> parse(CommandLine _commandLine, ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = newArrayList();

        String settingsName = _api.getDefaultSettingsName();

        //todo �������� �� �������� ����� � � ��������
        //todo � ������ - ����� ������� ����� ������� �������?
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

        List<String> names = Arrays.asList(_commandLine.getOptionValues(PROCESS_SNIPPET_OPTION_SHORT_NAME));

        if (debugMode) {
            result.add(new EnableDebugConsoleCommand(_api));
        }
        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));
        result.add(new ProcessSnippetsCommand(names, forcedDays, _api, _context));

        return result;
    }
}
