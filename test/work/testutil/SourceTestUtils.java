package work.testutil;

import dated.item.modification.stream.ModificationList;
import dated.item.modification.Modification;

/**
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public final class SourceTestUtils {

    public static boolean bothExists(final String _url01, final String _url02, final ModificationList _result) {
        return (_result.get(0).getUrl().equals(_url02) && _result.get(1).getUrl().equals(_url01)) ||
                (_result.get(1).getUrl().equals(_url02) && _result.get(0).getUrl().equals(_url01));
    }

    public static Modification getForUrl(final String _url, final ModificationList _list) {
        Modification result = null;

        for (int index = 0; index < _list.size(); ++index) {
            Modification modification = _list.get(index);

            if (modification.getUrl().equalsIgnoreCase(_url)) {
                result = modification;
                break;
            }
        }

        return result;
    }    private SourceTestUtils() {
        // empty
    }
}
