package constructor.objects.processor;

import static constructor.objects.processor.VariableProcessorUtils.specified;
import variables.Variables;

/**
 * ����������� ���������� ����������. ������ ��� ����������������
 * ����� ��� �������� ��������� � ����������
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public abstract class AbstractVariableProcessor implements VariableProcessor {

    protected final String input;
    protected final String output;

    public AbstractVariableProcessor(final String _in, final String _out) {
        this.input = !specified(_in) ? Variables.DEFAULT_INPUT_VARIABLE_NAME : _in;
        this.output = !specified(_out) ? Variables.DEFAULT_OUTPUT_VARIABLE_NAME : _out;
    }
}
