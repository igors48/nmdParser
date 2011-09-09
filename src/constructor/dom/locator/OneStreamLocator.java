package constructor.dom.locator;

import app.templater.Template;
import constructor.dom.Locator;
import constructor.dom.ObjectType;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Локатор для одного потока с сохраненным объектом.
 * На все запросы возвращает только его
 *
 * @author Igor Usenko
 *         Date: 08.12.2009
 */
public class OneStreamLocator implements Locator {

    private final InputStream stream;

    public OneStreamLocator(final InputStream _stream) {
        Assert.notNull(_stream, "Input stream is null");
        this.stream = _stream;
    }

    public String getSourceFile(final String _id, final ObjectType _type) throws LocatorException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.notNull(_type, "Type is null.");

        return "";
    }

    public InputStream locate(final String _id, final ObjectType _type) throws LocatorException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.notNull(_type, "Type is null.");

        return this.stream;
    }

    public List<String> locateAll(final ObjectType _type, final Mask _mask) throws LocatorException {
        Assert.notNull(_type, "Type is null.");
        Assert.notNull(_mask, "Mask is null.");

        return new ArrayList<String>();
    }

    public void storeTemplates(final String _name, final List<Template> _templates) throws LocatorException {
        Assert.isValidString(_name, "Name is not valid.");
        Assert.notNull(_templates, "Templates is null.");

        // empty
    }

    public void removeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException {
        // empty
    }

    public void storeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException {
        // empty
    }
}
