package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class SimpleFeederModel extends Model {

    private final boolean blank;

    private SimplerConfiguration configuration;

    public SimpleFeederModel(final SimplerConfiguration _configuration, final boolean _blank) {
        super(ModelType.SIMPLE_FEEDER);

        this.blank = _blank;

        setConfiguration(_configuration);
    }

    public SimplerConfiguration getConfiguration() {
        return this.configuration;
    }

    public final void setConfiguration(final SimplerConfiguration _configuration) {
        Assert.notNull(_configuration, "Configuration is null");
        this.configuration = _configuration;
    }

    public boolean isBlank() {
        return this.blank;
    }
}
