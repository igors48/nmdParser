package constructor.objects.processor.chain;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import debug.Snapshot;
import debug.snapshot.TopLevelObjectSnapshot;
import util.Assert;
import variables.Variables;

/**
 * Реализует обработку по цепочке из обработчиков
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class ChainProcessor implements VariableProcessor {

    private final ChainProcessorAdapter adapter;

    public ChainProcessor(final ChainProcessorAdapter _adapter) {
        Assert.notNull(_adapter, "Chain processor adapter is null");
        this.adapter = _adapter;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        try {

            this.adapter.appendSnapshot(getSnapshot());
            this.adapter.appendSnapshot(_variables.getSnapshot());

            for (VariableProcessor processor : this.adapter.getProcessors()) {

                if (this.adapter.isCancelled()) {
                    break;
                }

                this.adapter.appendSnapshot(processor.getSnapshot());

                processor.process(_variables);

                this.adapter.appendSnapshot(_variables.getSnapshot());
            }
        } catch (ConfigurationException e) {
            throw new VariableProcessorException(e);
        }
    }

    public Snapshot getSnapshot() {
        return new TopLevelObjectSnapshot(this.adapter.getId());
    }
}
