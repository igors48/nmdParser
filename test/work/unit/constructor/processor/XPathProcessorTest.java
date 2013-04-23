package work.unit.constructor.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.xpath.XPathProcessor;
import constructor.objects.processor.xpath.XPathProcessorMode;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.06.2009
 */
public class XPathProcessorTest extends TestCase {

    public XPathProcessorTest(final String _s) {
        super(_s);
    }

    // �������������� ����

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        String fixture = "<html><body><div><div><p>asd</p></div></div><div>def</div></body></html>";

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, fixture);

        XPathProcessor processor01 = new XPathProcessor("", "//body/div/div", XPathProcessorMode.INNER, "");
        processor01.process(variables);

        XPathProcessor processor02 = new XPathProcessor("", "//body/div[2]", XPathProcessorMode.INNER, "out02");
        processor02.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("<div><p>asd</p></div>", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals(1, variables.getSize("out02"));
        assertEquals("<div>def</div>", variables.get("out02"));
    }

    // ���� ����� HTML ����

    public void testIllformed() throws VariableProcessor.VariableProcessorException {
        String fixture = "<div><div><p>asd</p></div></div><div>def</div>";

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, fixture);

        XPathProcessor processor01 = new XPathProcessor("", "//body/div/div", XPathProcessorMode.INNER, "");
        processor01.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("<div><p>asd</p></div>", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // ���� �� ���������� ���� � �� ��� �����������

    public void testTag() throws VariableProcessor.VariableProcessorException {
        String fixture = "<div><div a=b b=c><p>asd</p></div></div><div>def</div>";

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, fixture);

        XPathProcessor processor01 = new XPathProcessor("", "html/body/div/div", XPathProcessorMode.TAG, "");
        processor01.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("<div a=\"b\" b=\"c\" />", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // ���� �� ���������� ��������� ����

    public void testAttribute() throws VariableProcessor.VariableProcessorException {
        String fixture = "<div><div a=\"b\" b=\"c\"><p>asd</p></div></div><div>def</div>";

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, fixture);

        XPathProcessor processor01 = new XPathProcessor("", "html/body/div/div/@a", XPathProcessorMode.INNER, "");
        processor01.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("b", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // ���� �� ������� ������������� ����������

    public void testDoubleEscape() throws VariableProcessor.VariableProcessorException {
        String fixture = "<div>a&b</div>";

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, fixture);

        XPathProcessor processor01 = new XPathProcessor("", "//div", XPathProcessorMode.INNER, "");
        processor01.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("<div>a&amp;b</div>", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    public void testDeleteNodes() throws VariableProcessor.VariableProcessorException {
        String fixture = "<html><head/><body><div>f</div><p>p</p><div>l</div></body></html>";

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, fixture);

        XPathProcessor processor = new XPathProcessor("", "//div", XPathProcessorMode.DELETE, "");
        processor.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("<html><head/><body><p>p</p></body></html>", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // ���� ��������

    public void testSnapshot() {
        XPathProcessor processor = new XPathProcessor("test", "content2", XPathProcessorMode.INNER, "out");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(XPathProcessor.XPATH_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(XPathProcessor.EXPRESSION_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("content2", parameters.get(1).getValue());

        assertEquals(XPathProcessor.MODE_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("INNER", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("out", parameters.get(3).getValue());
    }

}
