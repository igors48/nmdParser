package app;

import app.api.ApiFacade;
import app.api.NmdApi;
import app.cli.command.Command;
import app.cli.parser.CliParser;
import app.cli.parser.CliParserResult;
import app.workingarea.Defaults;
import app.workingarea.SettingsManager;
import app.workingarea.WorkspaceManager;
import app.workingarea.defaults.FileDefaults;
import app.workingarea.settings.FileSettingsManager;
import app.workingarea.workspace.WorkspaceDirectoriesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.PropertiesUtil;
import util.TimeTools;

import java.util.Arrays;
import java.util.List;

/**
 * !!!
 * <p/>
 * First start date 18 apr 2009 at 16:15
 *
 * @author Igor Usenko
 *         Date: 16.04.2009
 */
public class NmdCliApplication {

    private static final String DEFAULTS_PROPERTIES_FILE_NAME = "./etc/defaults.properties";

    private Defaults defaults;

    private WorkspaceManager workspaceManager;
    private SettingsManager settingsManager;

    private CliParser cliParser;

    private VersionInfo versionInfo;

    private ApiFacade api;

    private long startTime;

    private boolean cleaned;

    private static final Log log = LogFactory.getLog(NmdCliApplication.class);

    public static void main(final String[] _args) {
        NmdCliApplication application = null;

        try {
            log.debug("Command line argument(s) " + Arrays.toString(_args));
            application = new NmdCliApplication();

            PropertiesUtil.logProperties("System", System.getProperties(), log);
            application.setup();
            application.run(_args);
        } catch (Throwable e) {
            log.error("Unexpected error.", e);
        } finally {

            if (application != null) {
                application.cleanup();
            }
        }
    }

    public NmdCliApplication() {
        this.cleaned = false;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                NmdCliApplication.this.cleanup();
                long upTime = System.currentTimeMillis() - NmdCliApplication.this.startTime;
                NmdCliApplication.log.info(NmdCliApplication.this.versionInfo.getCliApplicationTitle() + " stopped. Uptime: " + TimeTools.format(upTime));
            }
        });

    }

    private void setup() {
        readVersionInfo();

        loadDefaults();

        createWorkspaceManager();
        createSettingsManager();

        createApi();
        createCliParser();
    }

    private void readVersionInfo() {
        this.versionInfo = VersionInfoTools.readVersionInfo(getClass());
    }

    private void run(final String[] _args) {
        log.info(this.versionInfo.getCliApplicationTitle() + " started.");
        this.startTime = System.currentTimeMillis();

        try {
            CliParserResult parserResult = parseCommandLine(_args);
            execute(parserResult.getScript());

        } catch (CliParser.CliParserException e) {
            log.error("Error parsing command line [ " + e.getMessage() + " ].", e);
            this.cliParser.printUsage();
        } catch (Command.CommandExecutionException e) {
            log.error("Error execute command [ " + e.getMessage() + " ].", e);
        }

    }

    private CliParserResult parseCommandLine(final String[] _args) throws CliParser.CliParserException {
        return this.cliParser.parse(_args);
    }

    private void execute(final List<Command> _script) throws Command.CommandExecutionException {

        for (Command command : _script) {
            command.execute();
        }
    }

    private void cleanup() {

        if (!this.cleaned) {
            this.api.cleanup();
            this.cleaned = true;
        } else {
            log.debug("Application cleaned already");
        }
    }

    private void loadDefaults() {
        this.defaults = new FileDefaults(DEFAULTS_PROPERTIES_FILE_NAME);
    }

    private void createWorkspaceManager() {
        this.workspaceManager = new WorkspaceDirectoriesManager(this.defaults.getWorkspacesDirectory());
    }

    private void createSettingsManager() {
        this.settingsManager = new FileSettingsManager(this.defaults.getSettingsDirectory(), this.defaults.getDefaultStorageRoot(), this.defaults.getDefaultStoragePeriod(), this.defaults.getGoogleReaderDirectory());
    }

    private void createApi() {
        this.api = new NmdApi(this.defaults, this.settingsManager, this.workspaceManager, this.versionInfo);
    }

    private void createCliParser() {
        this.cliParser = new CliParser(System.out, this.api);
    }
}
