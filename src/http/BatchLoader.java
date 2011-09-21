package http;

import app.controller.Controller;
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
     * @param _controller ���������� ��������
     * @return ������ ������� ����������� ������. ���� �������� ������
     *         �� ������� - ������������ ������ ������
     */
    Map<String, HttpData> loadUrls(List<String> _urls, long _pauseBetweenRequests, Controller _controller);

    /**
     * ��������� ������ � ���������� ����, ���������� ��������� �������
     *
     * @param _url     �����
     * @param _referer �������
     * @return ����������� ������
     */
    HttpData loadUrlWithReferer(String _url, String _referer);

    HttpData loadUrl(String _url);
    
}
