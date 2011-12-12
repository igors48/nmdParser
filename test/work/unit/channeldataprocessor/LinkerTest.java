package work.unit.channeldataprocessor;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.core.data.InterpreterData;
import constructor.objects.output.configuration.DateSectionMode;
import constructor.objects.output.core.linker.*;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import http.Data;
import junit.framework.TestCase;
import timeservice.StillTimeService;
import work.testutil.AtdcTestUtils;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 27.02.2011
 */
public class LinkerTest extends TestCase {

    public LinkerTest(final String _s) {
        super(_s);
    }

    public void testCreateFlatFromOneChannelDataFromNewToOld() throws Data.DataException {
        ChannelData channelData = createChannelDataWithSameDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(channelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.OFF, true);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        assertEquals(1, linkerSectionList.size());

        ContentSection linkerSection = (ContentSection) linkerSectionList.get(0);
        assertEquals(3, linkerSection.getContent().size());

        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(2)));
    }

    public void testCreateFlatFromOneChannelDataFromOldToNew() throws Data.DataException {
        ChannelData channelData = createChannelDataWithSameDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(channelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.OFF, false);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        assertEquals(1, linkerSectionList.size());

        ContentSection linkerSection = (ContentSection) linkerSectionList.get(0);
        assertEquals(3, linkerSection.getContent().size());

        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(2)));
    }

    public void testCreateFlatFromManyChannelDataFromNewToOld() throws Data.DataException {
        ChannelData firstChannelData = createChannelDataWithSameDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);
        ChannelData secondChannelData = createChannelDataWithSameDatedItems("title2", "firstName2", "lastName2", "sourceUrl2", "coverUrl2", 3, 5);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(firstChannelData);
        channelDataList.add(secondChannelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.OFF, true);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        assertEquals(1, linkerSectionList.size());

        ContentSection linkerSection = (ContentSection) linkerSectionList.get(0);
        assertEquals(6, linkerSection.getContent().size());

        assertEquals("data5", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data4", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
        assertEquals("data3", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(2)));
        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(3)));
        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(4)));
        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(5)));
    }

    public void testCreateFlatFromManyChannelDataFromOldToNew() throws Data.DataException {
        ChannelData firstChannelData = createChannelDataWithSameDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);
        ChannelData secondChannelData = createChannelDataWithSameDatedItems("title2", "firstName2", "lastName2", "sourceUrl2", "coverUrl2", 3, 5);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(firstChannelData);
        channelDataList.add(secondChannelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.OFF, false);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        assertEquals(1, linkerSectionList.size());

        ContentSection linkerSection = (ContentSection) linkerSectionList.get(0);
        assertEquals(6, linkerSection.getContent().size());

        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(2)));
        assertEquals("data3", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(3)));
        assertEquals("data4", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(4)));
        assertEquals("data5", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(5)));
    }

    public void testCreateDatedFromManyChannelDataFromOldToNewSameDate() throws Data.DataException {
        ChannelData firstChannelData = createChannelDataWithSameDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);
        ChannelData secondChannelData = createChannelDataWithSameDatedItems("title2", "firstName2", "lastName2", "sourceUrl2", "coverUrl2", 3, 5);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(firstChannelData);
        channelDataList.add(secondChannelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.ALWAYS, false);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        assertEquals(1, linkerSectionList.size());

        DateSection linkerSection = (DateSection) linkerSectionList.get(0);
        assertEquals(6, linkerSection.getContent().size());

        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(2)));
        assertEquals("data3", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(3)));
        assertEquals("data4", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(4)));
        assertEquals("data5", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(5)));
    }

    public void testCreateDatedFromManyChannelDataFromNewToOldSameDate() throws Data.DataException {
        ChannelData firstChannelData = createChannelDataWithSameDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);
        ChannelData secondChannelData = createChannelDataWithSameDatedItems("title2", "firstName2", "lastName2", "sourceUrl2", "coverUrl2", 3, 5);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(firstChannelData);
        channelDataList.add(secondChannelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.ALWAYS, true);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        assertEquals(1, linkerSectionList.size());

        DateSection linkerSection = (DateSection) linkerSectionList.get(0);
        assertEquals(6, linkerSection.getContent().size());

        assertEquals("data5", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data4", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
        assertEquals("data3", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(2)));
        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(3)));
        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(4)));
        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(5)));
    }

    public void testCreateDatedFromManyChannelDataFromNewToOldDifferentDates() throws Data.DataException {
        ChannelData firstChannelData = createChannelDataWithDifferentDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);
        ChannelData secondChannelData = createChannelDataWithDifferentDatedItems("title2", "firstName2", "lastName2", "sourceUrl2", "coverUrl2", 3, 5);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(firstChannelData);
        channelDataList.add(secondChannelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.ALWAYS, true);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        // date sections sort
        assertEquals(3, linkerSectionList.size());

        assertTrue(((DateSection) linkerSectionList.get(0)).getDate().getTime() > ((DateSection) linkerSectionList.get(1)).getDate().getTime());
        assertTrue(((DateSection) linkerSectionList.get(1)).getDate().getTime() > ((DateSection) linkerSectionList.get(2)).getDate().getTime());

        // date sections content
        DateSection linkerSection = (DateSection) linkerSectionList.get(0);
        assertEquals(2, linkerSection.getContent().size());

        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data5", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));

        linkerSection = (DateSection) linkerSectionList.get(1);
        assertEquals(2, linkerSection.getContent().size());

        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data4", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));

        linkerSection = (DateSection) linkerSectionList.get(2);
        assertEquals(2, linkerSection.getContent().size());

        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data3", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
    }

    public void testCreateDatedFromManyChannelDataFromOldToNewDifferentDates() throws Data.DataException {
        ChannelData firstChannelData = createChannelDataWithDifferentDatedItems("title", "firstName", "lastName", "sourceUrl", "coverUrl", 0, 2);
        ChannelData secondChannelData = createChannelDataWithDifferentDatedItems("title2", "firstName2", "lastName2", "sourceUrl2", "coverUrl2", 3, 5);

        ChannelDataList channelDataList = new ChannelDataList();
        channelDataList.add(firstChannelData);
        channelDataList.add(secondChannelData);

        RootSection rootSection = Linker.create(channelDataList, DateSectionMode.ALWAYS, false);
        List<LinkerSection> linkerSectionList = rootSection.getChildren();

        // date sections sort
        assertEquals(3, linkerSectionList.size());

        assertTrue(((DateSection) linkerSectionList.get(0)).getDate().getTime() < ((DateSection) linkerSectionList.get(1)).getDate().getTime());
        assertTrue(((DateSection) linkerSectionList.get(1)).getDate().getTime() < ((DateSection) linkerSectionList.get(2)).getDate().getTime());

        // date sections content
        DateSection linkerSection = (DateSection) linkerSectionList.get(0);
        assertEquals(2, linkerSection.getContent().size());

        assertEquals("data0", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data3", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));

        linkerSection = (DateSection) linkerSectionList.get(1);
        assertEquals(2, linkerSection.getContent().size());

        assertEquals("data1", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data4", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));

        linkerSection = (DateSection) linkerSectionList.get(2);
        assertEquals(2, linkerSection.getContent().size());

        assertEquals("data2", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(0)));
        assertEquals("data5", AtdcTestUtils.getContentString((AtdcItem) linkerSection.getContent().get(1)));
    }

    private ChannelData createChannelDataWithSameDatedItems(final String _title, final String _firstName, final String _lastName, final String _sourceUrl, final String _coverUrl, final int _from, final int _to) {
        ChannelDataHeader header = new ChannelDataHeader(_title, _firstName, _lastName, _sourceUrl, _coverUrl);

        InterpreterData interpreterData = createSameDatedInterpreterItems(_from, _to);
        List<InterpreterData> interpreterDataList = newArrayList();
        interpreterDataList.add(interpreterData);

        return new ChannelData(header, interpreterDataList);
    }

    private ChannelData createChannelDataWithDifferentDatedItems(final String _title, final String _firstName, final String _lastName, final String _sourceUrl, final String _coverUrl, final int _from, final int _to) {
        ChannelDataHeader header = new ChannelDataHeader(_title, _firstName, _lastName, _sourceUrl, _coverUrl);

        InterpreterData interpreterData = createDifferentDatesInterpreterItems(_from, _to);
        List<InterpreterData> interpreterDataList = newArrayList();
        interpreterDataList.add(interpreterData);

        return new ChannelData(header, interpreterDataList);
    }

    private InterpreterData createSameDatedInterpreterItems(final int _from, final int _to) {
        List<DatedItem> datedItemList = newArrayList();

        for (int i = _from; i <= _to; ++i) {
            datedItemList.add(AtdcTestUtils.getAtdcFullItem(i));
        }

        return new InterpreterData(datedItemList);
    }

    private InterpreterData createDifferentDatesInterpreterItems(final int _from, final int _to) {
        List<DatedItem> datedItemList = newArrayList();

        StillTimeService timeService = new StillTimeService();

        for (int i = _from; i <= _to; ++i) {
            datedItemList.add(AtdcTestUtils.getAtdcFullItem(timeService, i));

            timeService.changeDay(1);
        }

        return new InterpreterData(datedItemList);
    }
}
