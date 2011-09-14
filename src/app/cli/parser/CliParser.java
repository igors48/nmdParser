package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import static app.cli.parser.OptionNameTable.*;
import org.apache.commons.cli.*;
import util.Assert;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * ������ ��� ��������� ������. ������ - �� ��������� ������
 * ��������� �����������(��) �������(�) � ��������, ���� �������
 * ��������
 *
 * @author Igor Usenko
 *         Date: 14.04.2009
 */
public class CliParser {

    private final ApiFacade api;
    private final OutputStream console;

    private Map<String, OptionParser> parsers;
    private Options options;

    private static final int MAXIMUM_ARGS = 48;
    private static final int FORCED_MODE_MAX_ARGS = 1;
    private static final int ONE_ARG = FORCED_MODE_MAX_ARGS;
    private static final int PROPERTIES_ARGS_COUNT = 2;
    private static final int TEMPLATE_CREATION_MAX_ARGS = PROPERTIES_ARGS_COUNT;
    private static final String APP_EXEC_FILE_NAME = "nmd";
    private static final int SCREEN_WIDTH = 80;

    public CliParser(final OutputStream _console, final ApiFacade _api) {
        Assert.notNull(_console, "Console is null.");
        this.console = _console;

        Assert.notNull(_api, "Api is null.");
        this.api = _api;

        createOptions();
        createParsersMap();
    }

    public CliParserResult parse(final String[] _args) throws CliParserException {
        Assert.notNull(_args, "Arguments list is null.");

        try {
            List<Command> script = new ArrayList<Command>();
            Properties properties = null;

            CommandLineParser parser = new GnuParser();
            CommandLine commandLine = parser.parse(this.options, _args);
            OptionParser optionParser = getParser(commandLine);

            if (optionParser == null) {
                printUsage();
            } else {
                properties = commandLine.getOptionProperties(PROPERTIES_OPTION_SHORT_NAME);
                properties = properties == null ? new Properties() : properties;

                script = optionParser.parse(commandLine, this.api, OptionParserUtils.convert(properties));
            }

            return new CliParserResult(script);
        } catch (ParseException e) {
            printUsage();

            throw new CliParserException(e);
        }
    }

    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter writer = new PrintWriter(this.console);

        formatter.printUsage(writer, SCREEN_WIDTH, APP_EXEC_FILE_NAME, options);
        formatter.printHelp(writer, SCREEN_WIDTH, " ", "", options, 0, 0, "");

