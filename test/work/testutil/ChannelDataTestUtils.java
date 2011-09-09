package work.testutil;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.core.data.InterpreterData;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import timeservice.StillTimeService;
import timeservice.TimeService;
import util.Assert;
import util.DateTools;

import java.util.*;

/**
 * @author Igor Usenko
 *         Date: 27.12.2008
 */
public final class ChannelDataTestUtils {

    private static final String ONE_CHANNEL_ONE_ITEM = "ONE_CHANNEL_ONE_ITEM";
    private static final String ONE_INTERPRETER = "ONE_INTERPRETER";

    private static final String MANY_CHANNEL_MANY_ITEM = "MANY_CHANNEL_MANY_ITEM";
    private static final String MANY_INTERPRETER = "MANY_INTERPRETER";

    private static final String MANY_CHANNEL_MANY_ITEM_URL = "URL";

    private static final String COVER_URL = "COVER";

    public static int getDatedItemsCount(final List<ChannelData> channelData, final int _channelIndex, final int _interpreterIndex) {
        return channelData.get(_channelIndex).getData().get(_interpreterIndex).getItems().size();
    }

    public static int getInterpreterCount(final List<ChannelData> _channelData, final int _channelIndex) {
        return _channelData.get(_channelIndex).getData().size();
    }

    public static InterpreterData getInterpreter(final List<ChannelData> _channelData, final int _channelIndex, final int _interpreterIndex) {
        return _channelData.get(_channelIndex).getData().get(_interpreterIndex);
    }

    public static int calculateDateCount(final InterpreterData _data) {
        Map<Date, Integer> map = new HashMap<Date, Integer>();

        for (DatedItem item : _data.getItems()) {
            Date date = DateTools.getDateWithoutTime(item.getDate());

            Integer entry = map.get(date);

            if (entry == null) {
                map.put(date, 1);
            } else {
                map.put(date, ++entry);
            }
        }

        return map.size();
    }

    public static List<ChannelData> getManyChannelOneInterpreterManySameDateItem(final TimeService _timeService, final int _channelCount, final int _dataCount) {
        List<ChannelData> result = new ArrayList<ChannelData>();

        for (int channelIndex = 0; channelIndex < _channelCount; ++channelIndex) {
            InterpreterData interpreterData = getInterpreterDataSameDate(_timeService, ONE_INTERPRETER, _dataCount);

            List<InterpreterData> list = new ArrayList<InterpreterData>();
            list.add(interpreterData);

            ChannelDataHeader header = new ChannelDataHeader(ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, COVER_URL);
            result.add(new ChannelData(header, list));
        }

        return result;
    }

    public static List<ChannelData> getManyChannelOneInterpreterManyDifferDateItem(final TimeService _timeService, final int _channelCount, final int _dataCount) {
        List<ChannelData> result = new ArrayList<ChannelData>();

        for (int channelIndex = 0; channelIndex < _channelCount; ++channelIndex) {
            InterpreterData interpreterData = getInterpreterDataDifferDate(_timeService, ONE_INTERPRETER, _dataCount);

            List<InterpreterData> list = new ArrayList<InterpreterData>();
            list.add(interpreterData);

            ChannelDataHeader header = new ChannelDataHeader(ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, COVER_URL);
            result.add(new ChannelData(header, list));
        }

        return result;
    }

    public static List<ChannelData> getOneChannelOneInterpreterManyDifferDateItem(final TimeService _timeService, final int _dataCount) {
        List<ChannelData> result = new ArrayList<ChannelData>();

        InterpreterData interpreterData = getInterpreterDataDifferDate(_timeService, ONE_INTERPRETER, _dataCount);

        List<InterpreterData> list = new ArrayList<InterpreterData>();
        list.add(interpreterData);

        ChannelDataHeader header = new ChannelDataHeader(ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, COVER_URL);
        result.add(new ChannelData(header, list));

        return result;
    }

