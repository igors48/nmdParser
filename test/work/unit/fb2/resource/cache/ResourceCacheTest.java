package work.unit.fb2.resource.cache;

import converter.format.fb2.resource.resolver.cache.InMemoryStorageAdapter;
import converter.format.fb2.resource.resolver.cache.StandardResourceCache;
import converter.format.fb2.resource.resolver.cache.StorageAdapter;
import http.Data;
import http.data.MemoryData;
import junit.framework.TestCase;
import timeservice.StandardTimeService;
import timeservice.StillTimeService;
import timeservice.TimeService;

import java.util.Arrays;

/**
 * @author Igor Usenko
 *         Date: 06.11.2009
 */
public class ResourceCacheTest extends TestCase {

    public ResourceCacheTest(final String _s) {
        super(_s);
    }

    // �������������� ����

    public void testSmoke() throws Data.DataException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        TimeService timeService = new StandardTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 1000, 1000, 100);

        byte[] buffer = new byte[3];
        buffer[0] = 1;
        buffer[1] = 2;
        buffer[2] = 3;

        Data data = new MemoryData(buffer);

        cache.put("url", data);
        Data loaded = cache.get("url");

        assertNotNull(loaded);
        assertTrue(Arrays.equals(buffer, loaded.getData()));
    }

    // ��� ������ ������

    public void testTwoRead() throws Data.DataException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        TimeService timeService = new StandardTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 1000, 1000, 100);

        byte[] buffer = new byte[3];
        buffer[0] = 1;
        buffer[1] = 2;
        buffer[2] = 3;

        Data data = new MemoryData(buffer);

        cache.put("url", data);
        cache.get("url");
        Data loaded = cache.get("url");

        assertNotNull(loaded);
        assertTrue(Arrays.equals(buffer, loaded.getData()));
    }

    // ������ ����� ������ ������

    public void testNewRewriteOld() throws Data.DataException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        TimeService timeService = new StandardTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 1000, 1000, 100);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;

        Data data01 = new MemoryData(buffer01);

        byte[] buffer02 = new byte[3];
        buffer02[0] = 4;
        buffer02[1] = 5;
        buffer02[2] = 6;

        Data data02 = new MemoryData(buffer02);

        cache.put("url", data01);
        cache.put("url", data02);
        Data loaded = cache.get("url");

        assertNotNull(loaded);
        assertTrue(Arrays.equals(buffer02, loaded.getData()));
    }

    // ������������ ������
    /*public void testReserveMemory() throws Data.DataException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        TimeService timeService = new StandardTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 1000, 4, 3);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;

        Data data01 = new MemoryData(buffer01);

        byte[] buffer02 = new byte[3];
        buffer02[0] = 4;
        buffer02[1] = 5;
        buffer02[2] = 6;

        Data data02 = new MemoryData(buffer02);

        cache.put("url01", data01);
        cache.put("url02", data02);
        Data loaded01 = cache.get("url01");
        Data loaded02 = cache.get("url02");

        assertNull(loaded01);
        assertNotNull(loaded02);
        assertTrue(Arrays.equals(buffer02, loaded02.getData()));
    } */

    // �������� ������������ ������ ��� ������� �������� � ��� ������

    public void testRemoveStalledData() throws Data.DataException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        StillTimeService timeService = new StillTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 5, 4, 3);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;

        Data data01 = new MemoryData(buffer01);

        cache.put("url01", data01);

        timeService.changeSecond(10);

        Data loaded01 = cache.get("url01");

        assertNull(loaded01);
    }

    // ������� ������� ������������� ������� �� �����������

    public void testNoInsertBiggerThanMax() throws Data.DataException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        StillTimeService timeService = new StillTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 5, 4, 2);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;

        Data data01 = new MemoryData(buffer01);

        cache.put("url01", data01);
        Data loaded01 = cache.get("url01");

        assertNull(loaded01);
    }

    // ����� : �������� �����

    public void testRemoveOrphans() throws Data.DataException, StorageAdapter.StorageAdapterException {
        StorageAdapter adapter = new InMemoryStorageAdapter();
        StillTimeService timeService = new StillTimeService();

        byte[] buffer = new byte[3];
        buffer[0] = 1;
        buffer[1] = 2;
        buffer[2] = 3;
        Data data = new MemoryData(buffer);
        String name = adapter.store(data);

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 5, 4, 3);

        Data loaded = cache.get(name);

        assertNull(loaded);
        assertNull(adapter.load(name));
    }

    // ����� : ���� �������� �� ������� ���������, ��������, ��� �������� �� ������� ����������� ������

    public void testAudit() throws Data.DataException {
        InMemoryStorageAdapter adapter = new InMemoryStorageAdapter();
        StillTimeService timeService = new StillTimeService();

        byte[] buffer = new byte[3];
        buffer[0] = 1;
        buffer[1] = 2;
        buffer[2] = 3;
        Data data = new MemoryData(buffer);

        StandardResourceCache cache01 = new StandardResourceCache(adapter, timeService, 5, 4, 3);
        cache01.put("url", data);

        adapter.getItems().clear();

        StandardResourceCache cache02 = new StandardResourceCache(adapter, timeService, 5, 4, 3);
        Data loaded = cache02.get("url");

        assertNull(loaded);
    }

    // ����������� ������ ��������� �������� � ������� ������������ ����������� ��������

    public void testThisReadsPrevious() throws Data.DataException {
        InMemoryStorageAdapter adapter = new InMemoryStorageAdapter();
        StillTimeService timeService = new StillTimeService();

        byte[] buffer = new byte[3];
        buffer[0] = 1;
        buffer[1] = 2;
        buffer[2] = 3;
        Data data = new MemoryData(buffer);

        StandardResourceCache cache01 = new StandardResourceCache(adapter, timeService, 5, 4, 3);
        cache01.put("url", data);

        StandardResourceCache cache02 = new StandardResourceCache(adapter, timeService, 5, 4, 3);
        Data loaded = cache02.get("url");

        assertNotNull(loaded);
        assertTrue(Arrays.equals(buffer, loaded.getData()));
    }

    public void testReserveSpaceWhenAdapterFreeSpaceInsufficiently() {
        InMemoryStorageAdapter adapter = new InMemoryStorageAdapter();
        StillTimeService timeService = new StillTimeService();

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 5, 4, 3);

        byte[] buffer01 = new byte[3];
        buffer01[0] = 1;
        buffer01[1] = 2;
        buffer01[2] = 3;
        Data data01 = new MemoryData(buffer01);

        byte[] buffer02 = new byte[2];
        buffer02[0] = 1;
        buffer02[1] = 2;
        Data data02 = new MemoryData(buffer02);

        cache.put("data01", data01);

        adapter.setFreeSpace(1);

        cache.put("data02", data02);

        assertNull(cache.get("data01"));
        assertNotNull(cache.get("data02"));
    }

    // ���� ��������� ��� ���������� ������� ������� 01
    /* public void testSpaceLimitOne() throws Data.DataException, InterruptedException {
        InMemoryStorageAdapter adapter = new InMemoryStorageAdapter();
        TimeService timeService = new StandardTimeService();

        byte[] fixture = new byte[3];
        fixture[0] = 1;
        fixture[1] = 2;
        fixture[2] = 3;

        Data data01 = new MemoryData(fixture);
        Data data02 = new MemoryData(fixture);
        Data data03 = new MemoryData(fixture);
        Data data04 = new MemoryData(fixture);

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 1000, 9, 3);
        cache.put("url01", data01);
        Thread.sleep(50);
        cache.put("url02", data02);
        Thread.sleep(50);
        cache.put("url03", data03);
        Thread.sleep(50);
        cache.put("url04", data04);
        Thread.sleep(50);

        Data loaded01 = cache.get("url01");
        Data loaded02 = cache.get("url02");
        Data loaded03 = cache.get("url03");
        Data loaded04 = cache.get("url04");

        assertNull(loaded01);
        assertNotNull(loaded02);
        assertNotNull(loaded03);
        assertNotNull(loaded04);
    }*/

    // ���� ��������� ��� ���������� ������� ������� 02
    /* public void testSpaceLimitTwo() throws Data.DataException, InterruptedException {
        InMemoryStorageAdapter adapter = new InMemoryStorageAdapter();
        TimeService timeService = new StandardTimeService();

        byte[] fixture01 = new byte[3];
        fixture01[0] = 1;
        fixture01[1] = 2;
        fixture01[2] = 3;

        byte[] fixture02 = new byte[6];
        fixture02[0] = 1;
        fixture02[1] = 2;
        fixture02[2] = 3;
        fixture02[3] = 3;
        fixture02[4] = 3;
        fixture02[5] = 3;

        Data data01 = new MemoryData(fixture01);
        Data data02 = new MemoryData(fixture01);
        Data data03 = new MemoryData(fixture01);
        Data data04 = new MemoryData(fixture02);

        StandardResourceCache cache = new StandardResourceCache(adapter, timeService, 1000, 9, 8);
        cache.put("url01", data01);
        Thread.sleep(50);
        cache.put("url02", data02);
        Thread.sleep(50);
        cache.put("url03", data03);
        Thread.sleep(50);
        cache.put("url04", data04);
        Thread.sleep(50);

        Data loaded01 = cache.get("url01");
        Data loaded02 = cache.get("url02");
        Data loaded03 = cache.get("url03");
        Data loaded04 = cache.get("url04");

        assertNull(loaded01);
        assertNull(loaded02);
        assertNotNull(loaded03);
        assertNotNull(loaded04);
    }*/
}
