package constructor.objects.processor.filter;

import com.nmd.philter.Philter;
import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.Variables;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public class FilterProcessor extends AbstractVariableProcessor {

    public static final String FILTER_PROCESSOR_NAME = "filter";

    public FilterProcessor(final String _in, final String _out) {
        super(_in, _out);
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        String inputValue = _variables.get(this.input);

        String filtered = new Philter().filter(inputValue);

        _variables.put(this.output, filtered);
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(FILTER_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }

}
