package constructor.objects.output.core;

import constructor.objects.channel.core.stream.ChannelDataList;
import flowtext.Document;
import timeservice.TimeService;

/**
 * ��������� ����������� ������ ������
 *
 * @author Igor Usenko
 *         Date: 25.04.2009
 */
public interface ChannelDataListProcessor {

    /**
     * ��������� �������� �� ������ ������
     *
     * @param _data        ������ ������
     * @param _title       ��������� ��������� (���� �� ������ - ������� ��������� �� ���������)
     * @param _timeService ������ �������
     * @return �������������� ��������
     */
    Document process(ChannelDataList _data, String _title, TimeService _timeService);
}
