package constructor.objects.simpler.configuration;

import constructor.objects.output.configuration.DocumentItemsSortMode;
import util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 25.07.2010
 */
public final class SimplerConfigurationTools {

    private static final String FIRST_LINE_TEMPLATE = "<simpler feedUrl=\"{0}\" coverUrl=\"{1}\" storeDays=\"{2}\" branch=\"{3}\" outName=\"{4}\" {5}>";
    private static final String FROM_NEW_TO_OLD_TEMPLATE = "fromNewToOld=\"{0}\"";
    private static final String CRITERION_TEMPLATE = "<xPath>{0}</xPath>";
    private static final String LAST_LINE_TEMPLATE = "</simpler>";
    private static final String YES_TOKEN = "yes";
    private static final String NO_TOKEN = "no";

    public static List<String> render(final SimplerConfiguration _configuration) {
        Assert.notNull(_configuration, "Configuration is null");

        List<String> result = newArrayList();


        String first = MessageFormat.format(FIRST_LINE_TEMPLATE,
                _configuration.getFeedUrl(),
                _configuration.getCoverUrl(),
                _configuration.getStoreDays(),
                _configuration.getBranch(),
                _configuration.getOutName(),
                getFromNewToOldToken(_configuration.getFromNewToOld()));

        result.add(first);

        result.add(renderCriterions(_configuration.getCriterions()));

        if (_configuration.isAutoContentFiltering()) {
            result.add("<content-filter/>");
        }

        result.add(LAST_LINE_TEMPLATE);

        return result;
    }

    private static String getFromNewToOldToken(final DocumentItemsSortMode _mode) {
        String result = "";

        if (_mode != DocumentItemsSortMode.DEFAULT) {
            result = MessageFormat.format(FROM_NEW_TO_OLD_TEMPLATE, _mode == DocumentItemsSortMode.FROM_NEW_TO_OLD ? YES_TOKEN : NO_TOKEN);
        }

        return result;
    }

    private static String renderCriterions(final String _criterion) {
        return MessageFormat.format(CRITERION_TEMPLATE, _criterion);
    }

    private SimplerConfigurationTools() {
        // empty
    }
}
