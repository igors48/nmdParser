package work.testutil;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.interpreter.core.data.InterpreterData;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 27.12.2008
 */
public final class ChannelDataTestUtils {

    private static final String COVER_URL = "COVER";

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

        List<DatedItem> list01 = newArrayList();
        list01.add(item101);
        list01.add(item102);

        InterpreterData original01 = new InterpreterData();
        original01.setId("Id1");
        original01.setItems(list01);

        AtdcItem item201 = AtdcTestUtils.getAtdcFullItem(3);
        AtdcItem item202 = AtdcTestUtils.getAtdcFullItem(4);

        List<DatedItem> list02 = newArrayList();
        list02.add(item201);
        list02.add(item202);

        InterpreterData original02 = new InterpreterData();
        original02.setId("Id2");
        original02.setItems(list02);

        List<InterpreterData> list = newArrayList();
        list.add(original01);
        list.add(original02);
        return list;
    }

    private static List<InterpreterData> getList02() {
        AtdcItem item101 = AtdcTestUtils.getAtdcFullItem(56);
        AtdcItem item102 = AtdcTestUtils.getAtdcFullItem(57);

        List<DatedItem> list01 = newArrayList();
        list01.add(item101);
        list01.add(item102);

        InterpreterData original01 = new InterpreterData();
        original01.setId("Id143");
        original01.setItems(list01);

        AtdcItem item201 = AtdcTestUtils.getAtdcFullItem(58);
        AtdcItem item202 = AtdcTestUtils.getAtdcFullItem(59);

        List<DatedItem> list02 = newArrayList();
        list02.add(item201);
        list02.add(item202);

        InterpreterData original02 = new InterpreterData();
        original02.setId("Id144");
        original02.setItems(list02);

        List<InterpreterData> list = newArrayList();
        list.add(original01);
        list.add(original02);
        return list;
    }

    private static ChannelDataHeader getHeader01() {
        List<String> genres = newArrayList();
        genres.add("genre1");
        genres.add("genre2");

        return new ChannelDataHeader("TITLE", "FIRST", "LAST", "SOURCE", COVER_URL, genres, "fr");
    }

    private static ChannelDataHeader getHeader02() {
        List<String> genres = newArrayList();
        genres.add("genre3");
        genres.add("genre4");
        genres.add("genre5");
        genres.add("genre6");

        return new ChannelDataHeader("_TITLE", "_FIRST", "_LAST", "_SOURCE", COVER_URL, genres, "tu");
    }

    private static ChannelDataHeader getHeader03() {
        List<String> genres = newArrayList();
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

    private ChannelDataTestUtils() {
        // empty
    }
}
