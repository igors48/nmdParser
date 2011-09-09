package app.iui.flow.models;

import app.iui.entity.Entity;
import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 23.12.2010
 */
public class ChooseFeederDebugActionModel extends Model {

    private final Entity entity;

    private Action action;

    public ChooseFeederDebugActionModel(final Entity _entity) {
        super(ModelType.CHOOSE_FEEDER_DEBUG_ACTION);

        Assert.notNull(_entity, "Entity is null");
        this.entity = _entity;

        this.action = Action.RETURN;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(final Action _action) {
        Assert.notNull(_action, "Action is null");

        this.action = _action;
    }

    public enum Action {
        TEST,
        RETURN
    }
}
