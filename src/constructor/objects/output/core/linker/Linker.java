package constructor.objects.output.core.linker;

import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.configuration.DateSectionMode;
import dated.DatedComparator;
import dated.DatedItem;
import util.Assert;

import java.util.*;

/**
 * Строит структуру документа из списков данных канала или каналов.
 *
 * @author Igor Usenko
 *         Date: 26.02.2011
 */
public class Linker {

    public static RootSection create(final ChannelDataList _source, final DateSectionMode _dateSectionMode, final boolean _fromNewToOld) {
        Assert.notNull(_source, "Source is null");
        Assert.notNull(_dateSectionMode, "Date section mode is null");

        Set<Date> dateSet = ChannelDataTools.getDateSet(_source);

        return adjustDateSectionMode(_dateSectionMode, dateSet) == DateSectionMode.ALWAYS ? createDated(_source, dateSet, _fromNewToOld) : createFlat(_source, _fromNewToOld);
    }

    private static RootSection createFlat(final ChannelDataList _source, final boolean _fromNewToOld) {
        RootSection result = new RootSection();

        List<DatedItem> items = ChannelDataTools.getFlat(_source);
        sort(items, _fromNewToOld);

        result.addContentSection(new ContentSection(items));

        return result;
    }

    private static RootSection createDated(final ChannelDataList _source, final Set<Date> _dateSet, final boolean _fromNewToOld) {
        RootSection result = new RootSection();

        List<Date> dates = createDatesList(_dateSet, _fromNewToOld);

        for (Date current : dates) {
            List<DatedItem> candidates = ChannelDataTools.getForDate(_source, current);
            sort(candidates, _fromNewToOld);

            result.addDateSection(new DateSection(current, candidates));
        }

        return result;
    }

    private static List<Date> createDatesList(final Set<Date> _dateSet, final boolean _fromNewToOld) {
        List<Date> dates = Arrays.asList(_dateSet.toArray(new Date[_dateSet.size()]));
        Collections.sort(dates);

        if (_fromNewToOld) {
            Collections.reverse(dates);
        }

        return dates;
    }

    private static DateSectionMode adjustDateSectionMode(final DateSectionMode _dateSectionMode, final Set<Date> dateSet) {
        DateSectionMode result = _dateSectionMode;

        if (_dateSectionMode == DateSectionMode.AUTO) {
            result = dateSet.size() == 1 ? DateSectionMode.OFF : DateSectionMode.ALWAYS;
        }

        return result;
    }

    private static void sort(final List<DatedItem> items, final boolean _fromNewToOld) {
        Collections.sort(items, _fromNewToOld ? Collections.reverseOrder(new DatedComparator()) : new DatedComparator());
    }
}