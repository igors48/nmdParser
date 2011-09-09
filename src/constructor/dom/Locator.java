package constructor.dom;

import app.templater.Template;
import constructor.dom.locator.Mask;
import constructor.objects.simpler.configuration.SimplerConfiguration;

import java.io.InputStream;
import java.util.List;

/**
 * ��������� ������ ����������� ��������
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Locator {

    /**
     * �� ����� � ���� ���������� ������ ���� � ��������� ����� �������
     *
     * @param _id   ���
     * @param _type ���
     * @return ������ ���� � ��������� ����� �������
     * @throws LocatorException ���� �� ���������� ����� ��������� ������
     */
    String getSourceFile(final String _id, final ObjectType _type) throws LocatorException;

    /**
     * �� ����� � ���� ���������� ����� �� �������� ����� ���� ������ ������
     *
     * @param _id   ���
     * @param _type ���
     * @return ��������� �������
     * @throws LocatorException ���� �� ���������� ����� ��������� ������
     */
    InputStream locate(final String _id, final ObjectType _type) throws LocatorException;

    /**
     * ���������� ������ ���� ���� ��������� �������� ��������� ����
     *
     * @param _type ��� �������
     * @param _mask ����� ������ ��������
     * @return ������ ����
     * @throws LocatorException ���� �� ����������
     */
    List<String> locateAll(final ObjectType _type, final Mask _mask) throws LocatorException;

    /**
     * ��������� ���������� �������� �������� � ����������� � ��������� ������
     *
     * @param _name      ��� �����������
     * @param _templates �������� ��������
     * @throws LocatorException ���� �� ����������
     */
    void storeTemplates(final String _name, final List<Template> _templates) throws LocatorException;

    /**
     * ������� ���� � ������������� �������� (���� ������������ �� ��������������)
     *
     * @param _configuration ������������ ��������
     * @throws LocatorException ���� �� ����������
     */
    void removeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException;

    /**
     * ��������� ������������ �������� � ����� (���� ������������ �� ��������������)
     *
     * @param _configuration ������������ ��������
     * @throws LocatorException ���� �� ����������
     */
    void storeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException;

    public class LocatorException extends Exception {

        public LocatorException() {
        }

        public LocatorException(String s) {
            super(s);
        }

        public LocatorException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public LocatorException(Throwable throwable) {
            super(throwable);
        }
    }
}