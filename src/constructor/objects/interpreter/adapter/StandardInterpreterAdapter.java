package constructor.objects.interpreter.adapter;

import app.controller.NullController;
import constructor.objects.AdapterException;
import constructor.objects.interpreter.configuration.InterpreterConfiguration;
import constructor.objects.interpreter.core.FragmentAnalyser;
import constructor.objects.interpreter.core.InterpreterAdapter;
import constructor.objects.interpreter.core.PageAnalyser;
import constructor.objects.interpreter.core.PageListAnalyser;
import constructor.objects.interpreter.core.standard.*;
import dated.item.modification.Modification;
import downloader.BatchLoader;
import downloader.Downloader;
import downloader.batchloader.StandardBatchLoader;
import timeservice.TimeService;
import util.Assert;

/**
 * Стандартный адаптер интерпретатора
 *
 * @author Igor Usenko
 *         Date: 09.04.2009
 */
public class StandardInterpreterAdapter implements InterpreterAdapter {

    private final InterpreterConfiguration interpreterConfiguration;
    private final Modification modification;
    private final BatchLoader batchLoader;

    private final int lastItemCount;
    private final TimeService refTimeService;
    private final int precachedItemsCount;
    private final long pauseBetweenRequests;

    private boolean cancelled;

    public StandardInterpreterAdapter(final InterpreterConfiguration _interpreterConfiguration, final Modification _modification, final Downloader _downloader, final int _lastPostCount, final TimeService _refTimeService, final int _precachedItemsCount, final long _pauseBetweenRequests) {
        Assert.notNull(_downloader, "Downloader is null");
        Assert.notNull(_interpreterConfiguration, "Interpreter configuration is null");
        Assert.notNull(_modification, "Modification is null");
        Assert.greaterOrEqual(_lastPostCount, 0, "Last post count < 0");
        Assert.notNull(_refTimeService, "Reference time service is null");
        Assert.greater(_precachedItemsCount, 0, "Precached items count <= 0");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        this.interpreterConfiguration = _interpreterConfiguration;
        this.modification = _modification;
        this.batchLoader = new StandardBatchLoader(_downloader, new NullController());
        this.lastItemCount = _lastPostCount;
        this.refTimeService = _refTimeService;
        this.precachedItemsCount = _precachedItemsCount;
        this.pauseBetweenRequests = _pauseBetweenRequests;
    }

    public PageListAnalyser getPageListAnalyser() throws AdapterException {

        if (this.interpreterConfiguration.getPageListAnalyser() == null) {
            return new OnePageListAnalyser();
        } else {
            return new StandardPageListAnalyserEx(this.interpreterConfiguration.getPageListAnalyser());
        }
    }

    public BatchLoader getWebPageLoader() throws AdapterException {
        return this.batchLoader;
    }

    public FragmentAnalyser getFragmentAnalyser() throws AdapterException {

        if (this.interpreterConfiguration.getFragmentAnalyserConfiguration() == null) {
            return new OneFragmentAnalyser();
        } else {
            return new StandardFragmentAnalyser(this.interpreterConfiguration.getFragmentAnalyserConfiguration(), this.refTimeService);
        }
    }

    public PageAnalyser getPageAnalyser() throws AdapterException {

        if (this.interpreterConfiguration.getPageAnalyser() == null) {
            return new OneFragmentPageAnalyser();
        } else {
            return new StandardPageAnalyserEx(this.interpreterConfiguration.getPageAnalyser());
        }
    }

    public Modification getModification() throws AdapterException {
        return this.modification;
    }

    public String getId() throws AdapterException {
        return this.interpreterConfiguration.getId();
    }

    public int getLastItemCount() {
        return this.lastItemCount;
    }

    public int getPrecachedItemsCount() {
        return this.precachedItemsCount;
    }

    public long getPauseBetweenRequests() {
        return this.pauseBetweenRequests;
    }

    public synchronized boolean isCancelled() {
        return this.cancelled;
    }

    public synchronized void setCancelled(final boolean _cancelled) {
        this.cancelled = _cancelled;
    }
}
