package work.unit.propertiesresolver;

import junit.framework.TestCase;
import app.workingarea.settings.properties.PropertiesLoaderMock;
import app.workingarea.settings.properties.PropertiesResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public class PropertiesResolverTest extends TestCase {

    public PropertiesResolverTest(String _s) {
        super(_s);
    }

    // проста€ проверка без наследовани€
    public void testSimpleWithoutExtends() throws PropertiesResolver.PropertyResolverException {
        Properties pack01 = new Properties();
        pack01.setProperty("first", "firstValue");
        pack01.setProperty("second", "secondValue");

        Map<String, Properties> map = new HashMap<String, Properties>();
        map.put("pack01", pack01);

        PropertiesLoaderMock loader = new PropertiesLoaderMock(map);
        PropertiesResolver resolver = new PropertiesResolver(loader);

        Properties resolved = resolver.resolve("pack01");

        assertEquals(pack01.size(), resolved.size());
        assertEquals(pack01.getProperty("first"), resolved.getProperty("first"));
        assertEquals(pack01.getProperty("second"), resolved.getProperty("second"));
    }

    // проверка c наследованием
    public void testWithExtends() throws PropertiesResolver.PropertyResolverException {
        Properties zpack01 = new Properties();
        zpack01.setProperty("first", "firstValue");
        zpack01.setProperty("second", "secondValue");

        Properties pack02 = new Properties();
        pack02.setProperty("extends", "zpack01");
        pack02.setProperty("third", "thirdValue");
        pack02.setProperty("fourth", "fourthValue");

        Map<String, Properties> map = new HashMap<String, Properties>();
        map.put("pack02", pack02);
        map.put("zpack01", zpack01);

        PropertiesLoaderMock loader = new PropertiesLoaderMock(map);
        PropertiesResolver resolver = new PropertiesResolver(loader);

        Properties resolved = resolver.resolve("pack02");

        assertEquals(5, resolved.size());
        assertEquals(zpack01.getProperty("first"), resolved.getProperty("first"));
        assertEquals(zpack01.getProperty("second"), resolved.getProperty("second"));
        assertEquals(pack02.getProperty("third"), resolved.getProperty("third"));
        assertEquals(pack02.getProperty("fourth"), resolved.getProperty("fourth"));
        assertEquals(pack02.getProperty("extends"), resolved.getProperty("extends"));
    }

    // проверка c наследованием от двух
    public void testWithExtendsOfTwo() throws PropertiesResolver.PropertyResolverException {
        Properties pack01 = new Properties();
        pack01.setProperty("first", "firstValue");
        pack01.setProperty("second", "secondValue");

        Properties pack02 = new Properties();
        pack02.setProperty("extends", "pack01");
        pack02.setProperty("third", "thirdValue");
        pack02.setProperty("fourth", "fourthValue");

        Properties pack03 = new Properties();
        pack03.setProperty("extends", "pack02");
        pack03.setProperty("fifth", "fifthValue");

        Map<String, Properties> map = new HashMap<String, Properties>();
        map.put("pack01", pack01);
        map.put("pack02", pack02);
        map.put("pack03", pack03);

        PropertiesLoaderMock loader = new PropertiesLoaderMock(map);
        PropertiesResolver resolver = new PropertiesResolver(loader);

        Properties resolved = resolver.resolve("pack03");

        assertEquals(6, resolved.size());
        assertEquals(pack01.getProperty("first"), resolved.getProperty("first"));
        assertEquals(pack01.getProperty("second"), resolved.getProperty("second"));
        assertEquals(pack02.getProperty("third"), resolved.getProperty("third"));
        assertEquals(pack02.getProperty("fourth"), resolved.getProperty("fourth"));
        assertEquals(pack03.getProperty("fifth"), resolved.getProperty("fifth"));
    }

    // проверка реакции на отсутствие пропертей
    public void testNotExists() {
        Properties pack01 = new Properties();
        pack01.setProperty("first", "firstValue");
        pack01.setProperty("second", "secondValue");

        Map<String, Properties> map = new HashMap<String, Properties>();
        map.put("pack01", pack01);

        PropertiesLoaderMock loader = new PropertiesLoaderMock(map);
        PropertiesResolver resolver = new PropertiesResolver(loader);

        try {
            resolver.resolve("pack02");
        } catch (PropertiesResolver.PropertyResolverException e) {
            return;
        }
        fail();
    }

    // проверка реакции на отсутствие предка
    public void testParentNotExists() throws PropertiesResolver.PropertyResolverException {
        Properties pack01 = new Properties();
        pack01.setProperty("first", "firstValue");
        pack01.setProperty("second", "secondValue");

        Properties pack02 = new Properties();
        pack02.setProperty("extends", "pack03");
        pack02.setProperty("third", "thirdValue");
        pack02.setProperty("fourth", "fourthValue");

        Map<String, Properties> map = new HashMap<String, Properties>();
        map.put("pack01", pack01);
        map.put("pack02", pack02);

        PropertiesLoaderMock loader = new PropertiesLoaderMock(map);
        PropertiesResolver resolver = new PropertiesResolver(loader);

        try {
            resolver.resolve("pack02");
        } catch (PropertiesResolver.PropertyResolverException e) {
            return;
        }
        fail();
    }

    // тест на ошибку 03 окт 2009 (v1.1.83) с нарушением пор€дка наследовани€ default <- another
    public void testRealIncorrectInheritanceOrder() throws PropertiesResolver.PropertyResolverException {
        Properties defaults = new Properties();
        defaults.setProperty("api.temp.directory", "E:/temp/_cache_nmd_/");
        defaults.setProperty("downloader.banned.list.treshold", "10");

        Properties another = new Properties();
        another.setProperty("extends", "defaults");
        another.setProperty("api.temp.directory", "E:/temp/_cache_nmd_2/");
        
        Map<String, Properties> map = new HashMap<String, Properties>();
        map.put("defaults", defaults);
        map.put("another", another);

        PropertiesLoaderMock loader = new PropertiesLoaderMock(map);
        PropertiesResolver resolver = new PropertiesResolver(loader);

        Properties result = resolver.resolve("another");

        assertEquals("E:/temp/_cache_nmd_2/", result.getProperty("api.temp.directory"));
    }
}
