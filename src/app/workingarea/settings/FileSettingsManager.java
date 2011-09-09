package app.workingarea.settings;

import app.workingarea.Settings;
import app.workingarea.SettingsManager;
import app.workingarea.settings.properties.PropertiesFileLoader;
import app.workingarea.settings.properties.PropertiesResolver;
import util.Assert;

import java.io.File;

/**
 * Менеджер установок хранящихся в файлах. Все файлы установок хранятся
 * в одном каталоге
 *
 * @author Igor Usenko
 *         Date: 16.04.2009
 */
public class FileSettingsManager implements SettingsManager {

    private final PropertiesResolver resolver;
    private final String defaultStorageRoot;
    private final long defaultStoragePeriod;
    private final String googleReaderRoot;

    public FileSettingsManager(final String _root, final String _defaultStorageRoot, final long _defaultStoragePeriod, final String _googleReaderRoot) {
        Assert.isValidString(_root, "Settings manager root path is not valid.");
        Assert.isTrue(new File(_root).exists(), "Settings manager root path [ " + _root + " ] is not exists.");

        this.resolver = new PropertiesResolver(new PropertiesFileLoader(_root));

        Assert.isValidString(_defaultStorageRoot, "Default storage root is not valid");
        this.defaultStorageRoot = _defaultStorageRoot;

        Assert.greaterOrEqual(_defaultStoragePeriod, 0, "Default storage period < 0");
        this.defaultStoragePeriod = _defaultStoragePeriod;

        Assert.isValidString(_googleReaderRoot, "Google Reader root path is not valid.");
        Assert.isTrue(new File(_root).exists(), "Google Reader root path [ " + _root + " ] is not exists.");
        this.googleReaderRoot = _googleReaderRoot;
    }

    public Settings getSettings(final String _id) throws SettingsManagerException {
        Assert.isValidString(_id, "Properties id is not valid.");

        try {
            return new SettingsFromProperties(this.resolver.resolve(_id), this.defaultStorageRoot, this.defaultStoragePeriod, this.googleReaderRoot);
        } catch (PropertiesResolver.PropertyResolverException e) {
            throw new SettingsManagerException(e);
        } catch (Settings.SettingsException e) {
            throw new SettingsManagerException(e);
        }
    }
}
