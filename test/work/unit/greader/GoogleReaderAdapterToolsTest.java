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

        Subscription first = new Subscription("f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertEquals(first.getId(), feedConfigurations.get(0).getUrl());
        assertEquals(first.getTitle(), feedConfigurations.get(0).getName());

        assertEquals(second.getId(), feedConfigurations.get(1).getUrl());
        assertEquals(second.getTitle(), feedConfigurations.get(1).getName());
    }

    public void testExistItemsNotChanged() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.createForUrlAndName("f", "f"));

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(2, feedConfigurations.size());

        assertEquals(first.getId(), feedConfigurations.get(0).getUrl());
        assertEquals(first.getTitle(), feedConfigurations.get(0).getName());

        assertEquals(second.getId(), feedConfigurations.get(1).getUrl());
        assertEquals(second.getTitle(), feedConfigurations.get(1).getName());
    }

    public void testMissingItemsNotChanged() {
        List<FeedConfiguration> feedConfigurations = newArrayList();
        feedConfigurations.add(FeedConfiguration.createForUrlAndName("m", "m"));

        List<Subscription> subscriptions = newArrayList();

        Subscription first = new Subscription("f", "f");
        subscriptions.add(first);

        Subscription second = new Subscription("s", "s");
        subscriptions.add(second);

        GoogleReaderAdapterTools.synchronize(feedConfigurations, subscriptions);

        assertEquals(3, feedConfigurations.size());

        assertEquals("m", feedConfigurations.get(0).getUrl());
        assertEquals("m", feedConfigurations.get(0).getName());

        assertEquals(first.getId(), feedConfigurations.get(1).getUrl());
        assertEquals(first.getTitle(), feedConfigurations.get(1).getName());

        assertEquals(second.getId(), feedConfigurations.get(2).getUrl());
        assertEquals(second.getTitle(), feedConfigurations.get(2).getName());
    }
}
