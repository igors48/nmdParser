package constructor.objects.source.configuration;

import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import util.Assert;
import util.sequense.PatternListSequencer;
import util.sequense.SequenceGenerationParams;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Конфигурация источника модификаций
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class SourceConfiguration implements Blank {

    private List<SequenceGenerationParams> urlGenerationPatterns;

    private String id;
    private FetcherType fetcherType;
    private String storeDays;
    private boolean autoUpdate;
    private ChainProcessorAdapter processorAdapter;
    private ChainProcessorAdapter customAdapter;

    private String from;
    private String to;
    private String step;
    private String mult;
    private String len;
    private String padd;

    private static final String AUTO_UPDATE_MODE_NAME = "auto";

    public SourceConfiguration() {
        this.urlGenerationPatterns = newArrayList();
        this.fetcherType = FetcherType.RSS;

        this.storeDays = "0";

        setSequenceGenerationDefaults();
    }

    public void setSingleUrl(final String _url) {
        Assert.isValidString(_url, "Url is not valid");
        this.urlGenerationPatterns.clear();
        addUrlGenerationParams(new SequenceGenerationParams(_url));
    }

    public void addUrlGenerationParams(final SequenceGenerationParams _params) {
        Assert.notNull(_params, "Url generation parameters is null");
        this.urlGenerationPatterns.add(_params);
    }

    public void setElementRssUrl(final String _value) {
        Assert.isValidString(_value, "RSS URL is invalid.");

        this.fetcherType = FetcherType.RSS;
        addUrlGenerationParams(getSequenceGenerationParams(_value));
        setSequenceGenerationDefaults();
    }

    public void setElementUrlUrl(final String _value) {
        Assert.isValidString(_value, "URL is invalid.");

        this.fetcherType = FetcherType.URL;
        addUrlGenerationParams(getSequenceGenerationParams(_value));
        setSequenceGenerationDefaults();
    }

    public void setElementCustom(Object _processor) {
        Assert.notNull(_processor, "Custom source processor is null.");

        this.fetcherType = FetcherType.CUSTOM;
        this.customAdapter = (ChainProcessorAdapter) _processor;
    }

    public ChainProcessorAdapter getCustomAdapter() {
        return this.customAdapter;
    }

    public String getId() {
        return this.id;
    }

    public FetcherType getFetcherType() {
        return this.fetcherType;
    }

    public List<String> getFetchedUrls() {
        return PatternListSequencer.getSequence(this.urlGenerationPatterns);
    }

    public void setProcessor(Object _processor) {
        Assert.notNull(_processor, "Processor is null.");
        this.processorAdapter = (ChainProcessorAdapter) _processor;
    }

    public ChainProcessorAdapter getProcessor() {
        return this.processorAdapter;
    }

    public void setAutoUpdate(String _mode) {
        Assert.isValidString(_mode, "Mode is not valid.");

        this.autoUpdate = AUTO_UPDATE_MODE_NAME.equals(_mode.trim());
    }

    public boolean isAutoUpdate() {
        return this.autoUpdate;
    }

    public void setStoreDays(final String _days) {
        Assert.isValidString(_days, "Days is not valid.");

        this.storeDays = _days;
    }

    public String getStoreDays() {
        return this.storeDays;
    }

    public void setId(String _id) {
        Assert.isValidString(_id, "Id is not valid.");
        this.id = _id;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String _from) {
        Assert.isValidString(_from, "From value is not valid");
        this.from = _from;
    }

    public String getMult() {
        return this.mult;
    }

    public void setMult(final String _mult) {
        Assert.isValidString(_mult, "Mult value is not valid");
        this.mult = _mult;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(final String _step) {
        Assert.isValidString(_step, "Step value is not valid");
        this.step = _step;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(final String _to) {
        Assert.isValidString(_to, "To value is not valid");
        this.to = _to;
    }

    public String getLen() {
        return this.len;
    }

    public void setLen(final String _len) {
        Assert.isValidString(_len, "Length value is not valid");
        this.len = _len;
    }

    public String getPadd() {
        return this.padd;
    }

    public void setPadd(final String _padd) {
        Assert.isValidString(_padd, "Padding value is not valid");
        this.padd = _padd;
    }

    public List<UsedObject> getUsedObjects() {
        List<UsedObject> result = newArrayList();

        if (this.customAdapter != null) {
            result.add(new UsedObject(this.customAdapter.getId(), ObjectType.PROCESSOR));
        }

        if (this.processorAdapter != null) {
            result.add(new UsedObject(this.processorAdapter.getId(), ObjectType.PROCESSOR));
        }

        return result;
    }

    private void setSequenceGenerationDefaults() {
        this.from = "";
        this.to = "";
        this.step = "1";
        this.mult = "1";
        this.len = "";
        this.padd = "";
    }

    private SequenceGenerationParams getSequenceGenerationParams(final String _url) {
        return new SequenceGenerationParams(_url,
                parse(this.from),
                parse(this.to),
                parse(this.step),
                parse(this.mult),
                parse(this.len),
                this.padd);
    }

    private int parse(final String _value) {
        int result = 0;

        try {
            result = Integer.valueOf(_value);
        } catch (Exception e) {
            // empty
        }

        return result;
    }
}
