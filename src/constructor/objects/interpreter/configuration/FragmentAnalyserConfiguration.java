package constructor.objects.interpreter.configuration;

import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.dateparser.adapter.StandardDateParserAdapter;
import constructor.objects.dateparser.configuration.DateParserConfiguration;
import constructor.objects.dateparser.core.DateParserAdapter;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Конфигурация анализатора фрагмента
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public class FragmentAnalyserConfiguration implements Blank {

    private String id;

    private StandardChainProcessorAdapter nickProcessor;
    private StandardChainProcessorAdapter infoProcessor;
    private StandardChainProcessorAdapter avatarProcessor;
    private StandardChainProcessorAdapter titleProcessor;
    private StandardChainProcessorAdapter dateProcessor;
    private StandardChainProcessorAdapter contentProcessor;

    private DateParserConfiguration dateParserConfiguration;

    private int timeCorrection;

    public void setNickProcessor(final StandardChainProcessorAdapter _processor) {
        Assert.notNull(_processor, "Nick processor is null.");
        this.nickProcessor = _processor;
    }

    public void setInfoProcessor(final StandardChainProcessorAdapter _processor) {
        Assert.notNull(_processor, "Info processor is null.");
        this.infoProcessor = _processor;
    }

    public void setAvatarProcessor(final StandardChainProcessorAdapter _processor) {
        Assert.notNull(_processor, "Avatar processor is null.");
        this.avatarProcessor = _processor;
    }

    public void setTitleProcessor(final StandardChainProcessorAdapter _processor) {
        Assert.notNull(_processor, "Title processor is null.");
        this.titleProcessor = _processor;
    }

    public void setDateProcessor(final StandardChainProcessorAdapter _processor) {
        Assert.notNull(_processor, "Date processor is null.");
        this.dateProcessor = _processor;
    }

    public void setContentProcessor(final StandardChainProcessorAdapter _processor) {
        Assert.notNull(_processor, "Content processor is null.");
        this.contentProcessor = _processor;
    }

    public StandardChainProcessorAdapter getAvatarProcessor() {
        return this.avatarProcessor;
    }

    public StandardChainProcessorAdapter getContentProcessor() {
        return this.contentProcessor;
    }

    public StandardChainProcessorAdapter getDateProcessor() {
        return this.dateProcessor;
    }

    public StandardChainProcessorAdapter getInfoProcessor() {
        return this.infoProcessor;
    }

    public StandardChainProcessorAdapter getNickProcessor() {
        return this.nickProcessor;
    }

    public StandardChainProcessorAdapter getTitleProcessor() {
        return this.titleProcessor;
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Fragment id is not valid.");
        this.id = _id;
    }

    public String getId() {
        return this.id;
    }

    public List<UsedObject> getUsedObjects() {
        List<UsedObject> result = newArrayList();

        if (this.nickProcessor != null) {
            result.add(new UsedObject(this.nickProcessor.getId(), ObjectType.PROCESSOR));
        }

        if (this.infoProcessor != null) {
            result.add(new UsedObject(this.infoProcessor.getId(), ObjectType.PROCESSOR));
        }

        if (this.avatarProcessor != null) {
            result.add(new UsedObject(this.avatarProcessor.getId(), ObjectType.PROCESSOR));
        }

        if (this.titleProcessor != null) {
            result.add(new UsedObject(this.titleProcessor.getId(), ObjectType.PROCESSOR));
        }

        if (this.dateProcessor != null) {
            result.add(new UsedObject(this.dateProcessor.getId(), ObjectType.PROCESSOR));
        }

        if (this.contentProcessor != null) {
            result.add(new UsedObject(this.contentProcessor.getId(), ObjectType.PROCESSOR));
        }

        if (this.dateParserConfiguration != null) {
            result.add(new UsedObject(this.dateParserConfiguration.getId(), ObjectType.DATEPARSER));
        }

        return result;
    }

    public void setDateParserConfiguration(final DateParserConfiguration _configuration) {
        Assert.notNull(_configuration, "Date parser configuration is null.");

        this.dateParserConfiguration = _configuration;
    }

    public void setTimeCorrection(final String _value) {
        Assert.isValidString(_value, "Time correction value is not valid.");

        this.timeCorrection = Integer.valueOf(_value);
    }

    public DateParserAdapter getDateParserAdapter() {
        return this.dateParserConfiguration == null ? null : new StandardDateParserAdapter(this.dateParserConfiguration);
    }

    public int getTimeCorrection() {
        return this.timeCorrection;
    }
}
