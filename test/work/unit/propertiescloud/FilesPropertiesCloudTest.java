package work.unit.propertiescloud;

import cloud.FilesPropertiesCloud;
import cloud.PropertiesCloud;
import constructor.dom.ObjectType;
import junit.framework.TestCase;
import static work.testutil.CompTestsUtils.cleanupDir;
import static work.testutil.CompTestsUtils.getCompTestsRoot;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Igor Usenko
 *         Date: 03.04.2009
 */
public class FilesPropertiesCloudTest extends TestCase {

    public static String DIR = getCompTestsRoot() + "FilesPropertiesCloudTest\\";

    public FilesPropertiesCloudTest(String _s) {
        super(_s);
    }

    // тест на возврат пустых пропертей если не нашлось
    public void testEmpty() throws PropertiesCloud.PropertyCloudException, IOException {
        cleanupDir(DIR);

        FilesPropertiesCloud cloud = new FilesPropertiesCloud(DIR);
        Properties properties = cloud.readProperties("Some", ObjectType.CHANNEL);

        assertTrue(properties != null);
        assertEquals(0, properties.size());

        cleanupDir(DIR);
    }

    // тест на сохранение/восстановление пропертей
    public void testStoreLoad() throws PropertiesCloud.PropertyCloudException, IOException {
        cleanupDir(DIR);

        FilesPropertiesCloud cloud = new FilesPropertiesCloud(DIR);

        Properties fixture = new Properties();
        fixture.setProperty("property01", "value01");
        fixture.setProperty("property02", "value02");

        cloud.storeProperties("Some", ObjectType.CHANNEL, fixture);

        Properties properties = cloud.readProperties("Some", ObjectType.CHANNEL);

        assertTrue(properties != null);
        assertEquals(2, properties.size());
        assertEquals(fixture.getProperty("property01"), properties.getProperty("property01"));
        assertEquals(fixture.getProperty("property02"), properties.getProperty("property02"));

        cleanupDir(DIR);
    }

    // тест на удаление пропертей
    public void testRemove() throws PropertiesCloud.PropertyCloudException, IOException {
        cleanupDir(DIR);

        FilesPropertiesCloud cloud = new FilesPropertiesCloud(DIR);

        Properties fixture = new Properties();
        fixture.setProperty("property01", "value01");
        fixture.setProperty("property02", "value02");

        cloud.storeProperties("Some", ObjectType.CHANNEL, fixture);

        cloud.removeProperties("Some", ObjectType.CHANNEL);

        Properties properties = cloud.readProperties("Some", ObjectType.CHANNEL);

        assertTrue(properties != null);
        assertEquals(0, properties.size());

        cleanupDir(DIR);
    }
}
