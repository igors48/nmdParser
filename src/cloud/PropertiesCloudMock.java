package cloud;

import constructor.dom.ObjectType;
import util.Assert;

import java.util.Map;
import java.util.Properties;

import static util.CollectionUtils.newHashMap;

/**
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public class PropertiesCloudMock implements PropertiesCloud {

    private final Map<String, Properties> map;

    public PropertiesCloudMock() {
        this.map = newHashMap();
    }

    public synchronized void storeProperties(final String _owner, final ObjectType _type, final Properties _properties) throws PropertyCloudException {
        Assert.isValidString(_owner);
        Assert.notNull(_properties);

        this.map.put(_owner, _properties);
    }

    public synchronized Properties readProperties(final String _owner, final ObjectType _type) throws PropertyCloudException {
        Assert.isValidString(_owner);

        Properties result = this.map.get(_owner);

        if (result == null) {
            result = new Properties();
        }

        return result;
    }

    public synchronized void removeProperties(final String _owner, final ObjectType _type) throws PropertyCloudException {
        Assert.isValidString(_owner);

        this.map.remove(_owner);
    }
}
