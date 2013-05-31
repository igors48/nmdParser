package work.unit.modificationlistfilestorage;

import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.source.core.storage.ModificationListFileStorage;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import junit.framework.TestCase;
import work.testutil.CompTestsUtils;

import java.io.IOException;
import java.util.Date;

import static work.testutil.CompTestsUtils.cleanupDir;
import static work.testutil.ModificationTestUtils.assertModificationEquals;

/**
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public class ModificationListFileStorageTest extends TestCase {

    public static String DIR = CompTestsUtils.getCompTestsRoot() + "ModificationListFileStorageTest\\";

    public ModificationListFileStorageTest(String _s) {
        super(_s);
    }

    // тест возврата пустого списка если ранее он не сохранялся

    public void testNonExistent() throws IOException, ModificationListStorage.ModificationListStorageException {
        cleanupDir(DIR);

        ModificationListFileStorage storage = new ModificationListFileStorage(DIR);
        ModificationList result = storage.load("nonexistent");

        assertTrue(result != null);
        assertEquals(0, result.size());

        cleanupDir(DIR);
    }

    // тест сохранения/восстановления

    public void testStoreLoad() throws IOException, ModificationListStorage.ModificationListStorageException {
        cleanupDir(DIR);

        ModificationList original = createModificationList();

        ModificationListFileStorage storage = new ModificationListFileStorage(DIR);
        storage.store("sample", original);

        ModificationList result = storage.load("sample");

        assertTrue(result != null);
        assertEquals(original.size(), result.size());
        assertModificationEquals(original.get(0), result.get(0));

        cleanupDir(DIR);
    }

    // тест на удаление

    public void testRemove() throws IOException, ModificationListStorage.ModificationListStorageException {
        cleanupDir(DIR);

        ModificationList original = createModificationList();

        ModificationListFileStorage storage = new ModificationListFileStorage(DIR);
        storage.store("sample", original);

        storage.remove("sample");

        ModificationList result = storage.load("nonexistent");

        assertTrue(result != null);
        assertEquals(0, result.size());

        cleanupDir(DIR);
    }

    private ModificationList createModificationList() {
        ModificationList original = new ModificationList();
        Modification modification = new Modification(new Date(), "URL");
        original.add(modification);

        return original;
    }
}
