package converter.format.fb2.objects;

import converter.format.fb2.Stringable;
import util.Assert;

import java.util.*;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 08.08.2008
 */
public class Fb2Header implements Stringable {

    private static final String OPEN_TAG_SYMBOL = "<";
    private static final String CLOSE_TAG_SYMBOL = "</";

    private static final int INDENT_SIZE = 4;

    private static final String DATE_DELIMITER = "-";

    private static final String TEMPLATE_LINE_02 = "<description>";
    private static final String TEMPLATE_LINE_03 = "<title-info>";
    private static final String TEMPLATE_LINE_04 = "<genre>";
    private static final String TEMPLATE_LINE_05 = "</genre>";
    private static final String TEMPLATE_LINE_06 = "<author>";
    private static final String TEMPLATE_LINE_07 = "<first-name>";
    private static final String TEMPLATE_LINE_08 = "</first-name>";
    private static final String TEMPLATE_LINE_09 = "<last-name>";
    private static final String TEMPLATE_LINE_10 = "</last-name>";
    private static final String TEMPLATE_LINE_11 = "</author>";
    private static final String TEMPLATE_LINE_12 = "<book-title>";
    private static final String TEMPLATE_LINE_13 = "</book-title>";
    private static final String TEMPLATE_LINE_131 = "<coverpage>";
    private static final String TEMPLATE_LINE_132 = "<image l:href=\"#";
    private static final String TEMPLATE_LINE_133 = "\"/>";
    private static final String TEMPLATE_LINE_134 = "</coverpage>";
    private static final String TEMPLATE_LINE_14 = "<date>";
    private static final String TEMPLATE_LINE_15 = "</date>";
    private static final String TEMPLATE_LINE_16 = "<lang>";
    private static final String TEMPLATE_LINE_17 = "</lang>";
    private static final String TEMPLATE_LINE_18 = "</title-info>";
    private static final String TEMPLATE_LINE_19 = "<document-info>";
    private static final String TEMPLATE_LINE_20 = "<author>";
    private static final String TEMPLATE_LINE_21 = "<first-name>";
    private static final String TEMPLATE_LINE_22 = "</first-name>";
    private static final String TEMPLATE_LINE_23 = "<last-name>";
    private static final String TEMPLATE_LINE_24 = "</last-name>";
    private static final String TEMPLATE_LINE_25 = "</author>";
    private static final String TEMPLATE_LINE_26 = "<program-used>";
    private static final String TEMPLATE_LINE_27 = "</program-used>";
    private static final String TEMPLATE_LINE_28 = "<date value=\"";
    private static final String TEMPLATE_LINE_29 = "\">";
    private static final String TEMPLATE_LINE_30 = "</date>";
    private static final String TEMPLATE_LINE_31 = "<id>";
    private static final String TEMPLATE_LINE_32 = "</id>";
    private static final String TEMPLATE_LINE_33 = "<version>";
    private static final String TEMPLATE_LINE_34 = "</version>";
    private static final String TEMPLATE_LINE_35 = "</document-info>";
    private static final String TEMPLATE_LINE_36 = "</description>";

    private final List<String> genres;
    private final String authorFirstName;
    private final String authorLastName;
    private final String bookTitle;
    private final String coverTag;
    private final String programUsed;
    private final String lang;

    private final Date date;
    private final String id;
    private final String version;

    private int indent;

    public Fb2Header(final List<String> _genres, final String _authorFirstName, final String _authorLastName, final String _bookTitle, final String _coverTag, final String _lang, final String _programUsed, final Date _date, final String _id, final String _version) {
        Assert.notNull(_genres);
        Assert.isValidString(_authorFirstName);
        Assert.isValidString(_authorLastName);
        Assert.isValidString(_bookTitle);
        Assert.notNull(_coverTag);
        Assert.isValidString(_programUsed);
        Assert.isValidString(_id);
        Assert.isValidString(_lang);
        Assert.isValidString(_version);
        Assert.notNull(_date);

        this.genres = _genres;
        this.authorFirstName = _authorFirstName;
        this.authorLastName = _authorLastName;
        this.bookTitle = _bookTitle;
        this.coverTag = _coverTag;
        this.programUsed = _programUsed;
        this.lang = _lang;
        this.date = _date;
        this.id = _id;
        this.version = _version;


        this.indent = 0;
    }

    public String[] getStrings() {
        List<String> result = newArrayList();

        store(result);

        return result.toArray(new String[result.size()]);
    }

