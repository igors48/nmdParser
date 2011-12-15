package constructor.objects.processor.xpath.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.xpath.XPathProcessor;
import constructor.objects.processor.xpath.XPathProcessorMode;
import util.Assert;

/**
 * Адаптер для XPath процессора
 *
 * @author Igor Usenko
 *         Date: 27.06.2009
 */
public class XPathProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;
    private String expression;
    private XPathProcessorMode mode;

    private static final String TAG_TOKEN = "tag";
    private static final String DELETE_TOKEN = "delete";

    public XPathProcessorAdapter() {
        this.mode = XPathProcessorMode.INNER;
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new XPathProcessor(this.in, this.expression, this.mode, this.out);
    }

    public void setAttributeIn(final String _value) {
        Assert.notNull(_value, "XPath : in value is null.");
        this.in = _value;
    }

    public void setAttributeOut(final String _value) {
        Assert.notNull(_value, "XPath : out value is null.");
        this.out = _value;
    }

    public void setAttributeMode(final String _value) {
        Assert.isValidString(_value, "XPath : mode value is not valid.");

        if (TAG_TOKEN.equalsIgnoreCase(_value)) {
            this.mode = XPathProcessorMode.TAG;
        }

        if (DELETE_TOKEN.equalsIgnoreCase(_value)) {
            this.mode = XPathProcessorMode.DELETE;
        }
    }

    public void setElementExpression(final String _value) {
        Assert.isValidString(_value, "XPath expression is not vlaid.");

        this.expression = _value;
    }

    public void setMode(final XPathProcessorMode _mode) {
        Assert.notNull(_mode, "Mode is null");
        this.mode = _mode;
    }

}
