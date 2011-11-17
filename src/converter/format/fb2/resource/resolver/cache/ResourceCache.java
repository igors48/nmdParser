package converter.format.fb2.resource.resolver.cache;

import http.Data;

/**
 * ��� ��������
 *
 * @author Igor Usenko
 *         Date: 08.11.2009
 */
public interface ResourceCache {
    /**
     * �������� ������ � ���. ���� ����� ��� ��� ���� - ��������.
     * ������������� ������ ���� � ������� ������ �������� ���� �������� ���� �������.
     *
     * @param _url  �����
     * @param _data ������
     */
    void put(String _url, Data _data);

    /**
     * ���������� ������ �� ����. ���� ��� ������������ - �������
     *
     * @param _url �����
     * @return ������ ��� null ���� ������ ������ � ���� ��� ��� ��� ������������
     */
    Data get(String _url);

    /**
     * ������ ������������� ������ ����� ����
     */
    void commitToc();

    /**
     * ��������� ����� ������ ����
     */
    void stop();

}
