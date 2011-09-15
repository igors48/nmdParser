package work.unit.constructor.processor;

import constructor.objects.processor.filter.FilterProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public class FilterProcessorTest extends TestCase {

    public FilterProcessorTest(final String _name) {
        super(_name);
    }

    public void testSnapshot() {
        FilterProcessor processor = new FilterProcessor("content", "filtered");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(FilterProcessor.FILTER_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(2, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("content", parameters.get(0).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(1).getName());
        assertEquals("filtered", parameters.get(1).getValue());
    }
}
