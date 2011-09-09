package app.iui.entity;

import java.util.Comparator;

/**
 * @author Igor Usenko
 *         Date: 21.11.2010
 */
public class EntityComparator implements Comparator<Entity> {

    public int compare(final Entity _entity01, final Entity _entity02) {
        return _entity01.getName().compareToIgnoreCase(_entity02.getName());
    }
}
