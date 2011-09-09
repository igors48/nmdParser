package work.unit.channeldatalistfilestorage;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.channel.core.storage.ChannelDataListFileStorage;
import constructor.objects.channel.core.stream.ChannelDataList;
import junit.framework.TestCase;
import static work.testutil.ChannelDataTestUtils.channelDataEquals;
import static work.testutil.ChannelDataTestUtils.createChannelDataFixture01;
import work.testutil.CompTestsUtils;
import static work.testutil.CompTestsUtils.cleanupDir;

import java.io.IOException;

/**
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public class ChannelDataListFileStorageTest extends TestCase {

    public static String DIR = CompTestsUtils.getCompTestsRoot() + "ChannelDataListFileStorageTest\\";

    public ChannelDataListFileStorageTest(String _s) {
        super(_s);
    }

    // тест возврата пустого списка если ранее он не сохранялся
    public void testNonExistent() throws IOException, ChannelDataListStorage.ChannelDataListStorageException {
        cleanupDir(DIR);

        ChannelDataListFileStorage storage = new ChannelDataListFileStorage(DIR);
        ChannelDataList result = storage.load("nonexistent");

        assertTrue(result != null);
        assertEquals(0, result.size());

        cleanupDir(DIR);
    }

    // тест сохранения/восстановления
    public void testStoreLoad() throws IOException, ChannelDataListStorage.ChannelDataListStorageException {
        cleanupDir(DIR);

        ChannelDataList original = createChannelDataList();

        ChannelDataListFileStorage storage = new ChannelDataListFileStorage(DIR);
        storage.store("sample", original);

        ChannelDataList result = storage.load("sample");

        assertTrue(result != null);
        assertEquals(original.size(), result.size());
        channelDataEquals(original.get(0), result.get(0));

        cleanupDir(DIR);
    }

    // тест на удаление
    public void testRemove() throws IOException, ChannelDataListStorage.ChannelDataListStorageException {
        cleanupDir(DIR);

        ChannelDataList original = createChannelDataList();

        ChannelDataListFileStorage storage = new ChannelDataListFileStorage(DIR);
        storage.store("sample", original);

        storage.remove("sample");

        ChannelDataList result = storage.load("sample");

        assertTrue(result != null);
        assertEquals(0, result.size());

        cleanupDir(DIR);
    }

    private ChannelDataList createChannelDataList() {
        ChannelData data = createChannelDataFixture01();

        ChannelDataList result = new ChannelDataList();
        result.add(data);

        return result;
    }

}
