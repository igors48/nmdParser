package constructor.dom;

import java.io.InputStream;

/**
 * ������� �������������. ������� ����������� - "���� ����������� - ���� ������".
 * ������������ ��� �������� ��������� ����������� ������������� ��� ����,
 * ����� ������� ����� "����������" ������ ��  �� ���� �������.
 *
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public interface ConstructorFactory {

    /**
     * ������� ��������� ������������
     *
     * @return ��������� ������������
     */
    Constructor getConstructor();

    /**
     * ������� ��������� ������������ ��� ���������� ������ ������� �� ���������� ������
     *
     * @param _inputStream ����� ��� ����������
     * @return ��������� ������������
     */
    Constructor getConstructor(InputStream _inputStream);
}