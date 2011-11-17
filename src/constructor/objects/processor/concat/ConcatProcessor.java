package constructor.objects.processor.concat;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import static constructor.objects.processor.VariableProcessorUtils.specified;

/**
 * Процессор конкатенации строк
 *
 * @author Igor Usenko
 *         Date: 06.06.2009
 */
public class ConcatProcessor extends AbstractVariableProcessor {

    public static final String CONCAT_PROCESSOR_NAME = "concat";
    public static final String FIRST_PARAMETER_NAME = "first";
    public static final String SECOND_PARAMETER_NAME = "second";

    private final String first;
    private final String second;

    private static final String TEMP_VARIABLE_NAME = "$$$temp$$$";

    public ConcatProcessor(final String _in, final String _first, final String _second, final String _out) {
        super(_in, _out);

        Assert.notNull(_first, "First variable name is null");
        this.first = _first;

        Assert.notNull(_second, "Second variable name is null");
        this.second = _second;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        String firstName = specified(this.first) ? this.first : this.input;
        String secondName = specified(this.second) ? this.second : Variables.DEFAULT_OUTPUT_VARIABLE_NAME;

        _variables.remove(TEMP_VARIABLE_NAME);

        copy(secondName, TEMP_VARIABLE_NAME, _variables, false);
        copy(firstName, TEMP_VARIABLE_NAME, _variables, true);
        copy(TEMP_VARIABLE_NAME, this.output, _variables, false);

        _variables.remove(TEMP_VARIABLE_NAME);
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(CONCAT_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(FIRST_PARAMETER_NAME, this.first);
        result.addParameter(SECOND_PARAMETER_NAME, this.second);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }

    private void copy(final String _source, final String _dest, final Variables _variables, final boolean _concat) {
        VariableIterator iterator = _variables.getIterator(_source);

        while (iterator.hasNext()) {
            int key = iterator.getKey();

            String value = _concat ? iterator.next() + getStoredValue(_dest, _variables, key) : (String) iterator.next();

            _variables.put(_dest, key, value);
        }

    }

    private String getStoredValue(final String _name, final Variables _variables, final int _key) {
        String value = _variables.get(_name, _key);

        return value == null ? "" : value;
    }
}
