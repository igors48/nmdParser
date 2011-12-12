package constructor.objects.processor.remove_tag;

import constructor.objects.processor.AbstractVariableProcessor;
import constructor.objects.processor.OccurrenceSet;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;
import util.batchtext.BatchTextTools;
import util.batchtext.Boundary;
import util.html.HtmlTagBounds;
import util.html.HtmlTagExtractor;
import variables.Variables;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * –еализует удаление содержимого тэга. “эг задаетс€ регул€рным выражением,
 * что позвол€ет "узнать его в толпе"
 *
 * @author Igor Usenko
 *         Date: 20.05.2009
 */
public class RemoveTagProcessor extends AbstractVariableProcessor {

    public static final String REMOVE_TAG_PROCESSOR_NAME = "removeTag";
    public static final String PATTERN_PARAMETER_NAME = "pattern";
    public static final String OCCURRENCES_PARAMETER_NAME = "occurrences";

    private final String pattern;
    private final OccurrenceSet occurrences;

    public RemoveTagProcessor(final String _in, final String _pattern, final OccurrenceSet _occurrences, final String _out) {
        super(_in, _out);

        Assert.isValidString(_pattern, "Invalid pattern string.");
        this.pattern = _pattern;

        Assert.notNull(_occurrences, "Occurences set is null.");
        this.occurrences = _occurrences;
    }

    public void process(final Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        String inputValue = _variables.get(this.input);
        String result = inputValue;

        List<HtmlTagBounds> bounds = HtmlTagExtractor.extractTag(this.pattern, inputValue);

        List<Boundary> boundaries = getBoundaries(bounds);

        if (!boundaries.isEmpty()) {
            result = BatchTextTools.remove(result, boundaries);
        }

        _variables.put(this.output, result);
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(REMOVE_TAG_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(PATTERN_PARAMETER_NAME, this.pattern);
        result.addParameter(OCCURRENCES_PARAMETER_NAME, this.occurrences.toString());
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }

    private List<Boundary> getBoundaries(final List<HtmlTagBounds> _bounds) {
        List<Boundary> result = newArrayList();

        for (int index = 0; index < _bounds.size(); ++index) {

            if (this.occurrences.acceptable(index)) {
                HtmlTagBounds bound = _bounds.get(index);
                result.add(new Boundary(bound.getStart(), bound.getEnd() - 1));
            }
        }

        return result;
    }
}
