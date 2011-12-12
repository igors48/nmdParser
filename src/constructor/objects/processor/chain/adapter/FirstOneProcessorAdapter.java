package constructor.objects.processor.chain.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import constructor.objects.processor.chain.FirstOneProcessor;
import debug.DebugConsole;
import debug.Snapshot;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Адаптер процессора firstOne
 *
 * @author Igor Usenko
 *         Date: 27.06.2010
 */
public class FirstOneProcessorAdapter implements VariableProcessorAdapter, ChainProcessorAdapter {

    private static final String ID = "firstOne";

    private final DebugConsole debugConsole;
    private final List<VariableProcessorAdapter> adapters;

    private boolean cancelled;

    private String out;

    public FirstOneProcessorAdapter(final DebugConsole _debugConsole) {
        Assert.notNull(_debugConsole, "Debug console is null");
        this.debugConsole = _debugConsole;

        this.adapters = newArrayList();
        this.cancelled = false;
        this.out = "";
    }

    public void appendSnapshot(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Snapshot is null");
        this.debugConsole.appendSnapshot(_snapshot);
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new FirstOneProcessor(this);
    }

    public String getId() {
        return ID;
    }

    public void addAdapter(final VariableProcessorAdapter _adapter) {
        Assert.notNull(_adapter, "Adapter is null");
        this.adapters.add(_adapter);
    }

    public List<VariableProcessor> getProcessors() throws ConfigurationException {
        List<VariableProcessor> processors = newArrayList();

        for (VariableProcessorAdapter adapter : this.adapters) {
            processors.add(adapter.getProcessor());
        }

        return processors;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public String getOut() {
        return this.out;
    }

    public void setOut(final String _out) {
        Assert.isValidString(_out, "Out name is not valid");
        this.out = _out;
    }
}
