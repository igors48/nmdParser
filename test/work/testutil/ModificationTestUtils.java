package work.testutil;

import dated.item.modification.Modification;
import util.Assert;
import static junit.framework.Assert.assertEquals;

/**
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public final class ModificationTestUtils {

    public static void assertModificationEquals(Modification _modification, Modification _restored) {
        Assert.notNull(_modification);
        Assert.notNull(_restored);

        assertEquals(_modification.getDate(), _restored.getDate());
        assertEquals(_modification.getUrl(), _restored.getUrl());
        assertEquals(_modification.getTitle(), _restored.getTitle());
        assertEquals(_modification.getDescription(), _restored.getDescription());
    }

    private ModificationTestUtils() {
        // empty
    }
}
