package constructor.objects.processor.get_group;

import constructor.objects.processor.AbstractVariableProcessor;
import constructor.objects.processor.OccurrenceSet;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * –еализует выбор фрагмента строки соответствующего заданным группам
 * в заданном регул€рном выражении
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public class GetGroupProcessor extends AbstractVariableProcessor {

    public static final String GET_GROUP_PROCESSOR_NAME = "getGroup";
    public static final String PATTERN_PARAMETER_NAME = "pattern";
    public static final String OCCURRENCES_PARAMETER_NAME = "occurrences";
    public static final String GROUP_PARAMETER_NAME = "group";

    private final Pattern pattern;
    private final OccurrenceSet occurrences;
    private final int group;

    public GetGroupProcessor(final String _in, final String _pattern, final OccurrenceSet _occurences, final int _group, final String _out) {
        super(_in, _out);

        Assert.notNull(_occurences, "Occurrence set is null.");
        Assert.isValidString(_pattern, "Invalid pattern string.");
        Assert.greater(_group, 0, "Invalid group id.");

        this.occurrences = _occurences;
        this.pattern = Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE);
        this.group = _group;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        int resultIndex = 0;
        VariableIterator iterator = _variables.getIterator(this.input);

        while (iterator.hasNext()) {
            Matcher matcher = this.pattern.matcher((String) iterator.next());
            int index = 0;

            while (matcher.find()) {

                if (this.occurrences.acceptable(index)) {
                    _variables.put(this.output, resultIndex++, matcher.group(this.group));
                }

                ++index;
            }
        }
        /*
        if (resultIndex == 0) {
            _variables.put(this.output, "");
        }
        */
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(GET_GROUP_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(PATTERN_PARAMETER_NAME, this.pattern.toString());
        result.addParameter(OCCURRENCES_PARAMETER_NAME, this.occurrences.toString());
        result.addParameter(GROUP_PARAMETER_NAME, String.valueOf(this.group));
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}
