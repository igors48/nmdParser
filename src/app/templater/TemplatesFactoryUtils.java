package app.templater;

import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * ������� ��� ������� �������� �����
 *
 * @author Igor Usenko
 *         Date: 28.09.2009
 */
public final class TemplatesFactoryUtils {

    public static Map<String, String> createParametersMap(final TemplateParameters _parameters) {
        Assert.notNull(_parameters, "Template parameters is null");

        Map<String, String> result = new HashMap<String, String>();

        result.put(TemplateContentFactory.RSS_URL_ID, _parameters.getRssUrl());
        result.put(TemplateContentFactory.NAME_ID, _parameters.getName());

        return result;
    }

    private TemplatesFactoryUtils() {
        // empty
    }
}
