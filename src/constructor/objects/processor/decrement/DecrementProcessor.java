package constructor.objects.processor.decrement;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

/**
 * Процессор декремента на 1
 *
 * @author Igor Usenko
 *         Date: 03.07.2009
 */
public class DecrementProcessor extends AbstractVariableProcessor {

    public static final String DECREMENT_PROCESSOR_NAME = "dec";

    public DecrementProcessor(final String _in, final String _out) {
        super(_in, _out);
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        VariableIterator iterator = _variables.getIterator(this.input);

        while (iterator.hasNext()) {
            int key = iterator.getKey();
            String result = (String) iterator.next();

            try {
                int value = Integer.valueOf(result);
                value--;
                result = String.valueOf(value);
            } catch (Exception e) {
                // empty
            }
            _variables.put(this.output, key, result);
        }
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(DECREMENT_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}
