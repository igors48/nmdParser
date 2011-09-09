package constructor.objects.processor;

import constructor.objects.ConfigurationException;

/**
 * ��������� ������������� ���������� ����������
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public interface VariableProcessorAdapter {

    /**
     * ���������� ������������������ ���������
     *
     * @return ��������� ����������
     * @throws constructor.objects.ConfigurationException
     *          ���� �� ����������
     */
    VariableProcessor getProcessor() throws ConfigurationException;
}
