package constructor.dom;

import util.Assert;

/**
 * Стандартные имена для типов
 *
 * @author Igor Usenko
 *         Date: 18.07.2009
 */
public final class StandardTypeNames {

    public static final String PROCESSOR_ELEMENT_NAME = "processor";
    public static final String SOURCE_ELEMENT_NAME = "source";
    public static final String INTERPRETER_ELEMENT_NAME = "interpreter";
    public static final String CHANNEL_ELEMENT_NAME = "channel";
    public static final String OUTPUT_ELEMENT_NAME = "output";
    public static final String STORAGE_ELEMENT_NAME = "storage";
    public static final String DATEPARSER_ELEMENT_NAME = "dateParser";
    public static final String SNIPPET_ELEMENT_NAME = "snippet";
    public static final String SIMPLER_ELEMENT_NAME = "simpler";

    public static ObjectType getType(final String _name) {
        Assert.isValidString(_name, "Type name is invalid.");

        ObjectType result = null;
        String name = _name.trim();

        if (PROCESSOR_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.PROCESSOR;
        }

        if (SOURCE_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.SOURCE;
        }

        if (INTERPRETER_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.INTERPRETER;
        }

        if (CHANNEL_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.CHANNEL;
        }

        if (OUTPUT_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.OUTPUT;
        }

        if (STORAGE_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.STORAGE;
        }

        if (DATEPARSER_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.DATEPARSER;
        }

        if (SNIPPET_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.SNIPPET;
        }

        if (SIMPLER_ELEMENT_NAME.equalsIgnoreCase(name)) {
            result = ObjectType.SIMPLER;
        }

        return result;
    }

    private StandardTypeNames() {
        // empty
    }
}
