package work.unit.dom;

import work.testutil.SaxLoaderTestUtils;
import util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import constructor.dom.Locator;
import constructor.dom.ObjectType;
import constructor.dom.locator.Mask;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import app.templater.Template;

/**
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public class LocatorMock implements Locator {

    private final Map<String, String> streams;

    public LocatorMock(final Map<String, String> _streams) {
        Assert.notNull(_streams);
        this.streams = _streams;
    }

    public String getSourceFile(final String _id, final ObjectType _type) throws LocatorException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.notNull(_type, "Type is null.");

        return "";
    }

    public InputStream locate(final String _id, final ObjectType _type) throws LocatorException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.notNull(_type, "Type is null.");

        String stream = this.streams.get(_id);

        if (stream == null){
            throw new LocatorException("Can`t locate object [ " + _id + " ] of type [ " + _type + " ].");
        }

        return SaxLoaderTestUtils.createStream(stream);
    }

    public List<String> locateAll(final ObjectType _type, final Mask _mask) throws LocatorException {
        return new ArrayList<String>();
    }

    public void storeTemplates(String _name, List<Template> _templates) throws LocatorException {
    }

    public void removeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException {
    }

    public void storeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException {
    }

}
