package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import app.iui.schedule.ScheduleAdapter;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 07.05.2011
 */
public class AutoUpdateFeedersModel extends Model {

    private final ScheduleAdapter adapter;

    private List<String> files;

    public AutoUpdateFeedersModel(final ScheduleAdapter _schedule) {
        super(ModelType.AUTO_UPDATE_FEEDERS);

        Assert.notNull(_schedule, "Schedule is null");
        this.adapter = _schedule;

        this.files = new ArrayList<String>();
    }

    public ScheduleAdapter getScheduleAdapter() {
        return this.adapter;
    }

    public List<String> getFiles() {
        return this.files;
    }

    public void addFiles(final List<String> _files) {
        Assert.notNull(_files, "Files is null");

        this.files.addAll(_files);
    }
}
