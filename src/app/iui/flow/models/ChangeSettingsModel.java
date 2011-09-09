package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.04.2011
 */
public class ChangeSettingsModel extends Model {

    private Settings settings;

    public ChangeSettingsModel() {
        super(ModelType.CHANGE_SETTINGS);
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(final Settings _settings) {
        Assert.notNull(_settings, "Settings is null");
        this.settings = _settings;
    }

    public enum Settings {
        TOOLS,
        POSTPROCESSING
    }
}
