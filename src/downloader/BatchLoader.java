package downloader;

import html.HttpData;

import java.util.List;
import java.util.Map;

/**
 * ��������� ���������� web ������
 *
 * @author Igor Usenko
 *         Date: 01.12.2008
 */

public interface BatchLoader {

    /**
     * ��������� ������ �� ������� ��������� � ������. ��������� �� ����������.
     *
     * @param _urls                  ������ ������ �������
     * @param _pauseBetweenRequests �������� ����� ���������
     * @return ������ ������� ����������� ������. ���� �������� ������
     *         �� ������� - ������������ ������ ������
     */
    Map<String, HttpData> loadUrls(List<String> _urls, long _pauseBetweenRequests);

    
    /**
     * ������������ ������ ��������
     *
     * @param _requests ������ ��������
     * @return ��������� ���������
     */
    //Map<RequestList, HttpData> load(List<RequestList> _requests);

    /**
     * ��������� ������ � ���������� ����, ���������� ��������� �������
     *
     * @param _url     �����
     * @param _referer �������
     * @return ����������� ������
     */
    HttpData loadUrlWithReferer(String _url, String _referer);

    /**
     * ���������� ���������� � ��� �� ��� �� ������� ������� ���������
     *
     * @return true ���� ��� ������� false ���� �����
     */
    boolean cancelled();
}
