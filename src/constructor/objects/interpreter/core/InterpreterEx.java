package constructor.objects.interpreter.core;

import constructor.objects.interpreter.core.data.InterpreterData;

/**
 * ��������� �������������� �����������
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface InterpreterEx {

    /**
     * �������������� �����������
     *
     * @return ������ ���������� � ���� ������������� �����������
     * @throws InterpreterException ���� �� ����������
     */
    InterpreterData process() throws InterpreterException;
}