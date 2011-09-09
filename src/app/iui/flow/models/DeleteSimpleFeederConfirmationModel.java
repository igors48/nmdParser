package app.iui.flow.models;

import app.iui.entity.Entity;
import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 20.12.2010
 */
public class DeleteSimpleFeederConfirmationModel extends Model {

    private final Entity feeder;

    public DeleteSimpleFeederConfirmationModel(final Entity _feeder) {
        super(ModelType.DELETE_SIMPLE_FEEDER_CONFIRMATION);

        Assert.notNull(_feeder, "Feeder is null");
        this.feeder = _feeder;
    }

    public Entity getFeeder() {
        return this.feeder;
    }
}