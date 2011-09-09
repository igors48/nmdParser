package greader.tools;

import greader.profile.FeedConfiguration;
import greader.entities.Subscription;
import greader.entities.FeedItem;

import java.util.List;

import util.Assert;
import util.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.RequestSourceType;
import app.cli.blitz.request.CriterionType;
import constructor.objects.output.configuration.Composition;
import dated.item.modification.Modification;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.08.2011
 */
public final class GoogleReaderAdapterTools {

    private static final Log log = LogFactory.getLog(GoogleReaderAdapterTools.class);

    public static BlitzRequest createBlitzRequest(final FeedConfiguration _feedConfiguration, final List<Modification> _modifications) {
        Assert.notNull(_feedConfiguration, "Feed configuration is null");
        Assert.notNull(_modifications, "Modifications is null");
        
        BlitzRequest result = new BlitzRequest(_modifications);

        result.setBranch(safeString(_feedConfiguration.getBranch()));
        result.setCoverUrl(safeString(_feedConfiguration.getCoverUrl()));

        if ((_feedConfiguration.getCriterions() != null) && (!_feedConfiguration.getCriterions().isEmpty())) {
            result.setCriterionExpression(_feedConfiguration.getCriterions());
            result.setCriterionType(CriterionType.XPATH);
        }

        result.setComposition(Composition.MANY_TO_ONE);
        result.setOutName(safeString(_feedConfiguration.getName()));

        return result;    
    }

    public static void synchronize(final List<FeedConfiguration> _feedConfigurations, final List<Subscription> _subscriptions) {
        Assert.notNull(_feedConfigurations, "Feed configurations is null");
        Assert.notNull(_subscriptions, "Subscriptions is null");

        for (Subscription current : _subscriptions) {

            if (contains(current, _feedConfigurations)) {
                log.debug(String.format("Feed [ %s ] already in profile", current.getId()));
            } else {
                _feedConfigurations.add(FeedConfiguration.createForUrlAndName(current.getId(), current.getTitle()));
                log.debug(String.format("Feed [ %s ] append to profile", current.getId()));
            }
        }
    }

    private static boolean contains(final Subscription _subscription, final List<FeedConfiguration> _feedConfigurations) {

        for (FeedConfiguration candidate : _feedConfigurations) {

            if (candidate.getUrl().equalsIgnoreCase(_subscription.getId())) {
                return true;
            }
        }

        return false;
    }
    
    private static String safeString(final String _string) {
        return _string == null ? "" : _string;
    }

    private GoogleReaderAdapterTools() {
        // empty
    }
}
