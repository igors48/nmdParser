package util.sequense;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * √енератор последовательности строк. Ќа вход шаблон строки,
 * с указанным местом вставки номера и параметры формировани€ номера
 * см NumericSequencer. Ќа выходе список строк с подставленными в исходный
 * шаблон номерами
 *
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public final class StringSequencer {

    private static final String WILDCARD = "*";
    private static final String ESCAPED = "\\";

    public static List<String> getSequence(final String _pattern, final int _start, final int _stop, final int _step, final int _mult, final int _len, final String _pad) {
        Assert.isValidString(_pattern, "Pattern is not valid.");
        Assert.greaterOrEqual(_len, 0, "Length < 0");
        Assert.notNull(_pad, "Padding sequence is null");

        List<String> result = newArrayList();

        if (_pattern.contains(WILDCARD)) {
            NumericSequencer sequencer = new NumericSequencer(_start, _stop, _step, _mult);

            while (sequencer.hasNext()) {
                String buffer = _pattern.replaceAll(ESCAPED + WILDCARD, getPadded(sequencer.getNext(), _len, _pad));
                result.add(buffer);
            }

        }

        if (result.isEmpty()) {
            result.add(removeWildcards(_pattern));
        }

        return result;
    }

    public static String removeWildcards(final String _pattern) {
        Assert.isValidString(_pattern, "Pattern is not valid.");

        return _pattern.replaceAll(ESCAPED + WILDCARD, "");
    }

    private static String getPadded(final int _source, final int _len, final String _pad) {
        String padding = preparePadding(_pad.isEmpty() ? "0" : _pad, _len);
        String value = String.valueOf(_source);

        String result = padding + value;
        int len = Math.max(value.length(), _len);

        return result.substring(result.length() - len);
    }

    private static String preparePadding(final String _pad, final int _len) {
        StringBuilder result = new StringBuilder(_pad);

        while (result.length() < _len) {
            result.append(_pad);
        }

        return result.toString();
    }

    private StringSequencer() {
        // empty
    }
}
