package app.templater;

import java.util.List;

/**
 * ������� ��������� ��������
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public interface TemplatesFactory {

    public static final String SOURCE_NAME = "source";
    public static final String CHANNEL_NAME = "channel";
    public static final String INTERPRETER_NAME = "interpreter";
    public static final String PROCESSOR_NAME = "content";
    public static final String OUTPUT_NAME = "output";

    /**
     * ���������� �������� ��������, ��������� �� ������ ����������
     *
     * @param _parameters ���������
     * @return �������� ��������
     */
    List<Template> createTemplates(TemplateParameters _parameters);
    
}
