package app.iui.validator.rules;

import app.iui.validator.AbstractRule;

import java.net.URI;

/**
 * @author Igor Usenko
 *         Date: 04.11.2010
 */
public class UrlValid extends AbstractRule {

    protected boolean validate(final String _data) {
        boolean result = true;

        try {
            new URI(_data);
        } catch (Exception e) {
            result = false;
        }

        return result;
    }
}
