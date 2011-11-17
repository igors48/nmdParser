package work.testutil;

import app.workingarea.service.NullServiceManager;
import constructor.dom.ComponentFactory;
import constructor.dom.ConstructorFactory;
import constructor.dom.Loader;
import constructor.dom.Locator;
import constructor.dom.constructor.StandardComponentFactory;
import constructor.dom.constructor.StandardConstructorFactory;
import constructor.dom.loader.DomStreamLoader;
import util.Assert;
import work.unit.dom.LocatorMock;

import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 26.03.2009
 */
public final class ConstructorTestUtils {

    public static ConstructorFactory createConstructorFactory(Map<String, String> _streams) {
        Assert.notNull(_streams, "Streams is null");

        ComponentFactory componentFactory = new StandardComponentFactory(new NullServiceManager());
        Loader loader = new DomStreamLoader(componentFactory);

        Locator locator = new LocatorMock(_streams);

        return new StandardConstructorFactory(locator, loader, new NullServiceManager());
    }

    private ConstructorTestUtils() {
        // empty
    }
}
