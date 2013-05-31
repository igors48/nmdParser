package app.workingarea.defaults;

import app.workingarea.Defaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.IOTools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ������� ����������� � �����
 *
 * @author Igor Usenko
 *         Date: 11.04.2009
 */
public class FileDefaults implements Defaults {

    public static final String DEFAULT_STORAGE_ROOT_KEY = "default.storage.root";
    public static final String DEFAULT_STORAGE_PERIOD_KEY = "default.storage.period.days";

    private static final int TO_MILLIS = 24 * 60 * 60 * 1000;
    private static final String EQUALS_LITERAL = " = ";

    private final Properties properties;

    private static final String DEFAULT_SETTINGS_NAME_KEY = "default.settings.name";
    private static final String DEFAULT_WORKSPACE_NAME_KEY = "default.workspace.name";
    private static final String SETTINGS_DIRECTORY_NAME_KEY = "settings.directory.name";
    private static final String WORKSPACES_DIRECTORY_NAME_KEY = "workspaces.directory.name";

    private static final int DEFAULT_STORAGE_PERIOD_DEFAULT = 7;
    private static final String DEFAULT_STORAGE_ROOT_DEFAULT = "./workarea/root/";
    private static final String SETTINGS_DIRECTORY_NAME_DEFAULT = "./workarea/settings/";
    private static final String WORKSPACES_DIRECTORY_NAME_DEFAULT = "./workarea/workspaces/";
    private static final String DEFAULT_NAME = "default";

    private static final String ENGINE_DEFAULTS_TITLE = "Engine defaults";
    private static final String DIVIDER_STRING = " ======= ";

    private final Log log;

    public FileDefaults(final String _file) {
        Assert.isValidString(_file, "File name is not valid.");

        this.log = LogFactory.getLog(getClass());

        this.properties = new Properties();
        loadProperties(_file);
    }

    public String getDefaultSettingsName() {
        return this.properties.getProperty(DEFAULT_SETTINGS_NAME_KEY, DEFAULT_NAME);
    }

    public String getDefaultWorkspaceName() {
        return this.properties.getProperty(DEFAULT_WORKSPACE_NAME_KEY, DEFAULT_NAME);
    }

    public String getSettingsDirectory() {
        return this.properties.getProperty(SETTINGS_DIRECTORY_NAME_KEY, SETTINGS_DIRECTORY_NAME_DEFAULT);
    }

    public String getWorkspacesDirectory() {
        return this.properties.getProperty(WORKSPACES_DIRECTORY_NAME_KEY, WORKSPACES_DIRECTORY_NAME_DEFAULT);
    }

    public String getDefaultStorageRoot() {
        return this.properties.getProperty(DEFAULT_STORAGE_ROOT_KEY, DEFAULT_STORAGE_ROOT_DEFAULT);
    }

    public long getDefaultStoragePeriod() {
        int result = DEFAULT_STORAGE_PERIOD_DEFAULT;

        try {
            result = Integer.valueOf(this.properties.getProperty(DEFAULT_STORAGE_PERIOD_KEY));
        } catch (Throwable e) {
            // empty
        }

        return result * TO_MILLIS;
    }

    private void logDefaults() {
        this.log.debug(DIVIDER_STRING + ENGINE_DEFAULTS_TITLE + DIVIDER_STRING);

        this.log.debug(DEFAULT_STORAGE_ROOT_KEY + EQUALS_LITERAL + getDefaultStorageRoot());
        this.log.debug(DEFAULT_STORAGE_PERIOD_KEY + EQUALS_LITERAL + getDefaultStoragePeriod());
        this.log.debug(DEFAULT_SETTINGS_NAME_KEY + EQUALS_LITERAL + getDefaultSettingsName());
        this.log.debug(DEFAULT_WORKSPACE_NAME_KEY + EQUALS_LITERAL + getDefaultWorkspaceName());
        this.log.debug(SETTINGS_DIRECTORY_NAME_KEY + EQUALS_LITERAL + getSettingsDirectory());
        this.log.debug(WORKSPACES_DIRECTORY_NAME_KEY + EQUALS_LITERAL + getWorkspacesDirectory());
    }

    private void loadProperties(final String _file) {
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(_file);
            this.properties.load(stream);

            logDefaults();
        } catch (IOException e) {
            log.error("Error loading defaults from file [ " + _file + " ].", e);
        } finally {
            IOTools.close(stream);
        }
    }

}