    public static List<ChannelData> getOneChannelOneInterpreterManySameDateItem(final TimeService _timeService, final int _count) {
        List<ChannelData> result = new ArrayList<ChannelData>();

        InterpreterData interpreterData = getInterpreterDataSameDate(_timeService, ONE_INTERPRETER, _count);

        List<InterpreterData> list = new ArrayList<InterpreterData>();
        list.add(interpreterData);

        ChannelDataHeader header = new ChannelDataHeader(ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, ONE_CHANNEL_ONE_ITEM, COVER_URL);
        result.add(new ChannelData(header, list));

        return result;
    }

    public static List<ChannelData> getManyChannelManyInterpreterManySameDateItem(final TimeService _timeService, final int _channelCount, final int _interpreterPerChannel, final int _itemPerInterpreter) {
        List<ChannelData> result = new ArrayList<ChannelData>();

        for (int channelIndex = 0; channelIndex < _channelCount; ++channelIndex) {

            List<InterpreterData> list = new ArrayList<InterpreterData>();

            for (int interpreterIndex = 0; interpreterIndex < _interpreterPerChannel; ++interpreterIndex) {
                InterpreterData interpreterData = getInterpreterDataSameDate(_timeService, MANY_INTERPRETER + interpreterIndex, _itemPerInterpreter);

                list.add(interpreterData);
            }

            ChannelDataHeader header = new ChannelDataHeader(MANY_CHANNEL_MANY_ITEM + channelIndex, MANY_CHANNEL_MANY_ITEM + channelIndex, MANY_CHANNEL_MANY_ITEM + channelIndex, MANY_CHANNEL_MANY_ITEM_URL + channelIndex, COVER_URL);
            result.add(new ChannelData(header, list));
        }

        return result;
    }

    public static ChannelData createChannelDataFixture01() {
        ChannelDataHeader header = getHeader01();
        List<InterpreterData> list = getList01();

        return new ChannelData(header, list);
    }

    public static ChannelData createChannelDataFixture02() {
        ChannelDataHeader header = getHeader02();
        List<InterpreterData> list = getList01();

        return new ChannelData(header, list);
    }

    public static ChannelData createChannelDataFixture03() {
        ChannelDataHeader header = getHeader03();
        List<InterpreterData> list = getList02();

        return new ChannelData(header, list);
    }

    private static List<InterpreterData> getList01() {
        AtdcItem item101 = AtdcTestUtils.getAtdcFullItem(1);
        AtdcItem item102 = AtdcTestUtils.getAtdcFullItem(2);

        List<DatedItem> list01 = new ArrayList<DatedItem>();
        list01.add(item101);
        list01.add(item102);

        InterpreterData original01 = new InterpreterData();
        original01.setId("Id1");
        original01.setItems(list01);

        AtdcItem item201 = AtdcTestUtils.getAtdcFullItem(3);
        AtdcItem item202 = AtdcTestUtils.getAtdcFullItem(4);

        List<DatedItem> list02 = new ArrayList<DatedItem>();
        list02.add(item201);
        list02.add(item202);

        InterpreterData original02 = new InterpreterData();
        original02.setId("Id2");
        original02.setItems(list02);

        List<InterpreterData> list = new ArrayList<InterpreterData>();
        list.add(original01);
        list.add(original02);
        return list;
    }

    private static List<InterpreterData> getList02() {
        AtdcItem item101 = AtdcTestUtils.getAtdcFullItem(56);
        AtdcItem item102 = AtdcTestUtils.getAtdcFullItem(57);

        List<DatedItem> list01 = new ArrayList<DatedItem>();
        list01.add(item101);
        list01.add(item102);

        InterpreterData original01 = new InterpreterData();
        original01.setId("Id143");
        original01.setItems(list01);

        AtdcItem item201 = AtdcTestUtils.getAtdcFullItem(58);
        AtdcItem item202 = AtdcTestUtils.getAtdcFullItem(59);

        List<DatedItem> list02 = new ArrayList<DatedItem>();
        list02.add(item201);
        list02.add(item202);

        InterpreterData original02 = new InterpreterData();
        original02.setId("Id144");
        original02.setItems(list02);

        List<InterpreterData> list = new ArrayList<InterpreterData>();
        list.add(original01);
        list.add(original02);
        return list;
    }

