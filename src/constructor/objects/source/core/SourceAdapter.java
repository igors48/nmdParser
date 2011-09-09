package constructor.objects.source.core;

import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.strategies.StoreStrategy;
import constructor.objects.strategies.UpdateStrategy;
import dated.item.modification.stream.ModificationList;
import timeservice.TimeService;

import java.util.Date;

/**
 * ��������� �������� ��������� �����������. �� ����������
 * �� ���� �� ������� �� ���������� null. ���� �������
 * ����������� ���������� �� �����-���� �������� ���������� -
 * ������������� ����������.
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public interface SourceAdapter extends Controller {
    /**
     * ���������� ������������� ���������
     *
     * @return �������������
     * @throws AdapterException ���� �� ����������
     */
    String getId() throws AdapterException;

    /**
     * ���������� ������ �����������
     *
     * @return ������
     * @throws AdapterException ���� �� ����������
     */
    ModificationFetcher getFetcher() throws AdapterException;

    /**
     * ���������� ���������� (����������� � ���������� ������)
     * ������ �����������
     *
     * @return ���������
     * @throws AdapterException ���� �� ����������
     */
    ModificationList getList() throws AdapterException;

    /**
     * ��������� ������ ����������� ��� ������������ �������������
     *
     * @param _list ������
     * @throws AdapterException ���� �� ����������
     */
    void storeList(ModificationList _list) throws AdapterException;

    /**
     * ���������� ��������� ��������� �����������
     *
     * @return ���������
     * @throws AdapterException ���� �� ����������
     */
    ModificationProcessor getProcessor() throws AdapterException;

    /**
     * ���������� ��������� �������� ����������� � ������
     *
     * @return ��������� ��������
     * @throws AdapterException ���� �� ����������
     */
    StoreStrategy getStoreStrategy() throws AdapterException;

    /**
     * ���������� ��������� ���������� ��������� �����������
     *
     * @return ��������� ����������
     * @throws AdapterException ���� �� ����������
     */
    UpdateStrategy getUpdateStrategy() throws AdapterException;

    /**
     * ���������� ���� ���������� ���������� ���������.
     * ���� �������� ����� ��� �� ���� �� ����� �� ���������� -
     * ������������ ������ �����.
     *
     * @return ���� ���������� ����������
     * @throws AdapterException ���� �� ����������
     */
    Date getLastUpdateTime() throws AdapterException;

    /**
     * ������������� ���� ���������� ���������� ������ ������� ����
     *
     * @throws AdapterException ���� �� ����������
     */
    void setLastUpdateTimeToCurrent() throws AdapterException;

    /**
     * ���������� ��������� ������ �������
     *
     * @return ������ �������
     */
    TimeService getTimeService();
}
