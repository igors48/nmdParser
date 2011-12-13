package app.iui.flow.models;

import app.iui.entity.Entity;
import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class UpdateFeederModel extends Model {

    private final Entity feeder;
    private final int forcedPeriod;
    private final Map<String, String> context;

    private List<String> files;
    private Throwable cause;

    public UpdateFeederModel(final Entity _feeder, final int _forcedPeriod, final Map<String, String> _context) {
        super(ModelType.UPDATE_FEEDER);

        Assert.notNull(_feeder, "Feeder is null");
        this.feeder = _feeder;

        Assert.greaterOrEqual(_forcedPeriod, -1, "Forced period < -1");
        this.forcedPeriod = _forcedPeriod;

        Assert.notNull(_context, "Context is null");
        this.context = _context;

        this.files = newArrayList();
    }

    public Entity getFeeder() {
        return this.feeder;
    }

    public int getForcedPeriod() {
        return this.forcedPeriod;
    }

    public Map<String, String> getContext() {
        return this.context;
    }

    public List<String> getFiles() {
        return this.files;
    }

    public void setFiles(final List<String> _files) {
        Assert.notNull(_files, "Files is null");
        this.files = _files;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public void setCause(final Throwable _cause) {
        this.cause = _cause;
    }

}