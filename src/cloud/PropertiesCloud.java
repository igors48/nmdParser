package cloud;

import constructor.dom.ObjectType;

import java.util.Properties;

/**
 * ������ ��������� - ������������ ���������� ��������� ���
 * ����������/������ ��������� ������� ���� ��������
 *
 * @author Igor Usenko
 *         Date: 03.03.2009
 */
public interface PropertiesCloud {

    /**
     * ��������� �������� ���������
     *
     * @param _owner      ������������� ���������
     * @param _type       ��� ���������
     * @param _properties �������� ���������
     * @throws PropertyCloudException ���� �� ����������
     */
    void storeProperties(final String _owner, final ObjectType _type, final Properties _properties) throws PropertyCloudException;

    /**
     * ���������� �������� ���������
     *
     * @param _owner ������������� ���������
     * @param _type  ��� ���������
     * @return �������� ��� ������ �������� ���� ������� ��� (����� �� �����������)
     * @throws PropertyCloudException ���� �� ����������
     */
    Properties readProperties(final String _owner, final ObjectType _type) throws PropertyCloudException;

    /**
     * ������� �������� ���������
     *
     * @param _owner ������������� ���������
     * @param _type  ��� ���������
     * @throws PropertyCloudException ���� �� ����������
     */
    void removeProperties(final String _owner, final ObjectType _type) throws PropertyCloudException;

    public class PropertyCloudException extends Exception {
        public PropertyCloudException() {
        }

        public PropertyCloudException(final String _s) {
            super(_s);
        }

        public PropertyCloudException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public PropertyCloudException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