        writer.flush();
        writer.close();
    }

    private void createParsersMap() {
        this.parsers = new HashMap<String, OptionParser>();

        this.parsers.put(CREATE_WORKSPACE_OPTION_SHORT_NAME, new CreateWorkspaceOptionParser());
        this.parsers.put(UPDATE_SOURCES_OPTION_SHORT_NAME, new UpdateSourcesOptionParser());
        this.parsers.put(UPDATE_ALL_SOURCES_OPTION_SHORT_NAME, new UpdateAllSourcesOptionParser());
        this.parsers.put(UPDATE_CHANNELS_OPTION_SHORT_NAME, new UpdateChannelsOptionParser());
        this.parsers.put(UPDATE_ALL_CHANNELS_OPTION_SHORT_NAME, new UpdateAllChannelsOptionParser());
        this.parsers.put(UPDATE_OUTPUTS_OPTION_SHORT_NAME, new UpdateOutputsOptionParser());
        this.parsers.put(UPDATE_ALL_OUTPUTS_OPTION_SHORT_NAME, new UpdateAllOutputsOptionParser());
        this.parsers.put(UPDATE_ALL_OPTION_SHORT_NAME, new UpdateAllOptionParser());
        this.parsers.put(CREATE_FULL_TEXT_TEMPLATES_OPTION_SHORT_NAME, new CreateFullTextTemplatesOptionParser());
        this.parsers.put(CREATE_BRIEF_TEXT_TEMPLATES_OPTION_SHORT_NAME, new CreateBriefTextTemplatesOptionParser());
        this.parsers.put(BLITZ_PAGE_REQUEST_OPTION_SHORT_NAME, new BlitzUrlRequestOptionParser());
        this.parsers.put(BLITZ_FEED_REQUEST_OPTION_SHORT_NAME, new BlitzFeedRequestOptionParser());
        this.parsers.put(REMOVE_SERVICE_FILES_OPTION_SHORT_NAME, new RemoveServiceFilesOptionParser());
        this.parsers.put(PROCESS_SNIPPET_OPTION_SHORT_NAME, new ProcessSnippetsOptionParser());
        this.parsers.put(GOOGLE_READER_CREATE_PROFILE_OPTION_SHORT_NAME, new GoogleReaderCreateProfileOptionParser());
        this.parsers.put(GOOGLE_READER_UPDATE_PROFILE_OPTION_SHORT_NAME, new GoogleReaderUpdateProfileOptionParser());
        this.parsers.put(GOOGLE_READER_TEST_PROFILE_FEED_OPTION_SHORT_NAME, new GoogleReaderTestProfileFeedOptionParser());
    }

    private OptionParser getParser(final CommandLine _commandLine) {
        OptionParser result = null;

        for (String option : this.parsers.keySet()) {

            if (_commandLine.hasOption(option)) {
                result = this.parsers.get(option);
                break;
            }
        }

        return result;
    }

    // todo !!! �������� !!!
    private void createOptions() {
        this.options = new Options();

        Option forcedModeSelect = new Option(FORCED_MODE_OPTION_SHORT_NAME,
                FORCED_MODE_OPTION_FULL_NAME,
                true,
                FORCED_MODE_OPTION_DESCRIPTION);
        forcedModeSelect.setArgName(FORCED_MODE_OPTION_ATTRIBUTE_NAME);
        forcedModeSelect.setOptionalArg(true);
        forcedModeSelect.setArgs(FORCED_MODE_MAX_ARGS);

        Option debugModeSelect = new Option(DEBUG_MODE_OPTION_SHORT_NAME,
                DEBUG_MODE_OPTION_FULL_NAME,
                false,
                DEBUG_MODE_OPTION_DESCRIPTION);

        Option workspaceSelect = new Option(WORKSPACE_OPTION_SHORT_NAME,
                WORKSPACE_OPTION_FULL_NAME,
                true,
                WORKSPACE_OPTION_DESCRIPTION);
        workspaceSelect.setArgName(WORKSPACE_OPTION_ATTRIBUTE_NAME);

        Option settingsSelect = new Option(SETTINGS_OPTION_SHORT_NAME,
                SETTINGS_OPTION_FULL_NAME,
                true,
                SETTINGS_OPTION_DESCRIPTION);
        settingsSelect.setArgName(SETTINGS_OPTION_ATTRIBUTE_NAME);

        OptionGroup mainGroup = new OptionGroup();

        Option createWorkspaceOption = new Option(CREATE_WORKSPACE_OPTION_SHORT_NAME,
                CREATE_WORKSPACE_OPTION_FULL_NAME,
                true,
                CREATE_WORKSPACE_OPTION_DESCRIPTION);
        createWorkspaceOption.setArgName(CREATE_WORKSPACE_OPTION_ATTRIBUTE_NAME);
        createWorkspaceOption.setRequired(true);

        Option updateSourcesOption = new Option(UPDATE_SOURCES_OPTION_SHORT_NAME,
                UPDATE_SOURCES_OPTION_FULL_NAME,
                true,
                UPDATE_SOURCES_OPTION_DESCRIPTION);
        updateSourcesOption.setArgName(UPDATE_SOURCES_OPTION_ATTRIBUTE_NAME);
        updateSourcesOption.setRequired(true);
        updateSourcesOption.setArgs(MAXIMUM_ARGS);

        Option updateAllSourcesOption = new Option(UPDATE_ALL_SOURCES_OPTION_SHORT_NAME,
                UPDATE_ALL_SOURCES_OPTION_FULL_NAME,
                false,
                UPDATE_ALL_SOURCES_OPTION_DESCRIPTION);
        updateAllSourcesOption.setArgName(UPDATE_ALL_SOURCES_OPTION_ATTRIBUTE_NAME);
        updateAllSourcesOption.setRequired(true);
        updateAllSourcesOption.setOptionalArg(true);
        updateAllSourcesOption.setArgs(MAXIMUM_ARGS);

        Option updateChannelsOption = new Option(UPDATE_CHANNELS_OPTION_SHORT_NAME,
                UPDATE_CHANNELS_OPTION_FULL_NAME,
                true,
                UPDATE_CHANNELS_OPTION_DESCRIPTION);
        updateChannelsOption.setArgName(UPDATE_CHANNELS_OPTION_ATTRIBUTE_NAME);
        updateChannelsOption.setRequired(true);
        updateChannelsOption.setArgs(MAXIMUM_ARGS);

        Option updateAllChannelsOption = new Option(UPDATE_ALL_CHANNELS_OPTION_SHORT_NAME,
                UPDATE_ALL_CHANNELS_OPTION_FULL_NAME,
                false,
                UPDATE_ALL_CHANNELS_OPTION_DESCRIPTION);
        updateAllChannelsOption.setArgName(UPDATE_ALL_CHANNELS_OPTION_ATTRIBUTE_NAME);
        updateAllChannelsOption.setRequired(true);
        updateAllChannelsOption.setOptionalArg(true);
        updateAllChannelsOption.setArgs(MAXIMUM_ARGS);

        Option updateOutputsOption = new Option(UPDATE_OUTPUTS_OPTION_SHORT_NAME,
                UPDATE_OUTPUTS_OPTION_FULL_NAME,
                true,
                UPDATE_OUTPUTS_OPTION_DESCRIPTION);
        updateOutputsOption.setArgName(UPDATE_OUTPUTS_OPTION_ATTRIBUTE_NAME);
        updateOutputsOption.setRequired(true);
        updateOutputsOption.setArgs(MAXIMUM_ARGS);

        Option updateAllOutputsOption = new Option(UPDATE_ALL_OUTPUTS_OPTION_SHORT_NAME,
                UPDATE_ALL_OUTPUTS_OPTION_FULL_NAME,
                false,
                UPDATE_ALL_OUTPUTS_OPTION_DESCRIPTION);
        updateAllOutputsOption.setArgName(UPDATE_ALL_OUTPUTS_OPTION_ATTRIBUTE_NAME);
        updateAllOutputsOption.setRequired(true);
        updateAllOutputsOption.setOptionalArg(true);
        updateAllOutputsOption.setArgs(MAXIMUM_ARGS);

        Option updateAllOption = new Option(UPDATE_ALL_OPTION_SHORT_NAME,
                UPDATE_ALL_OPTION_FULL_NAME,
                false,
                UPDATE_ALL_OPTION_DESCRIPTION);
        updateAllOption.setArgName(UPDATE_ALL_OPTION_ATTRIBUTE_NAME);
        updateAllOption.setRequired(true);
        updateAllOption.setOptionalArg(true);
        updateAllOption.setArgs(MAXIMUM_ARGS);

        Option createFullTextTemplatesOption = new Option(CREATE_FULL_TEXT_TEMPLATES_OPTION_SHORT_NAME,
                CREATE_FULL_TEXT_TEMPLATES_OPTION_FULL_NAME,
                false,
                CREATE_FULL_TEXT_TEMPLATES_OPTION_DESCRIPTION);
        createFullTextTemplatesOption.setArgName(CREATE_FULL_TEXT_TEMPLATES_OPTION_ATTRIBUTE_NAME);
        createFullTextTemplatesOption.setRequired(true);
        createFullTextTemplatesOption.setOptionalArg(false);
        createFullTextTemplatesOption.setArgs(TEMPLATE_CREATION_MAX_ARGS);

        Option createBriefTextTemplatesOption = new Option(CREATE_BRIEF_TEXT_TEMPLATES_OPTION_SHORT_NAME,
                CREATE_BRIEF_TEXT_TEMPLATES_OPTION_FULL_NAME,
                false,
                CREATE_BRIEF_TEXT_TEMPLATES_OPTION_DESCRIPTION);
        createBriefTextTemplatesOption.setArgName(CREATE_BRIEF_TEXT_TEMPLATES_OPTION_ATTRIBUTE_NAME);
        createBriefTextTemplatesOption.setRequired(true);
        createBriefTextTemplatesOption.setOptionalArg(false);
        createBriefTextTemplatesOption.setArgs(TEMPLATE_CREATION_MAX_ARGS);

        Option processSnippetsOption = new Option(PROCESS_SNIPPET_OPTION_SHORT_NAME,
                PROCESS_SNIPPET_OPTION_FULL_NAME,
                true,
                PROCESS_SNIPPET_OPTION_DESCRIPTION);
        processSnippetsOption.setArgName(PROCESS_SNIPPET_OPTION_ATTRIBUTE_NAME);
        processSnippetsOption.setRequired(true);
        processSnippetsOption.setArgs(MAXIMUM_ARGS);

        Option blitzPageRequestOption = new Option(BLITZ_PAGE_REQUEST_OPTION_SHORT_NAME,
                BLITZ_PAGE_REQUEST_OPTION_FULL_NAME,
                false,
                BLITZ_PAGE_REQUEST_OPTION_DESCRIPTION);
        blitzPageRequestOption.setArgName(BLITZ_PAGE_REQUEST_OPTION_ATTRIBUTE_NAME);
        blitzPageRequestOption.setRequired(true);
        blitzPageRequestOption.setOptionalArg(false);
        blitzPageRequestOption.setArgs(MAXIMUM_ARGS);

        Option blitzFeedRequestOption = new Option(BLITZ_FEED_REQUEST_OPTION_SHORT_NAME,
                BLITZ_FEED_REQUEST_OPTION_FULL_NAME,
                false,
                BLITZ_FEED_REQUEST_OPTION_DESCRIPTION);
        blitzFeedRequestOption.setArgName(BLITZ_FEED_REQUEST_OPTION_ATTRIBUTE_NAME);
        blitzFeedRequestOption.setRequired(true);
        blitzFeedRequestOption.setOptionalArg(false);
        blitzFeedRequestOption.setArgs(ONE_ARG);

        Option googleReaderCreateProfileOption = new Option(GOOGLE_READER_CREATE_PROFILE_OPTION_SHORT_NAME,
                GOOGLE_READER_CREATE_PROFILE_OPTION_FULL_NAME,
                false,
                GOOGLE_READER_CREATE_PROFILE_OPTION_DESCRIPTION);
        googleReaderCreateProfileOption.setArgName(GOOGLE_READER_CREATE_PROFILE_OPTION_ATTRIBUTE_NAME);
        googleReaderCreateProfileOption.setRequired(true);
        googleReaderCreateProfileOption.setOptionalArg(false);
        googleReaderCreateProfileOption.setArgs(2);

        Option googleReaderUpdateProfileOption = new Option(GOOGLE_READER_UPDATE_PROFILE_OPTION_SHORT_NAME,
                GOOGLE_READER_UPDATE_PROFILE_OPTION_FULL_NAME,
                false,
                GOOGLE_READER_UPDATE_PROFILE_OPTION_DESCRIPTION);
        googleReaderUpdateProfileOption.setArgName(GOOGLE_READER_UPDATE_PROFILE_OPTION_ATTRIBUTE_NAME);
        googleReaderUpdateProfileOption.setRequired(true);
        googleReaderUpdateProfileOption.setOptionalArg(false);
        googleReaderUpdateProfileOption.setArgs(1);

        Option googleReaderTestProfileFeedOption = new Option(GOOGLE_READER_TEST_PROFILE_FEED_OPTION_SHORT_NAME,
                GOOGLE_READER_TEST_PROFILE_FEED_OPTION_FULL_NAME,
                false,
                GOOGLE_READER_TEST_PROFILE_FEED_OPTION_DESCRIPTION);
        googleReaderTestProfileFeedOption.setArgName(GOOGLE_READER_TEST_PROFILE_FEED_OPTION_ATTRIBUTE_NAME);
        googleReaderTestProfileFeedOption.setRequired(true);
        googleReaderTestProfileFeedOption.setOptionalArg(false);
        googleReaderTestProfileFeedOption.setArgs(2);

        Option baseUrlOption = new Option(BASE_URL_OPTION_SHORT_NAME,
                BASE_URL_OPTION_FULL_NAME,
                true,
                BASE_URL_OPTION_DESCRIPTION);
        baseUrlOption.setArgName(BASE_URL_OPTION_ATTRIBUTE_NAME);

        Option xPathOption = new Option(XPATH_OPTION_SHORT_NAME,
                XPATH_OPTION_FULL_NAME,
                true,
                XPATH_OPTION_DESCRIPTION);
        xPathOption.setArgName(XPATH_OPTION_ATTRIBUTE_NAME);

        Option regExpOption = new Option(REG_EXP_OPTION_SHORT_NAME,
                REG_EXP_OPTION_FULL_NAME,
                true,
                REG_EXP_OPTION_DESCRIPTION);
        regExpOption.setArgName(REG_EXP_OPTION_ATTRIBUTE_NAME);

        Option mtoOption = new Option(MTO_OPTION_SHORT_NAME,
                MTO_OPTION_FULL_NAME,
                true,
                MTO_OPTION_DESCRIPTION);
        mtoOption.setArgName(MTO_OPTION_ATTRIBUTE_NAME);

        Option branchOption = new Option(BRANCH_OPTION_SHORT_NAME,
                BRANCH_OPTION_FULL_NAME,
                true,
                BRANCH_OPTION_DESCRIPTION);
        branchOption.setArgName(BRANCH_OPTION_ATTRIBUTE_NAME);

        Option storageOption = new Option(STORAGE_OPTION_SHORT_NAME,
                STORAGE_OPTION_FULL_NAME,
                true,
                STORAGE_OPTION_DESCRIPTION);
        storageOption.setArgName(STORAGE_OPTION_ATTRIBUTE_NAME);

        Option noLinksAsFootNotesOption = new Option(NO_LINKS_AS_FOOTNOTES_MODE_OPTION_SHORT_NAME,
                NO_LINKS_AS_FOOTNOTES_MODE_OPTION_FULL_NAME,
                false,
                NO_LINKS_AS_FOOTNOTES_MODE_OPTION_DESCRIPTION);

        Option noResolveImageLinksOption = new Option(NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_SHORT_NAME,
                NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_FULL_NAME,
                false,
                NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_DESCRIPTION);

        Option removeExistentFileOption = new Option(REMOVE_EXISTENT_FILE_MODE_OPTION_SHORT_NAME,
                REMOVE_EXISTENT_FILE_MODE_OPTION_FULL_NAME,
                false,
                REMOVE_EXISTENT_FILE_MODE_OPTION_DESCRIPTION);

        Option properties = OptionBuilder.withArgName(PROPERTIES_OPTION_ATTRIBUTE_NAME)
                .hasArgs(PROPERTIES_ARGS_COUNT)
                .withValueSeparator()
                .withDescription(PROPERTIES_OPTION_DESCRIPTION)
                .create(PROPERTIES_OPTION_SHORT_NAME);

        Option removeServiceFilesOption = new Option(REMOVE_SERVICE_FILES_OPTION_SHORT_NAME,
                REMOVE_SERVICE_FILES_OPTION_FULL_NAME,
                false,
                REMOVE_SERVICE_FILES_OPTION_DESCRIPTION);
        removeServiceFilesOption.setArgName(REMOVE_SERVICE_FILES_OPTION_ATTRIBUTE_NAME);
        removeServiceFilesOption.setRequired(true);
        removeServiceFilesOption.setOptionalArg(true);
        removeServiceFilesOption.setArgs(MAXIMUM_ARGS);

        mainGroup.addOption(createWorkspaceOption);
        mainGroup.addOption(updateSourcesOption);
        mainGroup.addOption(updateAllSourcesOption);
        mainGroup.addOption(updateChannelsOption);
        mainGroup.addOption(updateAllChannelsOption);
        mainGroup.addOption(updateOutputsOption);
        mainGroup.addOption(updateAllOutputsOption);
        mainGroup.addOption(updateAllOption);
        mainGroup.addOption(createFullTextTemplatesOption);
        mainGroup.addOption(createBriefTextTemplatesOption);
        mainGroup.addOption(processSnippetsOption);
        mainGroup.addOption(blitzPageRequestOption);
        mainGroup.addOption(blitzFeedRequestOption);
        mainGroup.addOption(removeServiceFilesOption);
        mainGroup.addOption(googleReaderCreateProfileOption);
        mainGroup.addOption(googleReaderUpdateProfileOption);
        mainGroup.addOption(googleReaderTestProfileFeedOption);

        this.options.addOptionGroup(mainGroup);

        this.options.addOption(workspaceSelect);
        this.options.addOption(settingsSelect);
        this.options.addOption(forcedModeSelect);
        this.options.addOption(debugModeSelect);
        this.options.addOption(properties);

        this.options.addOption(noLinksAsFootNotesOption);
        this.options.addOption(noResolveImageLinksOption);
        this.options.addOption(removeExistentFileOption);

        this.options.addOption(baseUrlOption);
        this.options.addOption(xPathOption);
        this.options.addOption(regExpOption);
        this.options.addOption(mtoOption);
        this.options.addOption(branchOption);
        this.options.addOption(storageOption);
    }

    public static class CliParserException extends Exception {

        public CliParserException() {
            super();
        }

        public CliParserException(final String _s) {
            super(_s);
        }

        public CliParserException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public CliParserException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
