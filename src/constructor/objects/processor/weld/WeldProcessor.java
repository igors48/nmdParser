package constructor.objects.processor.weld;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import util.TextTools;
import variables.VariableIterator;
import variables.Variables;

import java.util.ArrayList;
import java.util.List;

/**
 * Процессор склейки элементов переменной с использованием
 * опционального разделителя
 *
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class WeldProcessor extends AbstractVariableProcessor {

    public static final String WELD_PROCESSOR_NAME = "weld";
    public static final String DIVIDER_PARAMETER_NAME = "divider";

    private final String divider;

    public WeldProcessor(final String _in, final String _divider, final String _out) {
        super(_in, _out);

        Assert.notNull(_divider, "Divider is null");
        this.divider = _divider;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null");

        String dividerText = "";

        if (!this.divider.isEmpty()) {
            dividerText = _variables.get(this.divider);
            dividerText = dividerText == null ? "" : dividerText;
        }

        List<String> values = new ArrayList<String>();
        VariableIterator iterator = _variables.getIterator(this.input);

        while (iterator.hasNext()) {
            String current = (String) iterator.next();

            if (!current.isEmpty()) {
                values.add(current);
            }
        }

        _variables.put(this.output, TextTools.weld(values, dividerText));
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(WELD_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(DIVIDER_PARAMETER_NAME, this.divider);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}