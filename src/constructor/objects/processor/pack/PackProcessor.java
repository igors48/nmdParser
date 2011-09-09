package constructor.objects.processor.pack;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import java.util.LinkedList;

/**
 * Процессор удаления пустых элементов переменной
 *
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class PackProcessor extends AbstractVariableProcessor {

    public static final String PACK_PROCESSOR_NAME = "pack";

    public PackProcessor(final String _in, final String _out) {
        super(_in, _out);
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        LinkedList<String> result = new LinkedList<String>();
        VariableIterator iterator = _variables.getIterator(this.input);

        while (iterator.hasNext()) {
            String current = (String) iterator.next();

            if (!current.isEmpty()) {
                result.addLast(current);
            }
        }

        _variables.appendAll(this.output, result);
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(PACK_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}