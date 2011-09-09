package constructor.objects.output.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.OutputConfiguration;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента output в XML файле
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public class OutputElementHandler extends AbstractElementHandler {

    private static final String FORMAT_KEY = "format";
    private static final String STORAGE_KEY = "storage";
    private static final String BRANCH_KEY = "branch";
    private static final String CHANNEL_KEY = "channel";
    private static final String LINKS_AS_FOOTNOTES_KEY = "linksAsFootnotes";
    private static final String REMOVE_EXISTS_KEY = "removeExists";
    private static final String RESOLVE_IMAGE_LINKS_KEY = "resolveImageLinks";
    private static final String DATE_SECTION_MODE_KEY = "dateSectionMode";
    private static final String FROM_NEW_TO_OLD_KEY = "fromNewToOld";

    private static final String ONE_TO_ONE_KEY = "one-to-one";
    private static final String MANY_TO_ONE_KEY = "many-to-one";
    private static final String FOR_EACH_KEY = "forEach";

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();

        if (name == null) {
            throw new ElementHandlerException("Element name is null.");
        }

        if (name.equals(ONE_TO_ONE_KEY)) {
            ((OutputConfiguration) _blank).setComposition(Composition.ONE_TO_ONE);
        }

        if (name.equals(MANY_TO_ONE_KEY)) {
            ((OutputConfiguration) _blank).setComposition(Composition.MANY_TO_ONE);

            ElementHandler handler = new ManyToOneElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(FOR_EACH_KEY)) {
            ElementHandler handler = new ForEachElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }
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

        if (name.equals(FORMAT_KEY)) {
            handler = new FormatAttributeHandler();
        }

        if (name.equals(STORAGE_KEY)) {
            handler = new StorageAttributeHandler();
        }

        if (name.equals(BRANCH_KEY)) {
            handler = new BranchAttributeHandler();
        }

        if (name.equals(CHANNEL_KEY)) {
            handler = new ChannelAttributeHandler();
        }

        if (name.equals(LINKS_AS_FOOTNOTES_KEY)) {
            handler = new LinksAsFootnotesAttributeHandler();
        }

        if (name.equals(REMOVE_EXISTS_KEY)) {
            handler = new RemoveExistsAttributeHandler();
        }

        if (name.equals(RESOLVE_IMAGE_LINKS_KEY)) {
            handler = new ResolveImageLinksAttributeHandler();
        }

        if (name.equals(DATE_SECTION_MODE_KEY)) {
            handler = new DateSectionModeAttributeHandler();
        }

        if (name.equals(FROM_NEW_TO_OLD_KEY)) {
            handler = new FromNewToOldAttributeHandler();
        }

        if (handler == null) {
            throw new AttributeHandler.AttributeHandlerException("Can not find handler for attribute [ " + name + " ].");
        }

        handler.handle(name, value, _blank, _factory);
    }
}
