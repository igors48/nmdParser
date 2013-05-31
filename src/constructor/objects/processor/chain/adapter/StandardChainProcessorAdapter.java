package constructor.objects.processor.chain.adapter;

import constructor.dom.Blank;
import constructor.dom.UsedObject;
import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import debug.DebugConsole;
import debug.Snapshot;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Стандартный адаптер цепного процессора
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class StandardChainProcessorAdapter implements Blank, ChainProcessorAdapter, VariableProcessorAdapter {

    private final DebugConsole debugConsole;
    private final List<VariableProcessorAdapter> adapters;

    private String id;

    private boolean cancelled;

    public StandardChainProcessorAdapter(final DebugConsole _debugConsole) {
        Assert.notNull(_debugConsole, "Debug console is null");
        this.debugConsole = _debugConsole;

        this.adapters = newArrayList();

        this.cancelled = false;
    }

    public void addAdapter(VariableProcessorAdapter _adapter) {
        Assert.notNull(_adapter, "Configurer is null.");
        this.adapters.add(_adapter);
    }

    public String getId() {
        return this.id;
    }

    public List<VariableProcessor> getProcessors() throws ConfigurationException {
        List<VariableProcessor> processors = newArrayList();

        for (VariableProcessorAdapter adapter : this.adapters) {
            processors.add(adapter.getProcessor());
        }

        return processors;
    }

    public void appendSnapshot(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Debug snapshot is null");
        this.debugConsole.appendSnapshot(_snapshot);
    }

    public List<UsedObject> getUsedObjects() {
        return newArrayList();
    }

    public void setId(String _id) {
        Assert.isValidString(_id, "Id is not valid.");
        this.id = _id;
    }

    public synchronized boolean isCancelled() {
        return this.cancelled;
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new ChainProcessor(this);
    }
}
