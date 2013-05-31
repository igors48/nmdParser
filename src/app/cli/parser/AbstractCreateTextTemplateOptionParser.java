package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import app.cli.command.CreateTemplatesCommand;
import app.cli.command.LoadSettingsCommand;
import app.cli.command.LoadWorkspaceCommand;
import app.templater.TemplateParameters;
import app.templater.TemplateType;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.List;
import java.util.Map;

import static app.cli.parser.OptionNameTable.SETTINGS_OPTION_SHORT_NAME;
import static app.cli.parser.OptionNameTable.WORKSPACE_OPTION_SHORT_NAME;
import static util.CollectionUtils.newArrayList;

/**
 * Абстрактный парсер опции создания шаблонов для обработки фидов
 *
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public abstract class AbstractCreateTextTemplateOptionParser implements OptionParser {

    private static final int NAME_INDEX = 0;
    private static final int RSS_URL_INDEX = 1;

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

        List<String> cliParms = OptionParserUtils.getOptionsList(_commandLine, getOptionsName());
        TemplateParameters parameters = new TemplateParameters(getType(), cliParms.get(NAME_INDEX), cliParms.get(RSS_URL_INDEX), workspaceName);
        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));
        result.add(new CreateTemplatesCommand(parameters, _api));

        return result;
    }

    protected abstract TemplateType getType();

    protected abstract String getOptionsName();
}
