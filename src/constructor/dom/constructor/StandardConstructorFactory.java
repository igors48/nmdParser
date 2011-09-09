package constructor.dom.constructor;

import app.workingarea.ServiceManager;
import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.Loader;
import constructor.dom.Locator;
import constructor.dom.locator.OneStreamLocator;
import util.Assert;

import java.io.InputStream;

/**
 * Стандартная фабрика конструкторов
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public class StandardConstructorFactory implements ConstructorFactory {

    private final Locator locator;
    private final Loader loader;
    private final ServiceManager serviceManager;

    public StandardConstructorFactory(final Locator _locator, final Loader _loader, final ServiceManager _serviceManager) {
        Assert.notNull(_locator, "Locator is null.");
        Assert.notNull(_loader, "Loader is null.");
        Assert.notNull(_serviceManager, "Service manager is null.");

        this.locator = _locator;
        this.loader = _loader;
        this.serviceManager = _serviceManager;

    }

    public Constructor getConstructor() {
        return new StandardConstructor(this.locator, this.loader, this, this.serviceManager.getPreprocessor());
    }

    public Constructor getConstructor(final InputStream _inputStream) {
        Assert.notNull(_inputStream, "Input stream is null");

        return new StandardConstructor(new OneStreamLocator(_inputStream), this.loader, this, this.serviceManager.getPreprocessor());
    }
}
