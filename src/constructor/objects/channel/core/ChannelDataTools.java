package constructor.objects.channel.core;

import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.core.data.InterpreterData;
import constructor.objects.interpreter.core.data.InterpreterDataTools;
import dated.DatedItem;
import dated.DatedTools;
import downloader.BatchLoader;
import downloader.Data;
import downloader.data.DataUtil;
import html.HttpData;
import util.Assert;

import java.util.*;

/**
 * Утилитный класс для работы с данными канала
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public final class ChannelDataTools {

    public static final String DEFAULT_GENRE = "science";
    public static final String DEFAULT_LANG = "ru";

    public static final List<String> DEFAULT_GENRES = new ArrayList<String>();

    public static String loadImage(final String _url, final BatchLoader _loader, final long _pauseBetweenRequests) throws Data.DataException {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_loader, "Loader is null");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        List<String> urls = new ArrayList<String>();
        urls.add(_url);

        Map<String, HttpData> datas = _loader.loadUrls(urls, _pauseBetweenRequests);

        return DataUtil.getDataImage(datas.get(_url).getData());
    }

    public static DatedItem getLatestItem(final ChannelData _data) {
        Assert.notNull(_data, "Channel data is null.");

        DatedItem result = null;

        if (!_data.isEmpty()) {

            for (int index = 0; index < _data.getData().size(); ++index) {
                DatedItem current = InterpreterDataTools.getLatestItem(_data.getData().get(index));

                if (current != null) {

                    if (result == null) {
                        result = current;
                    } else {
                        result = (DatedItem) DatedTools.latest(result, current);
                    }
                }
            }
        }

        return result;
    }

    public static DatedItem getLatestItem(final ChannelDataList _data) {
        Assert.notNull(_data, "Channel data list is null.");

        DatedItem result = null;

        if (!_data.isEmpty()) {

            for (int index = 0; index < _data.size(); ++index) {
                DatedItem current = getLatestItem(_data.get(index));

                if (current != null) {

                    if (result == null) {
                        result = current;
                    } else {
                        result = (DatedItem) DatedTools.latest(result, current);
                    }
                }
            }
        }

        return result;
    }

    public static Set<Date> getDateSet(final ChannelDataList _data) {
        Assert.notNull(_data, "Channel data list is null.");

        Set<Date> result = new HashSet<Date>();

        if (!_data.isEmpty()) {

            for (int index = 0; index < _data.size(); ++index) {
                result.addAll(getDateSet(_data.get(index)));
            }
        }

        return result;
    }

    public static Set<Date> getDateSet(final ChannelData _data) {
        Assert.notNull(_data, "Channel data is null.");

        Set<Date> result = new HashSet<Date>();

        if (!_data.isEmpty()) {

            for (InterpreterData current : _data.getData()) {
                result.addAll(InterpreterDataTools.getDateSet(current));
            }
        }

        return result;
    }

    public static List<DatedItem> getFlat(final ChannelDataList _data) {
        Assert.notNull(_data, "Channel data list is null.");

        List<DatedItem> result = new ArrayList<DatedItem>();

        if (!_data.isEmpty()) {

            for (int index = 0; index < _data.size(); ++index) {
                result.addAll(getFlat(_data.get(index)));
            }
        }

        return result;
    }

    public static List<DatedItem> getFlat(final ChannelData _data) {
        Assert.notNull(_data, "Channel data is null.");

        List<DatedItem> result = new ArrayList<DatedItem>();

        if (!_data.isEmpty()) {

            for (InterpreterData current : _data.getData()) {
                result.addAll(current.getItems());
            }
        }

        return result;
    }

    public static List<DatedItem> getForDate(final ChannelDataList _data, final Date _date) {
        Assert.notNull(_data, "Channel data list is null.");
        Assert.notNull(_date, "Date is null");

        List<DatedItem> result = new ArrayList<DatedItem>();

        if (!_data.isEmpty()) {

            for (int index = 0; index < _data.size(); ++index) {
                result.addAll(getForDate(_data.get(index), _date));
            }
        }

        return result;
    }

    public static List<DatedItem> getForDate(final ChannelData _data, final Date _date) {
        Assert.notNull(_data, "Channel data list is null.");
        Assert.notNull(_date, "Date is null");

        List<DatedItem> result = new ArrayList<DatedItem>();

        if (!_data.isEmpty()) {

            for (InterpreterData current : _data.getData()) {
                result.addAll(current.getForDate(_date));
            }
        }

        return result;
    }

    private ChannelDataTools() {
        // empty
    }
}
