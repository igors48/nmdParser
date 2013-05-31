package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 15.12.2010
 */
public class UpdateContextModel extends Model {

    private final String feederName;
    private Map<String, String> context;

    public UpdateContextModel(final String _feederName, final Map<String, String> _context) {
        super(ModelType.UPDATE_CONTEXT);

        Assert.isValidString(_feederName, "Feeder name is not valid");
        this.feederName = _feederName;

        setContext(_context);
    }

    public String getFeederName() {
        return this.feederName;
    }

    public Map<String, String> getContext() {
        return this.context;
    }

    public final void setContext(final Map<String, String> _context) {
        Assert.notNull(_context, "Context is null");

        this.context = _context;
    }
}
