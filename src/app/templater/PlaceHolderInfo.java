package app.templater;

import util.Assert;

/**
 * Информация о плейсхолдере: имя и значение по умолчанию, если есть
 *
 * @author Igor Usenko
 *         Date: 10.03.2010
 */
public class PlaceHolderInfo {

    private final String name;
    private final String defValue;

    public PlaceHolderInfo(final String _name, final String _defValue) {
        Assert.isValidString(_name, "Name is not valid");
        this.name = _name;

        this.defValue = _defValue;
    }

    public String getDefValue() {
        return this.defValue;
    }

    public String getName() {
        return this.name;
    }

}
