package app.iui.flow;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 01.12.2010
 */
public class Model {

    private final ModelType type;

    private boolean approved;

    public Model(final ModelType _type) {
        Assert.notNull(_type, "Type is null");
        this.type = _type;

        this.approved = false;
    }

    public ModelType getType() {
        return this.type;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public boolean isRejected() {
        return !this.approved;
    }

    public void approve() {
        setApproved(true);
    }

    public void reject() {
        setApproved(false);
    }

    private void setApproved(final boolean _approved) {
        this.approved = _approved;
    }
}
