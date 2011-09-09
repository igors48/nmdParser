package constructor.objects.processor.load.adapter;

import app.controller.NullController;
import app.workingarea.ServiceManager;
import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.load.LoadProcessor;
import downloader.batchloader.StandardBatchLoader;
import util.Assert;

/**
 * Адаптер для процессора LoadProcessor
 *
 * @author Igor Usenko
 *         Date: 04.08.2009
 */
public class LoadProcessorAdapter implements VariableProcessorAdapter {

    private final ServiceManager serviceManager;

    private String in;
    private String out;
    private String url;
    private String referer;

    public LoadProcessorAdapter(final ServiceManager _serviceManager) {
        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;
        this.url = "";
        this.in = "";
        this.out = "";
        this.referer = "";
    }

    public VariableProcessor getProcessor() throws ConfigurationException {

        try {
            return new LoadProcessor(this.in, this.url, this.referer, this.out, new StandardBatchLoader(this.serviceManager.getDownloader(), new NullController()));
        } catch (ServiceManager.ServiceManagerException e) {
            throw new ConfigurationException(e);
        }
    }

    public void setAttributeIn(final String _value) {
        Assert.isValidString(_value, "In value is invalid");
        this.in = _value;
    }

    public void setAttributeOut(final String _value) {
        Assert.isValidString(_value, "Out value is invalid");
        this.out = _value;
    }

    public void setAttributeReferer(final String _value) {
        Assert.isValidString(_value, "Referer value is invalid");
        this.referer = _value;
    }

    public void setElementUrl(final String _value) {
        Assert.isValidString(_value, "Url value is invalid");
        this.url = _value;
    }
}
