package work.unit.greader;

import greader.entities.Subscription;
import greader.profile.FeedConfiguration;
import greader.tools.GoogleReaderAdapterTools;
import junit.framework.TestCase;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.08.2011
 */
public class GoogleReaderAdapterToolsTest extends TestCase {

    public GoogleReaderAdapterToolsTest(final String _s) {
        super(_s);
    }

    public void testNotExistItemsAppended() {
        List<FeedConfiguration> feedConfigurations = newArrayList();

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertSubscriptionAndFeedConfigurationEqualsAndActive(first, feedConfigurations.get(0));
        assertSubscriptionAndFeedConfigurationEqualsAndActive(second, feedConfigurations.get(1));
    }

    public void testExistsItemsNotChanged() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.create("f", "f", "f", ""));

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertSubscriptionAndFeedConfigurationEqualsAndActive(first, feedConfigurations.get(0));
        assertSubscriptionAndFeedConfigurationEqualsAndActive(second, feedConfigurations.get(1));
    }

    public void testExistsItemsActivatedForcibly() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        FeedConfiguration configuration = FeedConfiguration.create("f", "f", "f", "");
        configuration.setActive(false);
        feedConfigurations.add(configuration);

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(1, feedConfigurations.size());

        assertSubscriptionAndFeedConfigurationEqualsAndActive(first, feedConfigurations.get(0));
    }

    public void testExistItemsBranchesNotChangedIfItsNotEmpty() {
        List<FeedConfiguration> feedConfigurations = newArrayList();

        FeedConfiguration original = FeedConfiguration.create("f", "f", "c", "");
        feedConfigurations.add(original);

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertEquals("f", feedConfigurations.get(0).getUrl());
        assertEquals("f", feedConfigurations.get(0).getName());
        assertEquals("c", feedConfigurations.get(0).getBranch());
        assertTrue(feedConfigurations.get(0).isActive());

        assertSubscriptionAndFeedConfigurationEqualsAndActive(second, feedConfigurations.get(1));
    }

    public void testExistItemsBranchesNotChangedIfItsEmpty() {
        List<FeedConfiguration> feedConfigurations = newArrayList();

        FeedConfiguration original = FeedConfiguration.create("f", "f", "", "");
        feedConfigurations.add(original);

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertSubscriptionAndFeedConfigurationEqualsAndActive(first, feedConfigurations.get(0));
        assertSubscriptionAndFeedConfigurationEqualsAndActive(second, feedConfigurations.get(1));
    }

    public void testMissingItemsMarksAsNotActive() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.create("m", "m", "m", ""));

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(3, feedConfigurations.size());

        assertEquals("m", feedConfigurations.get(0).getUrl());
        assertEquals("m", feedConfigurations.get(0).getName());
        assertEquals("m", feedConfigurations.get(0).getBranch());
        assertFalse(feedConfigurations.get(0).isActive());

        assertSubscriptionAndFeedConfigurationEqualsAndActive(first, feedConfigurations.get(1));
        assertSubscriptionAndFeedConfigurationEqualsAndActive(second, feedConfigurations.get(2));
    }

    private void assertSubscriptionAndFeedConfigurationEqualsAndActive(final Subscription _subscription, final FeedConfiguration _feedConfiguration) {
        assertEquals(_subscription.getId(), _feedConfiguration.getUrl());
        assertEquals(_subscription.getTitle(), _feedConfiguration.getName());
        assertEquals(_subscription.getCategories()[0].getLabel(), _feedConfiguration.getBranch());
        
        assertTrue(_feedConfiguration.isActive());
    }

}
