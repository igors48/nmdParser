package constructor.objects.processor.sequencer;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import util.sequense.StringSequencer;
import variables.Variables;

import java.util.List;

/**
 * Формирователь списка строк на основании шаблона и диапазона числовых
 * значений с указанным шагом
 *
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class SequenceProcessor extends AbstractVariableProcessor {

    public static final String SEQUENCE_PROCESSOR_NAME = "sequence";
    public static final String PATTERN_PARAMETER_NAME = "pattern";
    public static final String START_PARAMETER_NAME = "start";
    public static final String STOP_PARAMETER_NAME = "stop";
    public static final String STEP_PARAMETER_NAME = "step";
    public static final String MULT_PARAMETER_NAME = "mult";
    public static final String LEN_PARAMETER_NAME = "len";
    public static final String PADD_PARAMETER_NAME = "padd";

    private final String pattern;
    private final String start;
    private final String stop;
    private final String step;
    private final String mult;
    private final String len;
    private final String padd;

    public SequenceProcessor(final String _in, final String _pattern, final String _start, final String _stop, final String _step, final String _mult, final String _out, final String _len, final String _padd) {
        super(_in, _out);

        Assert.isValidString(_pattern, "Pattern is not valid.");
        Assert.isValidString(_start, "Start is not valid.");
        Assert.isValidString(_stop, "Stop is not valid.");
        Assert.isValidString(_step, "Step is not valid.");
        Assert.isValidString(_mult, "Mult is not valid.");
        Assert.notNull(_len, "Len is not valid.");
        Assert.notNull(_padd, "Padd is not valid.");

        this.pattern = _pattern;
        this.start = _start;
        this.stop = _stop;
        this.step = _step;
        this.mult = _mult;
        this.len = _len;
        this.padd = _padd;
    }

    public void process(final Variables _variables) throws VariableProcessorException {

        try {
            int startInt = Integer.valueOf(_variables.get(this.start));
            int stopInt = Integer.valueOf(_variables.get(this.stop));
            int stepInt = Integer.valueOf(_variables.get(this.step));
            int multInt = Integer.valueOf(_variables.get(this.mult));

            String lenVal = this.len.isEmpty() ? null : _variables.get(this.len);
            int lenInt = lenVal == null ? 0 : Integer.valueOf(lenVal);

            String paddVal = this.padd.isEmpty() ? null : _variables.get(this.padd);
            String padd = paddVal == null ? "0" : _variables.get(this.padd);

            List<String> result = StringSequencer.getSequence(_variables.get(this.pattern), startInt, stopInt, stepInt, multInt, lenInt, padd);

            _variables.putAll(this.output, result);
        } catch (NumberFormatException e) {
            _variables.put(this.output, StringSequencer.removeWildcards(_variables.get(this.pattern)));
        }
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(SEQUENCE_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(PATTERN_PARAMETER_NAME, this.pattern);
        result.addParameter(START_PARAMETER_NAME, this.start);
        result.addParameter(STOP_PARAMETER_NAME, this.stop);
        result.addParameter(STEP_PARAMETER_NAME, this.step);
        result.addParameter(MULT_PARAMETER_NAME, this.mult);
        result.addParameter(LEN_PARAMETER_NAME, this.len);
        result.addParameter(PADD_PARAMETER_NAME, this.padd);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}
