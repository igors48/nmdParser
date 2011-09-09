package constructor.objects.interpreter.adapter;

import constructor.objects.AdapterException;
import constructor.objects.interpreter.core.FragmentAnalyser;
import constructor.objects.interpreter.core.InterpreterAdapter;
import constructor.objects.interpreter.core.PageAnalyser;
import constructor.objects.interpreter.core.PageListAnalyser;
import dated.item.modification.Modification;
import downloader.BatchLoader;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 12.07.2010
 */
public class SimpleInterpreterAdapter implements InterpreterAdapter {

    private static final String SIMPLER_INTERPRETER_ADAPTER_ID = "simpler_interpreter_adapter";

    private final Modification modification;


    public SimpleInterpreterAdapter(final Modification _modification) {
        Assert.notNull(_modification, "Modification is null");
        this.modification = _modification;
    }

    public PageListAnalyser getPageListAnalyser() throws AdapterException {
        return null;
    }

    public BatchLoader getWebPageLoader() throws AdapterException {
        return null;
    }

    public FragmentAnalyser getFragmentAnalyser() throws AdapterException {
        return null;
    }

    public PageAnalyser getPageAnalyser() throws AdapterException {
        return null;
    }

    public Modification getModification() throws AdapterException {
        return this.modification;
    }

    public String getId() throws AdapterException {
        return SIMPLER_INTERPRETER_ADAPTER_ID;
    }

    public int getLastItemCount() {
        return 0;
    }

    public int getPrecachedItemsCount() {
        return 0;
    }

    public long getPauseBetweenRequests() {
        return 0;
    }

    public boolean isCancelled() {
        return false;
    }
}
