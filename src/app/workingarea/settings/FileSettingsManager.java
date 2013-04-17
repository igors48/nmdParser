package app.workingarea.settings;

import app.workingarea.Settings;
import app.workingarea.SettingsManager;
import app.workingarea.settings.properties.PropertiesFileLoader;
import app.workingarea.settings.properties.PropertiesResolver;
import util.Assert;

import java.io.File;

/**
 * �������� ��������� ���������� � ������. ��� ����� ��������� ��������
 * � ����� ��������
 *
 * @author Igor Usenko
 *         Date: 16.04.2009
 */
public class FileSettingsManager implements SettingsManager {

    private final PropertiesResolver resolver;
    private final String defaultStorageRoot;
    private final long defaultStoragePeriod;

    public FileSettingsManager(final String _root, final String _defaultStorageRoot, final long _defaultStoragePeriod) {
        Assert.isValidString(_root, "Settings manager root path is not valid.");
        Assert.isTrue(new File(_root).exists(), "Settings manager root path [ " + _root + " ] is not exists.");

        this.resolver = new PropertiesResolver(new PropertiesFileLoader(_root));

        Assert.isValidString(_defaultStorageRoot, "Default storage root is not valid");
        this.defaultStorageRoot = _defaultStorageRoot;

        Assert.greaterOrEqual(_defaultStoragePeriod, 0, "Default storage period < 0");
        this.defaultStoragePeriod = _defaultStoragePeriod;
    }

    public Settings getSettings(final String _id) throws SettingsManagerException {
        Assert.isValidString(_id, "Properties id is not valid.");

        try {
            return new SettingsFromProperties(this.resolver.resolve(_id), this.defaultStorageRoot, this.defaultStoragePeriod);
        } catch (PropertiesResolver.PropertyResolverException | Settings.SettingsException e) {
            throw new SettingsManagerException(e);
        }
    }
}
