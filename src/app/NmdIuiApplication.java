package app;

import app.api.ApiFacade;
import app.api.NmdApi;
import app.iui.ApiService;
import app.iui.GuiSettings;
import app.iui.MainFrame;
import app.iui.command.CommandExecutor;
import app.iui.command.ExitSignalListener;
import app.iui.flow.FlowController;
import app.iui.flow.FormFactory;
import app.iui.resource.StringResourceBundle;
import app.iui.validator.ValidatorFactory;
import app.iui.workingarea.GuiSettingsFromProperties;
import app.workingarea.Defaults;
import app.workingarea.SettingsManager;
import app.workingarea.WorkspaceManager;
import app.workingarea.defaults.FileDefaults;
import app.workingarea.settings.FileSettingsManager;
import app.workingarea.workspace.WorkspaceDirectoriesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.IOTools;
import util.PropertiesUtil;
import util.TimeTools;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Igor Usenko
 *         Date: 22.11.2010
 */
public class NmdIuiApplication implements MainFrame.Listener, ExitSignalListener {

    private static final String SHUTDOWN_HOOK_THREAD_NAME = "ShutdownHookThread";

    private static final String RESOURCE_BUNDLE_BASE_NAME = "strings";

    private static final String GUI_WORKAREA_DIRECTORY = "./workarea/lite/";
    private static final String GUI_PROPERTIES_FILE_NAME = "lite.properties";

    private static final String DEFAULTS_PROPERTIES_FILE_NAME = "./etc/defaults.properties";

    private final GuiSettingsFromProperties guiSettings;

    private final MainFrame mainFrame;

    private final VersionInfo versionInfo;
    private long startTime;
    private boolean cleaned;

    private Defaults defaults;
    private WorkspaceManager workspaceManager;
    private SettingsManager settingsManager;
    private ApiFacade api;

    private static final Log log = LogFactory.getLog(NmdIuiApplication.class);

    public NmdIuiApplication() throws GuiSettings.GuiSettingsException, ApiFacade.FatalException {
        PropertiesUtil.logProperties("System", System.getProperties(), log);

        this.cleaned = false;
        this.versionInfo = VersionInfoTools.readVersionInfo(getClass());
        this.startTime = System.currentTimeMillis();

        this.guiSettings = new GuiSettingsFromProperties(loadGuiProperties());

        StringResourceBundle stringResourceBundle = new StringResourceBundle(ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME));

        loadDefaults();
        createWorkspaceManager();
        createSettingsManager();
        createApi();

        setLookAndFeel();

        this.api.loadSettings(this.defaults.getDefaultSettingsName());
        CommandExecutor commandExecutor = new CommandExecutor(this);

        this.mainFrame = new MainFrame(new FlowController(stringResourceBundle, this.guiSettings, this.api),
                new FormFactory(new ApiService(this.api, commandExecutor, this.guiSettings),
                        commandExecutor,
                        new ValidatorFactory(stringResourceBundle),
                        stringResourceBundle),
                stringResourceBundle,
                this.versionInfo,
                this.guiSettings,
                this);
        this.mainFrame.setVisible(true);
        this.mainFrame.touch();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                setName(SHUTDOWN_HOOK_THREAD_NAME);
                NmdIuiApplication.this.cleanup();
                long upTime = System.currentTimeMillis() - NmdIuiApplication.this.startTime;
                NmdIuiApplication.log.debug(NmdIuiApplication.this.versionInfo.getGuiApplicationTitle() + " stopped. Uptime: " + TimeTools.format(upTime));
            }
        });

        log.debug(this.versionInfo.getGuiApplicationTitle() + " started");
        log.debug("Settings [ " + this.defaults.getDefaultSettingsName() + " ] loaded");
    }

    public void onNormalExit() {
        cleanup();
    }

    public void onFailureExit(final Throwable _cause) {

        if (this.mainFrame != null) {
            this.mainFrame.showUnexpectedError(_cause);
        }
    }

    private void setLookAndFeel() {

        try {

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (Throwable e) {
            log.error("Error setting Nimbus LaF. Default will be used");
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

    private void cleanup() {

        if (this.cleaned) {
            log.debug("Application cleaned already");
        } else {
            if (this.mainFrame != null) {
                this.mainFrame.dispose();
            }

            storeGuiProperties(this.guiSettings.getProperties());

            cleanupApi();

            this.cleaned = true;

            log.debug("Application cleaned");
        }
    }

    private void cleanupApi() {
        this.api.cleanup();
    }

    private Properties loadGuiProperties() {
        Properties result = new Properties();

        FileInputStream stream = null;

        try {
            stream = new FileInputStream(GUI_WORKAREA_DIRECTORY + GUI_PROPERTIES_FILE_NAME);
            result.load(stream);
        } catch (IOException e) {
            log.error("Error loading properties from file [ " + GUI_WORKAREA_DIRECTORY + GUI_PROPERTIES_FILE_NAME + " ].", e);
        } finally {
            IOTools.close(stream);
        }

        return result;
    }

    private void storeGuiProperties(final Properties _properties) {
        FileOutputStream stream = null;

        try {
            File file = new File(GUI_WORKAREA_DIRECTORY + GUI_PROPERTIES_FILE_NAME);
            file.createNewFile();

            stream = new FileOutputStream(file);
            _properties.store(stream, "");
        } catch (IOException e) {
            log.error("Error store properties to file [ " + GUI_WORKAREA_DIRECTORY + GUI_PROPERTIES_FILE_NAME + " ].", e);
        } finally {
            IOTools.close(stream);
        }
    }

    public static void main(final String[] _args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                try {
                    new NmdIuiApplication();
                } catch (Throwable e) {
                    log.error("Unexpected error", e);
                }
            }
        });
    }

}
