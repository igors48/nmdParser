package constructor.objects.processor.append;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import java.util.List;

import static constructor.objects.processor.VariableProcessorUtils.specified;
import static util.CollectionUtils.newArrayList;

/**
 * Процессор добавления элементов одной переменной к другой
 *
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class AppendProcessor extends AbstractVariableProcessor {

    public static final String APPEND_PROCESSOR_NAME = "append";
    public static final String FIRST_PARAMETER_NAME = "first";
    public static final String SECOND_PARAMETER_NAME = "second";

    private final String first;
    private final String second;

    public AppendProcessor(final String _in, final String _first, final String _second, final String _out) {
        super(_in, _out);

        Assert.notNull(_first, "First variable name is null");
        this.first = _first;

        Assert.notNull(_second, "Second variable name is null");
        this.second = _second;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        String firstName = specified(this.first) ? this.first : this.input;
        String secondName = specified(this.second) ? this.second : Variables.DEFAULT_OUTPUT_VARIABLE_NAME;

        VariableIterator iterator = _variables.getIterator(firstName);
        List<String> values = newArrayList();

        while (iterator.hasNext()) {
            values.add((String) iterator.next());
        }

        _variables.appendAll(secondName, values);
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(APPEND_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(FIRST_PARAMETER_NAME, this.first);
        result.addParameter(SECOND_PARAMETER_NAME, this.second);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}