    private static ChannelDataHeader getHeader01() {
        List<String> genres = new ArrayList<String>();
        genres.add("genre1");
        genres.add("genre2");

        return new ChannelDataHeader("TITLE", "FIRST", "LAST", "SOURCE", COVER_URL, genres, "fr");
    }

    private static ChannelDataHeader getHeader02() {
        List<String> genres = new ArrayList<String>();
        genres.add("genre3");
        genres.add("genre4");
        genres.add("genre5");
        genres.add("genre6");

        return new ChannelDataHeader("_TITLE", "_FIRST", "_LAST", "_SOURCE", COVER_URL, genres, "tu");
    }

    private static ChannelDataHeader getHeader03() {
        List<String> genres = new ArrayList<String>();
        genres.add("genre356");
        genres.add("genre456");
        genres.add("genre556");

        return new ChannelDataHeader("_TITLE_", "_FIRST_", "_LAST_", "_SOURCE_", COVER_URL, genres, "yu");
    }

    public static boolean channelDataEquals(final ChannelData _first, final ChannelData _second) {
        Assert.notNull(_first);
        Assert.notNull(_second);

        boolean result = true;

        if (!ChannelDataHeaderTestUtils.headerEquals(_first.getHeader(), _second.getHeader())) {
            result = false;
        }

        if (!interpreterDataListEquals(_first.getData(), _second.getData())) {
            result = false;
        }

        return result;
    }

    public static ChannelDataList convertToChannelDataList(final List<ChannelData> _list) {
        Assert.notNull(_list, "List is null.");

        ChannelDataList result = new ChannelDataList();
        result.addList(_list);

        return result;
    }

    private static boolean interpreterDataListEquals(final List<InterpreterData> _first, final List<InterpreterData> _second) {
        Assert.notNull(_first);
        Assert.notNull(_second);

        boolean result = true;

        if (_first.size() != _second.size()) {
            result = false;
        }

        if (result) {

            for (int index = 0; index < _first.size(); ++index) {
                InterpreterData firstData = _first.get(index);
                InterpreterData secondData = _second.get(index);

                if (!InterpreterDataTestUtils.interpreterDataEquals(firstData, secondData)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private static List<DatedItem> getDatedSameDateItemList(final TimeService _timeService, final int _count) {
        List<DatedItem> result = new ArrayList<DatedItem>();

        for (int index = 0; index < _count; ++index) {
            result.add(AtdcTestUtils.getAtdcNoTitleItem(_timeService, index));
        }

        return result;
    }

    private static List<DatedItem> getDatedDifferDateItemList(final TimeService _timeService, final int _count) {
        List<DatedItem> result = new ArrayList<DatedItem>();

        for (int dateIndex = 0; dateIndex < _count; ++dateIndex) {

            for (int itemIndex = 0; itemIndex < _count; ++itemIndex) {

                result.add(AtdcTestUtils.getAtdcNoTitleItem(_timeService, itemIndex));
                ((StillTimeService) _timeService).changeSecond(1000);
            }

            ((StillTimeService) _timeService).changeDay(2);
        }

        return result;
    }

    private static InterpreterData getInterpreterDataSameDate(final TimeService _timeService, final String _id, final int _count) {
        return new InterpreterData(getDatedSameDateItemList(_timeService, _count));
    }

    private static InterpreterData getInterpreterDataDifferDate(final TimeService _timeService, final String _id, final int _count) {
        return new InterpreterData(getDatedDifferDateItemList(_timeService, _count));
    }

    private ChannelDataTestUtils() {
        // empty
    }
}
