package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ChooseFetchModeModel extends Model {

    private final String workspace;

    private FetchMode mode;

    public ChooseFetchModeModel(final String _workspace) {
        super(ModelType.CHOOSE_FETCH_MODE);

        Assert.isValidString(_workspace, "Workspace is invalid");
        this.workspace = _workspace;

        this.mode = FetchMode.STANDARD;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public FetchMode getMode() {
        return this.mode;
    }

    public void setMode(final FetchMode _mode) {
        Assert.notNull(_mode, "Mode is null");
        this.mode = _mode;
    }

    public enum FetchMode {
        STANDARD,
        FORCED,
        PARAMETRIZED
    }
}
