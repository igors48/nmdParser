package app.workingarea.settings.properties;

import util.Assert;

import java.util.Map;
import java.util.Properties;

/**
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public class PropertiesLoaderMock implements Loader {

    public final Map<String, Properties> map;

    public PropertiesLoaderMock(final Map<String, Properties> _map) {
        Assert.notNull(_map, "Map is null.");
        this.map = _map;
    }

    public Properties load(final String _id) throws LocatorException {
        Assert.isValidString(_id, "Properties id is not valid.");

        Properties result = this.map.get(_id);

        if (result == null) {
            throw new LocatorException("Properties with id [ " + _id + " ] not found.");
        }

        return result;
    }
    
}
