package app.iui.schedule;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public interface ScheduleAdapter {

    public ScheduledItems getItemsForUpdate(long currentTime);

    public void itemUpdated(Item _item, long _time);

}
