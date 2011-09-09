package work.unit.constructor.source.modificator;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.EmptySnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

/**
 * @author Igor Usenko
 *         Date: 28.11.2009
 */
public class MockProcessor extends AbstractVariableProcessor {

    public MockProcessor(final String _in, final String _out) {
        super(_in, _out);
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        VariableIterator iterator = _variables.getIterator(Variables.DEFAULT_INPUT_VARIABLE_NAME);
        int index = 0;

        while (iterator.hasNext()) {
            _variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, index++, (String) iterator.next());
        }
    }

    public Snapshot getSnapshot() {
        return new EmptySnapshot();
    }
}
