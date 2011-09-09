package app.iui.resource;

import app.iui.StringResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.ResourceBundle;

/**
 * Ресурс строк хранящийся в ResourceBundle
 *
 * @author Igor Usenko
 *         Date: 13.04.2010
 */
public class StringResourceBundle implements StringResource {

    private final ResourceBundle bundle;

    private final Log log;

    public StringResourceBundle(final ResourceBundle _bundle) {
        Assert.notNull(_bundle, "Resource bundle is null");
        this.bundle = _bundle;

        this.log = LogFactory.getLog(getClass());
    }

    public String getString(final String _key) {
        Assert.isValidString(_key, "Resource key is not valid");

        String result = _key;

        try {
            result = this.bundle.getString(_key);
        } catch (Throwable t) {
            this.log.error("Resource with id [ " + _key + " ] not found", t);
        }

        return result.isEmpty() ? _key : result;
    }
}
