package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 14.12.2010
 */
public class ForcedUpdatePeriodModel extends Model {

    private int days;

    public ForcedUpdatePeriodModel() {
        super(ModelType.FORCED_UPDATE_PERIOD);
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(final int _days) {
        Assert.greaterOrEqual(_days, -1, "Days < -1");

        this.days = _days;
    }
}
