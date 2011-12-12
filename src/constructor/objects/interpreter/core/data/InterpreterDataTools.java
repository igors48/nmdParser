package constructor.objects.interpreter.core.data;

import dated.DatedItem;
import dated.DatedItemTools;
import util.Assert;
import util.DateTools;

import java.util.Date;
import java.util.Set;

import static util.CollectionUtils.newHashSet;

/**
 * Утилитный класс для работы с данными интерпретатора
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public final class InterpreterDataTools {

    public static DatedItem getLatestItem(final InterpreterData _data) {
        Assert.notNull(_data, "Interpreter data is null.");

        return DatedItemTools.getLatestItem(_data.getItems());
    }

    public static Set<Date> getDateSet(final InterpreterData _data) {
        Assert.notNull(_data, "Interpreter data is null.");

        Set<Date> result = newHashSet();

        for (DatedItem item : _data.getItems()) {
            result.add(DateTools.getDateWithoutTime(item.getDate()));
        }

        return result;
    }

    private InterpreterDataTools() {
        // empty
    }
}
