package constructor.objects.channel.adapter;

import app.controller.Controller;
import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.objects.AdapterException;
import constructor.objects.channel.configuration.ChannelConfiguration;
import constructor.objects.channel.core.AbstractChannelAdapter;
import constructor.objects.channel.core.ChannelAnalyser;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.channel.core.analyser.CustomAnalyser;
import constructor.objects.channel.core.analyser.StandardAnalyser;
import constructor.objects.interpreter.adapter.StandardInterpreterAdapter;
import constructor.objects.interpreter.configuration.InterpreterConfiguration;
import constructor.objects.interpreter.core.InterpreterEx;
import constructor.objects.interpreter.core.standard.SimpleInterpreter;
import constructor.objects.interpreter.core.standard.StandardInterpreter;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import constructor.objects.source.core.ModificationListStorage;
import dated.item.modification.Modification;
import http.BatchLoader;
import timeservice.TimeService;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ����������� ������� ������
 *
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class StandardChannelAdapter extends AbstractChannelAdapter {

    private final ChannelConfiguration configuration;
    private final ConstructorFactory factory;

    public StandardChannelAdapter(final ChannelDataListStorage _channelDataListStorage, final ChannelConfiguration _configuration, final BatchLoader _batchLoader, final ModificationListStorage _modificationListStorage, final ConstructorFactory _factory, final int _forcedDays, final TimeService _timeService, final Controller _controller, final int _precachedItemsCount) {
        super(_channelDataListStorage, _batchLoader, _modificationListStorage, _forcedDays, _timeService, _controller, _precachedItemsCount, 0);

        Assert.notNull(_configuration, "Channel configuration is null.");
        this.configuration = _configuration;

        Assert.notNull(_factory, "Factory is null.");
        this.factory = _factory;
    }

    public long getPauseBetweenRequests() {
        return this.configuration.getPauseBetweenRequests();
    }

    public String getId() {
        return this.configuration.getId();
    }

    public ChannelAnalyser getAnalyser() throws AdapterException {

        try {
            ChannelAnalyser result;

            if (this.configuration.getProcessorId() == null) {
                result = new StandardAnalyser();
            } else {
                Constructor constructor = this.factory.getConstructor();
                ChainProcessorAdapter adapter = (ChainProcessorAdapter) constructor.create(this.configuration.getProcessorId(), ObjectType.PROCESSOR);

                result = new CustomAnalyser(new ChainProcessor(adapter));
            }

            return result;

        } catch (Constructor.ConstructorException e) {
            throw new AdapterException(e);
        }
    }

    public List<InterpreterEx> getInterpreters(final Modification _modification) throws AdapterException {
        Assert.notNull(_modification, "Modification is null.");

        try {
            List<InterpreterEx> result = newArrayList();

            if (isSimpleHandling()) {
                InterpreterConfiguration configuration = new InterpreterConfiguration();
                configuration.setId("simple");

                StandardInterpreterAdapter adapter = new StandardInterpreterAdapter(configuration, _modification, this.batchLoader, this.configuration.getLastItemCount(), getRefTimeService(_modification), getPrecachedItemsCount(), getPauseBetweenRequests());

                result.add(new SimpleInterpreter(adapter));
            } else {
                Constructor constructor = this.factory.getConstructor();

                InterpreterConfiguration configuration = (InterpreterConfiguration) constructor.create(this.configuration.getInterpreterId(), ObjectType.INTERPRETER);
                StandardInterpreterAdapter adapter = new StandardInterpreterAdapter(configuration, _modification, this.batchLoader, this.configuration.getLastItemCount(), getRefTimeService(_modification), getPrecachedItemsCount(), getPauseBetweenRequests());

                result.add(new StandardInterpreter(adapter));
            }

            return result;
        } catch (Constructor.ConstructorException e) {
            throw new AdapterException(e);
        }
    }

    public long getForcedAge() throws AdapterException {
        return this.configuration.isForced() ? 0 : this.forcedAge;
    }

    public List<String> getGenres() throws AdapterException {
        return this.configuration.getGenres();
    }

    public String getLang() throws AdapterException {
        return this.configuration.getLang();
    }

    public String getCoverUrl() throws AdapterException {
        return this.configuration.getCover();
    }

    public boolean isSimpleHandling() {
        return this.configuration.getInterpreterId() == null;
    }

    protected String getSourceId() {
        return this.configuration.getSourceId();
    }

}
