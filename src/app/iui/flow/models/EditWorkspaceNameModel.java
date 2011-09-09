package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.12.2010
 */
public class EditWorkspaceNameModel extends Model {

    private final String label;
    private final String next;
    private final String oldName;

    private String name;

    public EditWorkspaceNameModel(final String _label, final String _next, final String _oldName, final String _name) {
        super(ModelType.EDIT_WORKSPACE_NAME);

        Assert.isValidString(_label, "Label is not valid");
        this.label = _label;

        Assert.isValidString(_next, "Next is not valid");
        this.next = _next;

        Assert.notNull(_name, "Name is null");
        this.name = _name;

        Assert.notNull(_oldName, "Old name is null");
        this.oldName = _oldName;
    }

    public String getLabel() {
        return this.label;
    }

    public String getNext() {
        return this.next;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String _name) {
        Assert.isValidString(_name, "Name is not valid");
        this.name = _name;
    }

    public String getOldName() {
        return this.oldName;
    }
}
