package constructor.objects.processor.chain;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.Debug;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;

import java.util.List;

/**
 * ��������� �������� ������� ����������
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public interface ChainProcessorAdapter extends Debug {
    /**
     * ���������� ������������� ����������
     *
     * @return �������������
     */
    String getId();

    /**
     * ��������� ������� ���������� � ������
     *
     * @param _adapter �������
     */
    void addAdapter(VariableProcessorAdapter _adapter);

    /**
     * ���������� ������� �����������
     *
     * @return �������
     * @throws constructor.objects.ConfigurationException
     *          ���� �������� ��������
     */
    List<VariableProcessor> getProcessors() throws ConfigurationException;

    /**
     * ���������� ���� ������ ������
     *
     * @return true �� ���������� false - ����������
     */
    boolean isCancelled();
}
