package greader.tools;

import app.cli.blitz.request.BlitzRequest;
import app.cli.blitz.request.CriterionType;
import constructor.objects.output.configuration.Composition;
import dated.item.modification.Modification;
import greader.entities.Category;
import greader.entities.Subscription;
import greader.profile.FeedConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.08.2011
 */
public final class GoogleReaderAdapterTools {

    public static final String CRITERION_PREFIX = "c:";
    public static final String SLASH_REPLACEMENT = "%";

    private static final String SLASH = "/";

    private static final Log log = LogFactory.getLog(GoogleReaderAdapterTools.class);

    public static BlitzRequest createBlitzRequest(final FeedConfiguration _feedConfiguration, final List<Modification> _modifications) {
        Assert.notNull(_feedConfiguration, "Feed configuration is null");
        Assert.notNull(_modifications, "Modifications is null");

        BlitzRequest result = new BlitzRequest(_modifications);

        result.setBranch(safeString(_feedConfiguration.getBranch()));
        result.setCoverUrl(safeString(_feedConfiguration.getCoverUrl()));

        if (_feedConfiguration.isAutoContentFiltering()) {
            result.setCriterionType(CriterionType.FILTER);
        } else if ((_feedConfiguration.getCriterions() != null) && (!_feedConfiguration.getCriterions().isEmpty())) {
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

        List<FeedConfiguration> notFoundConfigurations = newArrayList();
        notFoundConfigurations.addAll(_feedConfigurations);

        for (Subscription current : _subscriptions) {

            FeedConfiguration found = find(current, _feedConfigurations);

            if (found == null) {
                _feedConfigurations.add(FeedConfiguration.create(current.getId(),
                        current.getTitle(),
                        getBranch(current.getCategories()),
                        getCriterion(current.getCategories())));

                log.debug(String.format("Feed [ %s ] append to profile", current.getId()));
            } else {
                log.debug(String.format("Feed [ %s ] already in profile", current.getId()));

                notFoundConfigurations.remove(found);

                synchronize(current, found);
            }
        }

        for (FeedConfiguration configuration : notFoundConfigurations) {
            configuration.setActive(false);
        }
    }

    public static void synchronize(final Subscription _subscription, final FeedConfiguration _configuration) {
        Assert.notNull(_subscription, "Subscription is null");
        Assert.notNull(_configuration, "Configuration is null");

        _configuration.setActive(true);

        synchronizeBranch(_subscription, _configuration);
        synchronizeCriterion(_subscription, _configuration);
    }

    public static String getBranch(final Category[] _categories) {

        if (_categories != null) {

            for (Category current : _categories) {
                String label = current.getLabel();

                if (!isItCriterion(label)) {
                    return label;
                }
            }
        }

        return "";
    }

    public static String getCriterion(final Category[] _categories) {

        if (_categories != null) {

            for (Category current : _categories) {
                String label = current.getLabel();

                if (isItCriterion(label)) {
                    return label.substring(CRITERION_PREFIX.length()).replaceAll(SLASH_REPLACEMENT, SLASH);
                }
            }
        }

        return "";
    }

    private static boolean isItCriterion(final String _data) {
        return _data.startsWith(CRITERION_PREFIX);
    }

    private static FeedConfiguration find(final Subscription _subscription, final List<FeedConfiguration> _feedConfigurations) {

        for (FeedConfiguration candidate : _feedConfigurations) {

            if (candidate.getUrl().equalsIgnoreCase(_subscription.getId())) {
                return candidate;
            }
        }

        return null;
    }

    private static String safeString(final String _string) {
        return _string == null ? "" : _string;
    }

    private static void synchronizeCriterion(final Subscription _subscription, final FeedConfiguration _configuration) {
        String newCriterion = getCriterion(_subscription.getCategories());

        if (FeedConfiguration.AUTO_FILTER_CRITERION.equals(newCriterion)) {

            if (!_configuration.isAutoContentFiltering()) {
                log.debug(String.format("Feed [ %s ] criterion changed to automatic filtering", _subscription.getId()));

                _configuration.setAutoContentFiltering(true);
                _configuration.setCriterions("");
            }
        } else {
            _configuration.setAutoContentFiltering(false);

            if (!newCriterion.isEmpty() && !newCriterion.equalsIgnoreCase(_configuration.getCriterions())) {
                log.debug(String.format("Feed [ %s ] criterion changed from [ %s ] to [ %s ]", _subscription.getId(), _configuration.getCriterions(), newCriterion));

                _configuration.setCriterions(newCriterion);
            }
        }
    }

    private static void synchronizeBranch(final Subscription _subscription, final FeedConfiguration _configuration) {
        String newBranch = getBranch(_subscription.getCategories());

        if (!newBranch.isEmpty()) {
            
            if (_configuration.getBranch().isEmpty()) {
                log.debug(String.format("Feed [ %s ] branch changed from [ %s ] to [ %s ]", _subscription.getId(), _configuration.getBranch(), newBranch));

                _configuration.setBranch(newBranch);
            }
        }
    }

    private GoogleReaderAdapterTools() {
        // empty
    }

}
