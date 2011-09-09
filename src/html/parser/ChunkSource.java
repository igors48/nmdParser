package html.parser;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 17.09.2008
 */
public class ChunkSource {

    //todo должен возвращать инфу о текущем положении в коде

    private String data;

    private final String[] dividers;

    public ChunkSource(final String _data, final String[] _dividers) {
        Assert.isValidString(_data, "Data is not valid");
        Assert.notNull(_dividers, "Diveders is null");

        this.data = _data;
        this.dividers = _dividers;
    }

    public String get() {

        if (this.data.length() == 0) {

            return null;
        }

        if (this.data.length() == 1) {
            String result = this.data;
            this.data = "";

            return result;
        }

        int index = getClosestDividerIndex();

        if (index == -1) {
            String result = this.data;
            this.data = "";

            return result;
        }

        if (index == 0) {
            String result = this.data.substring(0, 1);
            this.data = this.data.substring(1);

            return result;
        }

        String result = this.data.substring(0, index);
        this.data = this.data.substring(index);

        return result;
    }

    private int getClosestDividerIndex() {
        int result = -1;

        for (String current : this.dividers) {
            int index = getDividerIndex(current);

            if (index != -1) {
                result = adjustResult(result, index);
            }
        }

        return result;
    }

    private int getDividerIndex(final String _current) {
        int index = this.data.indexOf(_current);

        if (index == -1) {
            index = this.data.indexOf(_current.toUpperCase());
        }

        return index;
    }

    private int adjustResult(int _result, final int _index) {

        if (_result == -1) {
            _result = _index;
        } else {
            _result = Math.min(_index, _result);
        }

        return _result;
    }

}
