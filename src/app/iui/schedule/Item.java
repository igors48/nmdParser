package app.iui.schedule;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public class Item {

    public static final long NEVER_UPDATED_BEFORE = -1;

    private String workspace;
    private String entity;
    private Period period;
    private long lastUpdate;

    public Item() {
        this.workspace = "";
        this.entity = "";
        this.period = Period.ALWAYS;
        this.lastUpdate = NEVER_UPDATED_BEFORE;
    }

    public Item(final String _workspace, final String _entity, final Period _period) {
        setWorkspace(_workspace);
        setEntity(_entity);
        setPeriod(_period);
        setLastUpdate(NEVER_UPDATED_BEFORE);
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(final String _entity) {
        Assert.isValidString(_entity, "Entity name is not valid");
        this.entity = _entity;
    }

    public long getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(final long _lastUpdate) {
        Assert.greaterOrEqual(_lastUpdate, NEVER_UPDATED_BEFORE, "Last update time < -1");
        this.lastUpdate = _lastUpdate;
    }

    public Period getPeriod() {
        return this.period;
    }

    public void setPeriod(final Period _period) {
        Assert.notNull(_period, "Period is null");
        this.period = _period;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(final String _workspace) {
        Assert.isValidString(_workspace, "Workspace name is not valid");
        this.workspace = _workspace;
    }

    public boolean needsUpdate(final long _currentTime) {
        Assert.greaterOrEqual(_currentTime, 0, "Current time < 0");

        return this.period == Period.ALWAYS || this.lastUpdate == NEVER_UPDATED_BEFORE || _currentTime >= this.lastUpdate + this.period.periodInMs;
    }
}
