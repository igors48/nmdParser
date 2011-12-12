package app.iui.flow.models;

import app.iui.entity.Entity;
import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class UpdateFeedersModel extends Model {

    private final List<Entity> feeders;
    private final int forcedPeriod;
    private final Map<String, String> context;

    private List<String> files;

    public UpdateFeedersModel(final List<Entity> _feeders, final int _forcedPeriod, final Map<String, String> _context) {
        super(ModelType.UPDATE_FEEDERS);

        Assert.notNull(_feeders, "Feeders is null");
        this.feeders = _feeders;

        Assert.greaterOrEqual(_forcedPeriod, -1, "Forced period < -1");
        this.forcedPeriod = _forcedPeriod;

        Assert.notNull(_context, "Context is null");
        this.context = _context;

        this.files = newArrayList();
    }

    public List<Entity> getFeeders() {
        return this.feeders;
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

    public void addFiles(final List<String> _files) {
        Assert.notNull(_files, "Files is null");

        this.files.addAll(_files);
    }
}