    private void store(final List<String> _buffer) {
        Assert.notNull(_buffer);

        writeTag(TEMPLATE_LINE_02, _buffer);
        writeTag(TEMPLATE_LINE_03, _buffer);

        //genre
        for (String genre : this.genres) {
            writeOneLineTag(TEMPLATE_LINE_04, genre, TEMPLATE_LINE_05, _buffer);
        }

        //author
        writeTag(TEMPLATE_LINE_06, _buffer);
        writeMultiLineTag(TEMPLATE_LINE_07, this.authorFirstName, TEMPLATE_LINE_08, _buffer);
        writeMultiLineTag(TEMPLATE_LINE_09, this.authorLastName, TEMPLATE_LINE_10, _buffer);
        writeTag(TEMPLATE_LINE_11, _buffer);

        //book-title
        writeMultiLineTag(TEMPLATE_LINE_12, this.bookTitle, TEMPLATE_LINE_13, _buffer);

        //date
        writeTag(TEMPLATE_LINE_14, _buffer);
        writeTag(TEMPLATE_LINE_15, _buffer);

        //coverpage
        if (!this.coverTag.isEmpty()) {
            writeOneLineTag(TEMPLATE_LINE_131, TEMPLATE_LINE_132 + this.coverTag + TEMPLATE_LINE_133, TEMPLATE_LINE_134, _buffer);
        }

        //lang
        writeOneLineTag(TEMPLATE_LINE_16, this.lang, TEMPLATE_LINE_17, _buffer);

        //pop title-info
        writeTag(TEMPLATE_LINE_18, _buffer);

        //open document-info
        writeTag(TEMPLATE_LINE_19, _buffer);

        //author
        writeTag(TEMPLATE_LINE_20, _buffer);
        writeMultiLineTag(TEMPLATE_LINE_21, this.authorFirstName, TEMPLATE_LINE_22, _buffer);
        writeMultiLineTag(TEMPLATE_LINE_23, this.authorLastName, TEMPLATE_LINE_24, _buffer);
        writeTag(TEMPLATE_LINE_25, _buffer);

        //program-used
        writeMultiLineTag(TEMPLATE_LINE_26, this.programUsed, TEMPLATE_LINE_27, _buffer);

        //date
        writeData(getDateTag(TEMPLATE_LINE_28, this.date, TEMPLATE_LINE_29, TEMPLATE_LINE_30), _buffer);

        //id
        writeOneLineTag(TEMPLATE_LINE_31, this.id, TEMPLATE_LINE_32, _buffer);

        //version
        writeMultiLineTag(TEMPLATE_LINE_33, this.version, TEMPLATE_LINE_34, _buffer);

        //pop document-info
        writeTag(TEMPLATE_LINE_35, _buffer);

        //pop description
        writeTag(TEMPLATE_LINE_36, _buffer);
    }

    private void writeTag(final String _tag, final List<String> _buffer) {
        if (isCloseTag(_tag)) {
            decIndent();
        }

        writeData(_tag, _buffer);

        if (isOpenTag(_tag)) {
            incIndent();
        }
    }

    private void decIndent() {
        this.indent = this.indent - INDENT_SIZE;
    }

    private void writeMultiLineTag(final String _openTag, final String _data, final String _closeTag, final List<String> _buffer) {
        writeData(_openTag, _buffer);
        incIndent();
        writeData(_data, _buffer);
        decIndent();
        writeData(_closeTag, _buffer);
    }

    private void incIndent() {
        this.indent = this.indent + INDENT_SIZE;
    }

    private void writeOneLineTag(final String _openTag, final String _data, final String _closeTag, final List<String> _buffer) {
        StringBuilder result = new StringBuilder();

        result.append(getIndent());
        result.append(_openTag);
        result.append(_data);
        result.append(_closeTag);

        _buffer.add(result.toString());
    }

    private void writeData(final String _data, final List<String> _buffer) {
        _buffer.add(new StringBuffer(getIndent()).append(_data).toString());
    }

    private boolean isOpenTag(final String _tag) {
        return !isCloseTag(_tag) && _tag.startsWith(OPEN_TAG_SYMBOL);
    }

    private boolean isCloseTag(final String _tag) {
        return _tag.startsWith(CLOSE_TAG_SYMBOL);
    }

    private String getIndent() {
        StringBuilder result = new StringBuilder(this.indent);

        for (int count = 0; count < this.indent; ++count) {
            result.append(" ");
        }

        return result.toString();
    }

    private String getDateTag(final String _openPart, final Date _date, final String _interTag, final String _closeTag) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(_date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = fillSpace(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        String day = fillSpace(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        StringBuffer dateString = new StringBuffer(year);
        dateString.append(DATE_DELIMITER);
        dateString.append(month);
        dateString.append(DATE_DELIMITER);
        dateString.append(day);

        StringBuffer result = new StringBuffer(_openPart);
        result.append(dateString);
        result.append(_interTag);
        result.append(dateString);
        result.append(_closeTag);

        return result.toString();
    }

    private String fillSpace(final String _value) {
        StringBuilder result = new StringBuilder();

        if (_value.length() != 2) {
            result.append("0");
        }
        result.append(_value);

        return result.toString();
    }

}
