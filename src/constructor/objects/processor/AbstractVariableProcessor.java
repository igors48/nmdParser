package constructor.objects.processor;

import variables.Variables;

import static constructor.objects.processor.VariableProcessorUtils.specified;

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
        this.input = specified(_in) ? _in : Variables.DEFAULT_INPUT_VARIABLE_NAME;
        this.output = specified(_out) ? _out : Variables.DEFAULT_OUTPUT_VARIABLE_NAME;
    }

}
