package constructor.objects.processor;

import debug.Snapshot;
import variables.Variables;

/**
 * �������� ����������� ����������
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public interface VariableProcessor {

    /**
     * ����������� ����� ������������ ������ ���-����� ���������
     * � ������������ ������ ����������
     *
     * @param _variables ������ ����������
     * @throws VariableProcessorException ���� �������� ��������
     */
    public void process(Variables _variables) throws VariableProcessorException;

    /**
     * ���������� ������� ����������, ������ � ���������� �����
     *
     * @return �������
     */
    Snapshot getSnapshot();

    public class VariableProcessorException extends Exception {

        public VariableProcessorException() {
        }

        public VariableProcessorException(String _s) {
            super(_s);
        }

        public VariableProcessorException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public VariableProcessorException(Throwable _throwable) {
            super(_throwable);
        }
    }
}