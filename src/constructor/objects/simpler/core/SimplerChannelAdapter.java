package constructor.objects.simpler.core;

import app.cli.blitz.BlitzInterpreterAdapter;
import app.cli.blitz.request.CriterionType;
import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.AbstractChannelAdapter;
import constructor.objects.channel.core.ChannelAdapterTools;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.interpreter.adapter.SimpleInterpreterAdapter;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.interpreter.core.InterpreterAdapter;
import constructor.objects.interpreter.core.InterpreterEx;
import constructor.objects.interpreter.core.standard.SimpleInterpreter;
import constructor.objects.interpreter.core.standard.StandardInterpreter;
import constructor.objects.source.core.ModificationListStorage;
import dated.item.modification.Modification;
import debug.DebugConsole;
import http.BatchLoader;
import timeservice.TimeService;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 07.07.2010
 */
public class SimplerChannelAdapter extends AbstractChannelAdapter {

    private static final String SIMPLER_CHAIN_PROCESSOR_ADAPTER_ID = "simpler_chain_processor_adapter_id";

    private final String sourceId;
    private final String id;
    private final String criterions;
    private final boolean autoContentFiltering;
    private final String coverUrl;
    private final DebugConsole debugConsole;

    public SimplerChannelAdapter(final String _id,
                                 final String _sourceId,
                                 final String _criterions,
                                 final boolean _autoContentFiltering,
                                 final String _coverUrl,
                                 final ChannelDataListStorage _channelDataListStorage,
                                 final BatchLoader _batchLoader,
                                 final ModificationListStorage _modificationListStorage,
                                 final int _forcedDays,
                                 final TimeService _timeService,
                                 final Controller _controller,
                                 final int _precachedItemsCount,
                                 final long _pauseBetweenRequests,
                                 final DebugConsole _debugConsole) {
        super(_channelDataListStorage, _batchLoader, _modificationListStorage, _forcedDays, _timeService, _controller, _precachedItemsCount, _pauseBetweenRequests);

        Assert.isValidString(_id, "Id is not valid");
        this.id = _id;

        Assert.isValidString(_sourceId, "Source id is not valid");
        this.sourceId = _sourceId;

        Assert.notNull(_criterions, "Criterions list is null");
        this.criterions = _criterions;

        this.autoContentFiltering = _autoContentFiltering;

        Assert.notNull(_coverUrl, "Cover Url is null");
        this.coverUrl = _coverUrl;

        Assert.notNull(_debugConsole, "Debug console is null");
        this.debugConsole = _debugConsole;
    }

    protected String getSourceId() {
        return this.sourceId;
    }

    public String getId() {
        return this.id;
    }

    public List<InterpreterEx> getInterpreters(final Modification _modification) throws AdapterException {
        Assert.notNull(_modification, "Modification is null");

        List<InterpreterEx> result = new ArrayList<InterpreterEx>();

        if (isSimpleHandling()) {
            result.add(new SimpleInterpreter(new SimpleInterpreterAdapter(_modification)));
        } else {
            result.add(new StandardInterpreter(createInterpreterAdapter(_modification)));
        }

        return result;
    }

    public long getForcedAge() throws AdapterException {
        return this.forcedAge;
    }

    public String getCoverUrl() throws AdapterException {
        return this.coverUrl;
    }

    public boolean isSimpleHandling() {
        return this.criterions.isEmpty() && !autoContentFiltering;
    }

    private InterpreterAdapter createInterpreterAdapter(final Modification _modification) {
        final FragmentAnalyserConfiguration fragmentAnalyserConfiguration = autoContentFiltering ?
                ChannelAdapterTools.createContentFilterConfiguration(SIMPLER_CHAIN_PROCESSOR_ADAPTER_ID, this.debugConsole) :
                ChannelAdapterTools.createFragmentAnalyserConfiguration(
                        CriterionType.XPATH,
                        this.criterions,
                        SIMPLER_CHAIN_PROCESSOR_ADAPTER_ID,
                        this.debugConsole);

        return new BlitzInterpreterAdapter(_modification,
                fragmentAnalyserConfiguration,
                this.batchLoader,
                getPrecachedItemsCount(),
                getPauseBetweenRequests());
    }
}
