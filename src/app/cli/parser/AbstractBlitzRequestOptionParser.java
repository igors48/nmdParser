package app.cli.parser;

import app.api.ApiFacade;
import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.CriterionType;
import app.cli.command.BlitzRequestCommand;
import app.cli.command.Command;
import app.cli.command.LoadSettingsCommand;
import app.cli.command.LoadWorkspaceCommand;
import static app.cli.parser.OptionNameTable.FORCED_MODE_OPTION_SHORT_NAME;
import constructor.objects.output.configuration.Composition;
import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный парсер блиц-запросов
 *
 * @author Igor Usenko
 *         Date: 01.11.2009
 */
public abstract class AbstractBlitzRequestOptionParser implements OptionParser {

    public List<Command> parse(final CommandLine _commandLine, final ApiFacade _api, final Map<String, String> _context) {
        Assert.notNull(_commandLine, "Command line is null.");
        Assert.notNull(_api, "Api is null.");
        Assert.notNull(_context, "Context is null.");

        List<Command> result = new ArrayList<Command>();

        String settingsName = _api.getDefaultSettingsName();

        if (_commandLine.hasOption(app.cli.parser.OptionNameTable.SETTINGS_OPTION_SHORT_NAME)) {
            settingsName = _commandLine.getOptionValue(app.cli.parser.OptionNameTable.SETTINGS_OPTION_SHORT_NAME);
        }

        String workspaceName = _api.getDefaultWorkspaceName();

        if (_commandLine.hasOption(app.cli.parser.OptionNameTable.WORKSPACE_OPTION_SHORT_NAME)) {
            workspaceName = _commandLine.getOptionValue(app.cli.parser.OptionNameTable.WORKSPACE_OPTION_SHORT_NAME);
        }

        int forcedDays = 0;

        if (_commandLine.hasOption(FORCED_MODE_OPTION_SHORT_NAME)) {
            forcedDays = OptionParserUtils.parseForcedDays(_commandLine);
        }

        result.add(new LoadSettingsCommand(settingsName, _api));
        result.add(new LoadWorkspaceCommand(workspaceName, _api));

        BlitzRequest request = createRequest(_commandLine);
        processMiscOptions(_commandLine, request);
        result.add(new BlitzRequestCommand(request, forcedDays, _api));

        return result;
    }

    protected abstract BlitzRequest createRequest(final CommandLine _commandLine);

    protected void processMiscOptions(final CommandLine _commandLine, final BlitzRequest _request) {

        if (_commandLine.hasOption(OptionNameTable.XPATH_OPTION_SHORT_NAME)) {
            _request.setCriterionType(CriterionType.XPATH);
            _request.setCriterionExpression(_commandLine.getOptionValue(OptionNameTable.XPATH_OPTION_SHORT_NAME));
        }

        if (_commandLine.hasOption(OptionNameTable.REG_EXP_OPTION_SHORT_NAME)) {
            _request.setCriterionType(CriterionType.REGEXP);
            _request.setCriterionExpression(_commandLine.getOptionValue(OptionNameTable.REG_EXP_OPTION_SHORT_NAME));
        }

        if (_commandLine.hasOption(OptionNameTable.MTO_OPTION_SHORT_NAME)) {
            _request.setComposition(Composition.MANY_TO_ONE);
            _request.setOutName(_commandLine.getOptionValue(OptionNameTable.MTO_OPTION_SHORT_NAME));
        }

        if (_commandLine.hasOption(OptionNameTable.BRANCH_OPTION_SHORT_NAME)) {
            _request.setBranch(_commandLine.getOptionValue(OptionNameTable.BRANCH_OPTION_SHORT_NAME));
        }

        if (_commandLine.hasOption(OptionNameTable.STORAGE_OPTION_SHORT_NAME)) {
            _request.setStorage(_commandLine.getOptionValue(OptionNameTable.STORAGE_OPTION_SHORT_NAME));
        }

        if (_commandLine.hasOption(OptionNameTable.NO_LINKS_AS_FOOTNOTES_MODE_OPTION_SHORT_NAME)) {
            _request.setLinksAsFootnotes(false);
        }

        if (_commandLine.hasOption(OptionNameTable.NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_SHORT_NAME)) {
            _request.setResolveImageLinks(false);
        }

        if (_commandLine.hasOption(OptionNameTable.REMOVE_EXISTENT_FILE_MODE_OPTION_SHORT_NAME)) {
            _request.setRemoveExists(true);
        }
    }

}
