package constructor.objects.processor.chain;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.AbstractVariableProcessor;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.adapter.FirstOneProcessorAdapter;
import debug.Snapshot;
import debug.snapshot.TopLevelObjectSnapshot;
import util.Assert;
import variables.Variable;
import variables.Variables;
import variables.VariablesTools;

/**
 * Выполняет вложенные операторы до тех пор, пока они не завершатся, либо до первого вложенного оператора,
 * изменившего значение выходной переменной.
 *
 * @author Igor Usenko
 *         Date: 26.06.2010
 */
public class FirstOneProcessor extends AbstractVariableProcessor {

    private final FirstOneProcessorAdapter adapter;

    public FirstOneProcessor(final FirstOneProcessorAdapter _adapter) {
        super("", _adapter.getOut());
        this.adapter = _adapter;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        try {
            this.adapter.appendSnapshot(getSnapshot());
            this.adapter.appendSnapshot(_variables.getSnapshot());

            Variable start = new Variable(_variables.getVariable(this.output));

            for (VariableProcessor processor : this.adapter.getProcessors()) {

                if (this.adapter.isCancelled()) {
                    break;
                }

                this.adapter.appendSnapshot(processor.getSnapshot());

                processor.process(_variables);

                this.adapter.appendSnapshot(_variables.getSnapshot());

                boolean outputChanged = !VariablesTools.equals(start, _variables.getVariable(this.output));

                if (outputChanged) {
                    break;
                }
            }
        } catch (ConfigurationException e) {
            throw new VariableProcessorException(e);
        }
    }

    public Snapshot getSnapshot() {
        return new TopLevelObjectSnapshot(this.adapter.getId());
    }
}
