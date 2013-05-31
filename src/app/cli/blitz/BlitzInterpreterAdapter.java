package app.cli.blitz;

import constructor.objects.AdapterException;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.interpreter.core.FragmentAnalyser;
import constructor.objects.interpreter.core.InterpreterAdapter;
import constructor.objects.interpreter.core.PageAnalyser;
import constructor.objects.interpreter.core.PageListAnalyser;
import constructor.objects.interpreter.core.standard.OneFragmentPageAnalyser;
import constructor.objects.interpreter.core.standard.OnePageListAnalyser;
import constructor.objects.interpreter.core.standard.StandardFragmentAnalyser;
import dated.item.modification.Modification;
import http.BatchLoader;
import timeservice.StandardTimeService;
import util.Assert;

/**
 * ������� ����-��������������
 *
 * @author Igor Usenko
 *         Date: 29.10.2009
 */
public class BlitzInterpreterAdapter implements InterpreterAdapter {

    private static final String BLITZ_INTERPRETER_ADAPTER_ID = "blitz_interpreter_adapter";
    private static final int GET_ALL_ITEMS = 0;

    private final BatchLoader batchLoader;
    private final Modification modification;
    private final FragmentAnalyserConfiguration fragmentAnalyserConfiguration;
    private final int precachedItemsCount;
    private final long pauseBetweenRequests;

    public BlitzInterpreterAdapter(final Modification _modification, final FragmentAnalyserConfiguration _fragmentAnalyserConfiguration, final BatchLoader _batchLoader, final int _precachedItemsCount, final long _pauseBetweenRequests) {
        Assert.notNull(_modification, "Modification is null");
        this.modification = _modification;

        Assert.notNull(_fragmentAnalyserConfiguration, "Fragment analyser configuration is null");
        this.fragmentAnalyserConfiguration = _fragmentAnalyserConfiguration;

        Assert.notNull(_batchLoader, "Page loader is null");
        this.batchLoader = _batchLoader;

        Assert.greater(_precachedItemsCount, 0, "Precached items count <= 0");
        this.precachedItemsCount = _precachedItemsCount;

        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");
        this.pauseBetweenRequests = _pauseBetweenRequests;
    }

    public PageListAnalyser getPageListAnalyser() throws AdapterException {
        return new OnePageListAnalyser();
    }

    public BatchLoader getWebPageLoader() throws AdapterException {
        return this.batchLoader;
    }

    public FragmentAnalyser getFragmentAnalyser() throws AdapterException {
        return new StandardFragmentAnalyser(this.fragmentAnalyserConfiguration, new StandardTimeService());
    }

    public PageAnalyser getPageAnalyser() throws AdapterException {
        return new OneFragmentPageAnalyser();
    }

    public Modification getModification() throws AdapterException {
        return this.modification;
    }

    public String getId() throws AdapterException {
        return BLITZ_INTERPRETER_ADAPTER_ID;
    }

    public int getLastItemCount() {
        return GET_ALL_ITEMS;
    }

    public int getPrecachedItemsCount() {
        return this.precachedItemsCount;
    }

    public long getPauseBetweenRequests() {
        return this.pauseBetweenRequests;
    }

    public boolean isCancelled() {
        return false;
    }
}
