package work.unit.greader;

import greader.entities.Subscription;
import greader.profile.FeedConfiguration;
import greader.tools.GoogleReaderAdapterTools;
import junit.framework.TestCase;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 08.12.2011
 */
public class FeedConfigurationSynchronizerTest extends TestCase {

    public FeedConfigurationSynchronizerTest(final String _name) {
        super(_name);
    }


    //    public static void synchronize(final Subscription _subscription, final FeedConfiguration _configuration) {


    public void testAfterSynchNotActiveConfigurationBecomesActive() throws Exception {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");
        configuration.setActive(false);

        Subscription subscription = new Subscription("id", "title", "category");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertTrue(configuration.isActive());
    }

    public void testIfBranchesInSubscriptionAndConfigurationAreTheSameThenConfigurationNotChanged() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");

        Subscription subscription = new Subscription("id", "title", "branch");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("branch", configuration.getBranch());
    }
    
    public void testIfBranchesInSubscriptionAndConfigurationNotTheSameThenBranchFromSubscriptionStoredInConfiguration() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");

        Subscription subscription = new Subscription("id", "title", "new-branch");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("new-branch", configuration.getBranch());
    }

    public void testEmptyBranchInSubscriptionLeftConfigurationBranchNotChanged() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");

        Subscription subscription = new Subscription("id", "title", "");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("branch", configuration.getBranch());
    }

    public void testIfCriterionsInSubscriptionAndConfigurationAreTheSameThenConfigurationNotChanged() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");

        Subscription subscription = new Subscription("id", "title", GoogleReaderAdapterTools.CRITERION_PREFIX + "criterion");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("criterion", configuration.getCriterions());
        assertFalse(configuration.isAutoContentFiltering());
    }

    public void testIfCriterionsInSubscriptionAndConfigurationNotTheSameThenCriterionFromSubscriptionStoredInConfiguration() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");

        Subscription subscription = new Subscription("id", "title", GoogleReaderAdapterTools.CRITERION_PREFIX + "new-criterion");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("new-criterion", configuration.getCriterions());
        assertFalse(configuration.isAutoContentFiltering());
    }

    public void testAutoFilteringActivatedProperly() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");
        configuration.setAutoContentFiltering(false);

        Subscription subscription = new Subscription("id", "title", GoogleReaderAdapterTools.CRITERION_PREFIX + FeedConfiguration.AUTO_FILTER_CRITERION);

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertTrue(configuration.getCriterions().isEmpty());
        assertTrue(configuration.isAutoContentFiltering());
    }

    public void testAutoFilteringResettedWithEmptyCriterion() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "");
        configuration.setAutoContentFiltering(true);

        Subscription subscription = new Subscription("id", "title", "");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertFalse(configuration.isAutoContentFiltering());
    }

    public void testAutoFilteringResettedWithNotEmptyCriterion() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "");
        configuration.setAutoContentFiltering(true);

        Subscription subscription = new Subscription("id", "title", GoogleReaderAdapterTools.CRITERION_PREFIX + "criterion");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("criterion", configuration.getCriterions());
        assertFalse(configuration.isAutoContentFiltering());
    }

    public void testEmptyCriterionInSubscriptionLeftConfigurationCriterionNotChanged() {
        FeedConfiguration configuration = FeedConfiguration.create("url", "name", "branch", "criterion");
        configuration.setAutoContentFiltering(true);

        Subscription subscription = new Subscription("id", "title", "");

        GoogleReaderAdapterTools.synchronize(subscription, configuration);

        assertEquals("criterion", configuration.getCriterions());
        assertFalse(configuration.isAutoContentFiltering());
    }

}
