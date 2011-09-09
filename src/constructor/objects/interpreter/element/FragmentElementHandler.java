package constructor.objects.interpreter.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик єлемента interpreter.fragment
 *
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public class FragmentElementHandler extends AbstractElementHandler {
    private static final String NICK_KEY = "nick";
    private static final String INFO_KEY = "info";
    private static final String AVATAR_KEY = "avatar";
    private static final String TITLE_KEY = "title";
    private static final String DATE_KEY = "date";
    private static final String CONTENT_KEY = "content";
    private static final String DATE_EXTRACTOR_KEY = "dateExtractor";
    private static final String DATE_PARSER_KEY = "dateParser";
    private static final String TIME_CORRECTION_KEY = "timeCorrection";

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        // there is no sub elements
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Atribute is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (name == null) {
            throw new AttributeHandler.AttributeHandlerException("Attribute name is null.");
        }

        AttributeHandler handler = null;

        if (name.equals(NICK_KEY)) {
            handler = new FragmentNickAttributeHandler();
        }

        if (name.equals(INFO_KEY)) {
            handler = new FragmentInfoAttributeHandler();
        }

        if (name.equals(AVATAR_KEY)) {
            handler = new FragmentAvatarAttributeHandler();
        }

        if (name.equals(TITLE_KEY)) {
            handler = new FragmentTitleAttributeHandler();
        }

        if (name.equals(DATE_KEY)) {
            handler = new FragmentDateExtractorAttributeHandler();
        }

        if (name.equals(CONTENT_KEY)) {
            handler = new FragmentContentAttributeHandler();
        }

        if (name.equals(DATE_EXTRACTOR_KEY)) {
            handler = new FragmentDateExtractorAttributeHandler();
        }

        if (name.equals(DATE_PARSER_KEY)) {
            handler = new FragmentDateParserAttributeHandler();
        }

        if (name.equals(TIME_CORRECTION_KEY)) {
            handler = new FragmentTimeCorrectionAttributeHandler();
        }

        if (handler != null) {
            handler.handle(name, value, _blank, _factory);
        }
    }
}
