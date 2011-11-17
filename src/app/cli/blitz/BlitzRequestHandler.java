package app.cli.blitz;

import app.api.ApiFacade;
import app.cli.blitz.request.BlitzRequest;

import java.util.List;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public interface BlitzRequestHandler {

    /**
     * ������������ ���� ������
     *
     * @param _request    ��������� �������
     * @param _forcedDays ���������� ���� ��� ��������������� ���������� -1 - ��� ������; 0 - ���; >0 - ���������� ����
     * @return ������ ������ ���� �������������� ������
     * @throws app.api.ApiFacade.FatalException
     *          ���� �� ����������
     */
    List<String> processBlitzRequest(BlitzRequest _request, int _forcedDays) throws ApiFacade.FatalException;

}
