package constructor.objects.processor.extract_tag;

import constructor.objects.processor.AbstractVariableProcessor;
import constructor.objects.processor.OccurrenceSet;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import util.html.HtmlTagBounds;
import util.html.HtmlTagExtractor;
import variables.Variables;

import java.util.List;

/**
 * –еализует выборку содержимого тэга. “эг задаетс€ регул€рным выражением,
 * что позвол€ет "узнать его в толпе"
 *
 * @author Igor Usenko
 *         Date: 29.04.2009
 */
public class ExtractTagProcessor extends AbstractVariableProcessor {

    public static final String EXTRACT_TAG_PROCESSOR_NAME = "extractTag";
    public static final String PATTERN_PARAMETER_NAME = "pattern";
    public static final String OCCURRENCES_PARAMETER_NAME = "occurrences";

    private final String pattern;
    private final OccurrenceSet occurrences;

    public ExtractTagProcessor(final String _in, final String _pattern, final OccurrenceSet _occurrences, final String _out) {
        super(_in, _out);

        Assert.isValidString(_pattern, "Invalid pattern string.");
        this.pattern = _pattern;

        Assert.notNull(_occurrences, "Occurences set is null.");
        this.occurrences = _occurrences;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        String inputValue = _variables.get(this.input);
        _variables.put(this.output, "");

        List<HtmlTagBounds> bounds = HtmlTagExtractor.extractTag(this.pattern, inputValue);

        int resultIndex = 0;

        for (int index = 0; index < bounds.size(); ++index) {

            if (this.occurrences.acceptable(index)) {
                HtmlTagBounds bound = bounds.get(index);
                String result = inputValue.substring(bound.getContentStart(), bound.getEnd() - bound.getClosingTagLength());
                _variables.put(this.output, resultIndex++, result);
            }
        }
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(EXTRACT_TAG_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(PATTERN_PARAMETER_NAME, this.pattern);
        result.addParameter(OCCURRENCES_PARAMETER_NAME, this.occurrences.toString());
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }
}
