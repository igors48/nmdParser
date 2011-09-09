package constructor.objects.processor.setter;

import constructor.objects.processor.AbstractVariableProcessor;
import static constructor.objects.processor.VariableProcessorUtils.specified;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

/**
 * Процессор присвоения значения переменной
 *
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class SetterProcessor extends AbstractVariableProcessor {

    public static final String SETTER_PROCESSOR_NAME = "set";
    public static final String VALUE_PARAMETER_NAME = "value";
    public static final String INDEX_PARAMETER_NAME = "index";

    private final String value;
    private final String index;

    public SetterProcessor(final String _in, final String _value, final String _index, final String _out) {
        super(_in, _out);

        this.value = _value;
        this.index = _index;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        if (!specified(this.value) && !specified(this.index)) {
            copy(_variables);
            return;
        }

        if (!specified(this.value) && specified(this.index)) {
            indexedCopy(_variables);
            return;
        }

        if (specified(this.value) && specified(this.index)) {
            indexedSet(_variables);
            return;
        }

        _variables.put(this.output, this.value);
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(SETTER_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(VALUE_PARAMETER_NAME, this.value);
        result.addParameter(INDEX_PARAMETER_NAME, this.index);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }


    private void indexedSet(final Variables _variables) {
        VariableIterator iterator = _variables.getIterator(this.index);

        while (iterator.hasNext()) {
            Integer index = parse((String) iterator.next());

            if (index == null) {
                continue;
            }

            _variables.put(this.output, index, this.value);
        }
    }

    private void indexedCopy(final Variables _variables) {
        VariableIterator iterator = _variables.getIterator(this.index);

        while (iterator.hasNext()) {
            Integer index = parse((String) iterator.next());

            if (index == null) {
                continue;
            }

            String value = _variables.get(this.input, index);

            if (value == null) {
                continue;
            }

            _variables.put(this.output, index, value);
        }
    }

    private void copy(final Variables _variables) {
        VariableIterator iterator = _variables.getIterator(this.input);

        while (iterator.hasNext()) {
            int key = iterator.getKey();
            _variables.put(this.output, key, (String) iterator.next());
        }
    }

    private Integer parse(final String _data) {
        Integer result = null;

        try {
            result = new Integer(_data);
        } catch (Exception e) {
            // empty
        }

        return result;
    }
}
