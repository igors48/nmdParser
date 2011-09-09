package work.unit.javastylelocator;

import junit.framework.TestCase;
import constructor.dom.locator.JavaStyleLocator;
import constructor.dom.locator.Mask;
import constructor.dom.ObjectType;
import constructor.dom.Locator;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import work.testutil.CompTestsUtils;
import work.testutil.SimplerConfigurationTestUtils;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import util.IOTools;

/**
 * @author Igor Usenko
 *         Date: 18.07.2009
 */
public class JavaStyleLocatorTest extends TestCase {
    
    public static String DIR = CompTestsUtils.getCompTestsRoot() + "JavaStyleLocatorTest/";

    public static String TEST_SMOKE_DIR = DIR + "testSmoke/";
    public static String TEST_SIMPLER_DIR = DIR + "testSimpler/";

    public JavaStyleLocatorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SMOKE_DIR);

        InputStream stream01 = null;
        InputStream stream02 = null;

        try {
            stream01 = locator.locate("3d-news-source", ObjectType.SOURCE);
            stream02 = locator.locate("package.3d-news-source", ObjectType.SOURCE);
        } catch (Locator.LocatorException e) {
            fail(e.getMessage());
        } finally {
            IOTools.close(stream01);
            IOTools.close(stream02);
        }
    }
    
    // поиск несуществующего
    public void testNonExistent() {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SMOKE_DIR);

        InputStream stream01 = null;

        try {
            stream01 = locator.locate("4d-news-source", ObjectType.SOURCE);

            fail();
        } catch (Locator.LocatorException e) {
          // empty
        } finally {
            IOTools.close(stream01);
        }
    }

    // поиск чтения файла неизвестного типа
    public void testIncorrect() {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SMOKE_DIR);

        InputStream stream01 = null;

        try {
            stream01 = locator.locate("incorrect", ObjectType.SOURCE);

            fail();
        } catch (Locator.LocatorException e) {
          // empty
        } finally {
            IOTools.close(stream01);
        }
    }

    // поиск чтения файла неизвестного формата
    public void testIncorrectFormat() {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SMOKE_DIR);

        InputStream stream01 = null;

        try {
            stream01 = locator.locate("broken", ObjectType.SOURCE);

            fail();
        } catch (Locator.LocatorException e) {
          // empty
        } finally {
            IOTools.close(stream01);
        }
    }
    
    // поиск всех объектов указанного типа
    public void testLocateAll() throws Locator.LocatorException {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SMOKE_DIR);

        List<String> ids = locator.locateAll(ObjectType.SOURCE, new Mask(new ArrayList<String>(), new ArrayList<String>()));

        assertEquals(2, ids.size());
    }

    // сохранение конфигурации корневого симплера
    public void testStoreRootSimplerConfiguration() throws Locator.LocatorException, IOException {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SIMPLER_DIR);

        SimplerConfiguration fixture = SimplerConfigurationTestUtils.createSimplerConfiguration("id", "yes");

        locator.storeSimplerConfiguration(fixture);

        InputStream stream = locator.locate("id", ObjectType.SIMPLER);
        stream.close();

        locator.removeSimplerConfiguration(fixture);
    }
    
    // сохранение конфигурации не корневого симплера
    public void testStoreNotRootSimplerConfiguration() throws Locator.LocatorException, IOException {
        JavaStyleLocator locator = new JavaStyleLocator(TEST_SIMPLER_DIR);

        SimplerConfiguration fixture = SimplerConfigurationTestUtils.createSimplerConfiguration("one.two.three.id", "yes");

        locator.storeSimplerConfiguration(fixture);

        InputStream stream = locator.locate("one.two.three.id", ObjectType.SIMPLER);
        stream.close();

        locator.removeSimplerConfiguration(fixture);
    }
}
