package work.testutil;

import constructor.objects.channel.core.ChannelDataHeader;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public final class ChannelDataHeaderTestUtils {

    public static boolean headerEquals(final ChannelDataHeader _first, final ChannelDataHeader _second) {
        Assert.notNull(_first);
        Assert.notNull(_second);

        boolean result = true;

        if (!_first.getFirstName().equals(_second.getFirstName())) {
            result = false;
        }

        if (!_first.getLastName().equals(_second.getLastName())) {
            result = false;
        }

        if (!_first.getSourceUrl().equals(_second.getSourceUrl())) {
            result = false;
        }

        if (!_first.getTitle().equals(_second.getTitle())) {
            result = false;
        }

        if (!_first.getLang().equals(_second.getLang())) {
            result = false;
        }

        if (!_first.getGenres().containsAll(_second.getGenres())) {
            result = false;
        }

        if (!_second.getGenres().containsAll(_first.getGenres())) {
            result = false;
        }

        return result;
    }

    private ChannelDataHeaderTestUtils() {
        // empty
    }
}
