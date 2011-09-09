package app.iui.schedule;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public interface ScheduleEditor {

    public void renameWorkspace(String _oldName, String _newName);

    public void renameEntity(String _workspace, String _oldName, String _newName);

    public void replace(Item _item, Item _new);

    public void add(Item _new);

    public void delete(Item _new);

    public List<Item> getItemsForWorkspce(String _workspaceName);
}
