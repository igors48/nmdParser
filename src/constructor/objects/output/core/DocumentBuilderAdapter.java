package constructor.objects.output.core;

import app.VersionInfo;
import app.controller.Controller;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DateSectionMode;
import flowtext.Document;
import timeservice.TimeService;

import java.util.List;

/**
 * ��������� �������� ����������� ���������
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public interface DocumentBuilderAdapter extends Controller {

    String getId();

    /**
     * ���������� ������ ������ ������� ������
     *
     * @return ������ ������ ������
     * @throws AdapterException ���� �� ����������
     */
    ChannelDataList getChannelDatas() throws AdapterException;

    /**
     * ���������� ������ �������
     *
     * @return ������ �������
     * @throws AdapterException ���� �� ����������
     */
    TimeService getTimeService() throws AdapterException;

    /**
     * ������������� �������� - ��������� � ���������
     *
     * @param _document ���������� ��������
     * @return ������ ������ ���� �������������� ������
     * @throws AdapterException ���� �� ����������
     */
    List<String> postprocessDocument(Document _document) throws AdapterException;

    /**
     * ���������� ����� ���������� ���������
     *
     * @return ����� ����������
     * @throws AdapterException ���� �� ����������
     */
    Composition getComposition() throws AdapterException;

    /**
     * ���������� ��� ��������� ��� ������ ���������� many-to-one
     *
     * @return ��� ��������� ���� ������ ������, ���� ��� �� ����������
     * @throws AdapterException ���� �� ����������
     */
    String getDocumentName() throws AdapterException;

    /**
     * ���������� ���� ���������� ���������� ���������.
     * ���� �������� ����� ��� �� ���� �� ����� �� ���������� -
     * ������������ ������ �����.
     *
     * @return ���� ���������� ����������
     * @throws AdapterException ���� �� ����������
     */
    long getLatestItemTime() throws AdapterException;

    /**
     * ������������� ���� ���������� ���������� ������ ��������� ����
     *
     * @param _time ��������� ����/�����
     * @throws AdapterException ���� �� ����������
     */
    void setLatestItemTime(long _time) throws AdapterException;

    /**
     * ���������� ����� ��������������� ���������� ����������
     *
     * @return true - ���� ����� �������������� false - ���� ����� �������
     * @throws AdapterException ���� �� ����������
     */
    boolean isForcedMode() throws AdapterException;

    /**
     * ���������� ����� ��������� ������ �� �����������
     *
     * @return true �������� ��������� �������� false ��������� ��, ��� ����
     */
    boolean resolveImageLinks();

    /**
     * ���������� ���������� � ������ ����������
     *
     * @return ���������� � ������
     */
    VersionInfo getVersionInfo();

    /**
     * ���������� ����� ������������ ������ ����
     *
     * @return ����� ������������ ������ ����
     */
    DateSectionMode getDateSectionMode();

    /**
     * ���������� ����� ���������� ��������� � ��������� �� ����
     *
     * @return true ���� �� ������� � ������� false ���� ��������
     */
    boolean isFromNewToOld();

}
