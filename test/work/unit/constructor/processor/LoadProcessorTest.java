package work.unit.constructor.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.load.LoadProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;
import work.unit.constructor.processor.mock.PageLoaderMock;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 04.08.2009
 */
public class LoadProcessorTest extends TestCase {

    public LoadProcessorTest(String s) {
        super(s);
    }

    // первоначальный тест

    public void testSmoke() throws UnsupportedEncodingException, VariableProcessor.VariableProcessorException {
        PageLoaderMock loader = new PageLoaderMock("testing");

        LoadProcessor processor = new LoadProcessor("", "url", "", "", loader);
        Variables variables = new Variables();

        processor.process(variables);

        assertEquals("testing", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест - url в переменной in

    public void testUrlInVariable() throws UnsupportedEncodingException, VariableProcessor.VariableProcessorException {
        PageLoaderMock loader = new PageLoaderMock("testing");

        LoadProcessor processor = new LoadProcessor("urlvar", "", "", "", loader);
        Variables variables = new Variables();
        variables.put("urlvar", "url");

        processor.process(variables);

        assertEquals("testing", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест - загрузка с реферером

    public void testLoadWithReferer() throws UnsupportedEncodingException, VariableProcessor.VariableProcessorException {
        PageLoaderMock loader = new PageLoaderMock("testing");

        LoadProcessor processor = new LoadProcessor("urlvar", "", "ref", "", loader);
        Variables variables = new Variables();
        variables.put("urlvar", "url");
        variables.put("ref", "referer");

        processor.process(variables);

        assertEquals("testing", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("referer", loader.getReferer());
    }

    // тест снапшота

    public void testSnapshot() throws UnsupportedEncodingException {
        PageLoaderMock loader = new PageLoaderMock("testing");

        LoadProcessor processor = new LoadProcessor("test", "content2", "ref", "out", loader);

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(LoadProcessor.LOAD_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(LoadProcessor.URL_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("content2", parameters.get(1).getValue());

        assertEquals(LoadProcessor.REFERER_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("ref", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("out", parameters.get(3).getValue());
    }
}
