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

        assertSubscriptionAndFeedConfigurationEquals(first, feedConfigurations.get(0));
        assertSubscriptionAndFeedConfigurationEquals(second, feedConfigurations.get(1));
    }

    public void testExistItemsNotChanged() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.createForUrlAndName("f", "f", "f"));

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertSubscriptionAndFeedConfigurationEquals(first, feedConfigurations.get(0));
        assertSubscriptionAndFeedConfigurationEquals(second, feedConfigurations.get(1));
    }

    public void testExistItemsBranchesChanged() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.createForUrlAndName("f", "f", "c"));

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertSubscriptionAndFeedConfigurationEquals(first, feedConfigurations.get(0));
        assertSubscriptionAndFeedConfigurationEquals(second, feedConfigurations.get(1));
    }

    public void testMissingItemsNotChanged() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.createForUrlAndName("m", "m", "m"));

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

        assertSubscriptionAndFeedConfigurationEquals(first, feedConfigurations.get(1));
        assertSubscriptionAndFeedConfigurationEquals(second, feedConfigurations.get(2));
    }

    private void assertSubscriptionAndFeedConfigurationEquals(final Subscription _first, final FeedConfiguration _feedConfiguration) {
        assertEquals(_first.getId(), _feedConfiguration.getUrl());
        assertEquals(_first.getTitle(), _feedConfiguration.getName());
        assertEquals(_first.getCategories()[0].getLabel(), _feedConfiguration.getBranch());
    }

}
