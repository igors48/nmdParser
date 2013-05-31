package work.unit.fb2.resource.cache;

import converter.format.fb2.resource.resolver.cache.CacheEntry;
import converter.format.fb2.resource.resolver.cache.InDirectoryStorageAdapter;
import converter.format.fb2.resource.resolver.cache.StorageAdapter;
import converter.format.fb2.resource.resolver.cache.StoredItem;
import http.Data;
import http.data.MemoryData;
import junit.framework.TestCase;
import work.testutil.CompTestsUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * @author Igor Usenko
 *         Date: 08.11.2009
 */
public class InDirectoryStorageAdapterTest extends TestCase {

    public static String TEST_ROOT = CompTestsUtils.getCompTestsRoot() + "InDirectoryStorageAdapterTest/";

    public InDirectoryStorageAdapterTest(final String _s) {
        super(_s);
    }

    // �������������� ���� ���������� � ������ ������� ��������� ����

    public void testStoreLoadToc() throws IOException, StorageAdapter.StorageAdapterException {
        String dir = TEST_ROOT + "testStoreLoadToc/";
        CompTestsUtils.cleanupDir(dir);

        Map<String, CacheEntry> entries = newHashMap();

        CacheEntry entry01 = new CacheEntry();
        entry01.setAddress("address01");
        entry01.setCreated(1);
        entry01.setFile("file01");
        entries.put("address01", entry01);

        CacheEntry entry02 = new CacheEntry();
        entry01.setAddress("address02");
        entry01.setCreated(2);
        entry01.setFile("file02");
        entries.put("address02", entry02);

        InDirectoryStorageAdapter adapter = new InDirectoryStorageAdapter(dir);

        adapter.storeToc(entries);
        Map<String, CacheEntry> loaded = adapter.loadToc();

        assertNotNull(loaded);
        assertEquals(2, loaded.size());
        assertTrue(entry01.equals(loaded.get("address01")));
        assertTrue(entry02.equals(loaded.get("address02")));
    }

    // ������� ������ TOC ����� ��� ��� 

    public void testNoToc() throws IOException, StorageAdapter.StorageAdapterException {
        String dir = TEST_ROOT + "testNoToc/";
        CompTestsUtils.cleanupDir(dir);

        InDirectoryStorageAdapter adapter = new InDirectoryStorageAdapter(dir);
        Map<String, CacheEntry> loaded = adapter.loadToc();

        assertNotNull(loaded);
        assertEquals(0, loaded.size());
    }

    // ���������� � �������� ������

    public void testStoreLoadData() throws IOException, StorageAdapter.StorageAdapterException, Data.DataException {
        String dir = TEST_ROOT + "testStoreLoadData/";
        CompTestsUtils.cleanupDir(dir);

        InDirectoryStorageAdapter adapter = new InDirectoryStorageAdapter(dir);

        byte[] buffer = new byte[3];
        buffer[0] = 1;
        buffer[1] = 2;
        buffer[2] = 3;
        Data data = new MemoryData(buffer);

        String id = adapter.store(data);
        Data loaded = adapter.load(id);

        assertNotNull(loaded);
        assertTrue(Arrays.equals(buffer, loaded.getData()));
    }

    // ��������� ����� ����������� ������

    public void testGetMap() throws IOException, StorageAdapter.StorageAdapterException, Data.DataException {
        String dir = TEST_ROOT + "testStoreLoadData/";
        CompTestsUtils.cleanupDir(dir);

        InDirectoryStorageAdapter adapter = new InDirectoryStorageAdapter(dir);

        Map<String, CacheEntry> entries = newHashMap();

        CacheEntry entry01 = new CacheEntry();
        entry01.setAddress("address01");
        entry01.setCreated(1);
        entry01.setFile("file01");
        entries.put("address01", entry01);

        adapter.storeToc(entries);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;
        Data data01 = new MemoryData(buffer01);

        byte[] buffer02 = new byte[4];
        buffer02[0] = 1;
        buffer02[1] = 2;
        buffer02[2] = 3;
        buffer02[3] = 4;
        Data data02 = new MemoryData(buffer02);

        byte[] buffer03 = new byte[5];
        buffer03[0] = 1;
        buffer03[1] = 2;
        buffer03[2] = 3;
        buffer03[3] = 4;
        buffer03[4] = 5;
        Data data03 = new MemoryData(buffer03);

        String id01 = adapter.store(data01);
        String id02 = adapter.store(data02);
        String id03 = adapter.store(data03);

        Map<String, StoredItem> map = adapter.getMap();

        assertNotNull(map);
        assertEquals(3, map.size());
    }

    // ��������� �������� ����������� ������

    public void testRemoveData() throws IOException, StorageAdapter.StorageAdapterException, Data.DataException {
        String dir = TEST_ROOT + "testStoreLoadData/";
        CompTestsUtils.cleanupDir(dir);

        InDirectoryStorageAdapter adapter = new InDirectoryStorageAdapter(dir);

        Map<String, CacheEntry> entries = newHashMap();

        CacheEntry entry01 = new CacheEntry();
        entry01.setAddress("address01");
        entry01.setCreated(1);
        entry01.setFile("file01");
        entries.put("address01", entry01);

        adapter.storeToc(entries);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;
        Data data01 = new MemoryData(buffer01);

        byte[] buffer02 = new byte[4];
        buffer02[0] = 1;
        buffer02[1] = 2;
        buffer02[2] = 3;
        buffer02[3] = 4;
        Data data02 = new MemoryData(buffer02);

        byte[] buffer03 = new byte[5];
        buffer03[0] = 1;
        buffer03[1] = 2;
        buffer03[2] = 3;
        buffer03[3] = 4;
        buffer03[4] = 5;
        Data data03 = new MemoryData(buffer03);

        String id01 = adapter.store(data01);
        String id02 = adapter.store(data02);
        String id03 = adapter.store(data03);

        adapter.remove(id02);
        Map<String, StoredItem> map = adapter.getMap();

        assertNotNull(map);
        assertEquals(2, map.size());
        assertNotNull(map.get(id01));
        assertNull(map.get(id02));
        assertNotNull(map.get(id03));
    }
}
