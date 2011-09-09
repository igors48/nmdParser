package cloud;

import constructor.dom.ObjectType;
import util.Assert;
import util.IOTools;
import util.PathTools;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Облако пропертей расположенных в отдельных файлах.
 * На каждого владельца - заводится отдельный файл с
 * имненем состоящим из идентификатора владельца и его типа
 *
 * @author Igor Usenko
 *         Date: 31.03.2009
 */
public class FilesPropertiesCloud implements PropertiesCloud {

    private final String root;

    private static final String PROPERTIES_FILE_EXTENSION = ".properties";
    private static final String NAME_TYPE_DIVIDER = "-";

    public FilesPropertiesCloud(final String _cloudDirectory) {
        Assert.isValidString(_cloudDirectory, "Cloud directory name is not valid.");
        this.root = PathTools.normalize(_cloudDirectory);
        Assert.isTrue((new File(this.root)).exists(), "Cloud directory [ " + this.root + " ] not exists.");
    }

    public synchronized void storeProperties(final String _owner, final ObjectType _type, final Properties _properties) throws PropertyCloudException {
        Assert.isValidString(_owner, "Owner is not valid.");
        Assert.notNull(_type, "Type is null.");
        Assert.notNull(_properties, "Properties is null.");

        try {
            IOTools.writePropertiesFile(_properties, getPropertiesFileHeader(_owner, _type), getPropertiesFile(_owner, _type));
        } catch (IOException e) {
            throw new PropertyCloudException(e);
        }
    }

    public synchronized Properties readProperties(final String _owner, final ObjectType _type) throws PropertyCloudException {
        Assert.isValidString(_owner, "Owner is not valid.");
        Assert.notNull(_type, "Type is null.");

        Properties result = new Properties();

        try {
            result = IOTools.readPropertiesFile(getPropertiesFile(_owner, _type));
        } catch (IOException e) {
            // empty
        }

        return result;
    }

    public synchronized void removeProperties(final String _owner, final ObjectType _type) throws PropertyCloudException {
        Assert.isValidString(_owner, "Owner is not valid.");
        Assert.notNull(_type, "Type is null.");

        if (!getPropertiesFile(_owner, _type).delete()) {
            throw new PropertyCloudException("Can not delete property file [ " + getPropertiesFileName(_owner, _type) + " ].");
        }
    }

    private File getPropertiesFile(final String _owner, final ObjectType _type) {
        return new File(this.root + getPropertiesFileName(_owner, _type));
    }

    private String getPropertiesFileName(final String _owner, final ObjectType _type) {
        return _type + NAME_TYPE_DIVIDER + _owner + PROPERTIES_FILE_EXTENSION;
    }

    private String getPropertiesFileHeader(final String _owner, final ObjectType _type) {
        return "This is properties file for " + _type + " with id [ " + _owner + " ] . Do not modify this file!";
    }
}
