package constructor.objects.channel.core;

import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.interpreter.core.InterpreterEx;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import http.BatchLoader;
import timeservice.TimeService;

import java.util.List;

/**
 * ��������� �������� ������ ��������� �����������. �� ����������
 * �� ���� �� ������� �� ���������� null. ���� �������
 * ����������� ���������� �� �����-���� �������� ���������� -
 * ������������� ����������.
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public interface ChannelAdapter extends Controller {

    String getId();

    /**
     * ���������� ������ ����������� ��� ����� ������
     *
     * @return ������ �����������
     * @throws AdapterException ���� �� ����������
     */
    ModificationList getModificationsList() throws AdapterException;

    /**
     * ���������� ����������� ����� ������ ������ ����� ������
     *
     * @return ������ ������
     * @throws AdapterException ���� �� ����������
     */
    ChannelDataList getChannelDataList() throws AdapterException;

    /**
     * ������� ������ ������ ������
     *
     * @throws AdapterException ���� �� ����������
     */
    void removeChannelDataList() throws AdapterException;

    /**
     * ��������� ������ ������ ������
     *
     * @param _datas ������ ������
     * @throws AdapterException ���� �� ����������
     */
    void storeChannelDataList(ChannelDataList _datas) throws AdapterException;

    /**
     * ���������� ���������� ������
     *
     * @return ���������� ������
     * @throws AdapterException ���� �� ����������
     */
    ChannelAnalyser getAnalyser() throws AdapterException;

    /**
     * ���������� ��������� �������
     *
     * @return ��������� �������
     * @throws AdapterException ���� �� ����������
     */
    BatchLoader getPageLoader() throws AdapterException;

    /**
     * ���������� �������� ��������������� ��� ������ �����������
     *
     * @param _modification �����������
     * @return �������� ���������������
     * @throws AdapterException ���� �� ����������
     */
    List<InterpreterEx> getInterpreters(Modification _modification) throws AdapterException;

    /**
     * ���������� ������� ��������� ������ ������ (� �������������) ���������� ��������������� ����������
     *
     * @return true - -1 - ��������������� ���
     *         0 - ������ ��������������,
     *         >0 - ������������� ����������� ��� ��� ������ ���������� ��������
     * @throws AdapterException ���� �� ����������
     */
    long getForcedAge() throws AdapterException;

    /**
     * ���������� ������ ������ ������
     *
     * @return ������ ������
     * @throws AdapterException ���� �� ����������
     */
    List<String> getGenres() throws AdapterException;

    /**
     * ���������� ���� ������
     *
     * @return ������������� �����
     * @throws AdapterException ���� �� ����������
     */
    String getLang() throws AdapterException;

    /**
     * ���������� ����� ������ ����� ���� ��������� ������� ��� ������
     *
     * @return �����
     * @throws AdapterException ���� �� ����������
     */
    String getCoverUrl() throws AdapterException;

    /**
     * ���������� ������ ���������� �������
     *
     * @return ������ �������
     * @throws AdapterException ���� �� ����������
     */
    TimeService getSystemTimeService() throws AdapterException;

    /**
     * ���������� ���������� �������������� ���������� ���������
     *
     * @return ���������� �������������� ���������� ���������
     */
    int getPrecachedItemsCount();

    /**
     * ���������� ����� �������� ����� �������� ���������������� ��������. Anti-DDoS
     *
     * @return ����� �������� � �������������
     */
    long getPauseBetweenRequests();

    /**
     * ���������� ����� ���������
     *
     * @return true ���� ���������� ������ ���������� �� ���������, false ���� ���
     *         ��������� ���������� ������������ ����� ����������� ��������� - �������������� � �.�.
     */
    boolean isSimpleHandling();
}
