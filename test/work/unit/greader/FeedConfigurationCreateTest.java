package work.unit.greader;

import greader.profile.FeedConfiguration;
import junit.framework.TestCase;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 08.12.2011
 */
public class FeedConfigurationCreateTest extends TestCase {

    public FeedConfigurationCreateTest(final String _name) {
        super(_name);
    }

    public void testMainParametersIsSet() throws Exception {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "");

        assertEquals("url", configuration.getUrl());
        assertEquals("name", configuration.getName());
        assertEquals("branch", configuration.getBranch());
    }

    public void testIfAutoFilterCriterionReceivedCorrespondingFlagIsSet() throws Exception {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", FeedConfiguration.AUTO_FILTER_CRITERION);

        assertTrue(configuration.isAutoContentFiltering());
        assertTrue(configuration.getCriterions().isEmpty());
    }

    public void testIfCommonCriterionReceivedItsSetCorrectly() throws Exception {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");

        assertFalse(configuration.isAutoContentFiltering());
        assertEquals("criterion", configuration.getCriterions());
    }

}
