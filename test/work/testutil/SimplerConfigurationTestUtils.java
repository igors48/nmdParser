package work.testutil;

import constructor.objects.simpler.configuration.SimplerConfiguration;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 14.09.2010
 */
public final class SimplerConfigurationTestUtils {

    public static SimplerConfiguration createSimplerConfiguration(String _id, String _fromNewToOldMode) {
        Assert.isValidString(_id);
        Assert.notNull(_fromNewToOldMode);

        SimplerConfiguration result = new SimplerConfiguration();

        result.setId(_id);
        result.setAttributeFeedUrl("feedUrl");
        result.setAttributeStoreDays("7");
        result.setAttributeBranch("branch");
        result.setAttributeOutName("outName");

        if (!_fromNewToOldMode.isEmpty()) {
            result.setAttributeFromNewToOld(_fromNewToOldMode);
        }

        result.setAttributeCoverUrl("coverUrl");

        result.setElementXPath("xPath");

        return result;
    }

    private SimplerConfigurationTestUtils() {
        // empty
    }
}
