package constructor.objects.channel.core.analyser;

import constructor.objects.channel.core.ChannelAnalyser;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.ChannelDataTools;
import dated.item.modification.Modification;
import http.BatchLoader;
import http.Data;
import util.Assert;

/**
 * ����������� ���������� ���������� ������. ��������� ���������
 * ������ �� ���� � ��������� ���-�������� ��������������� �����
 * ����
 *
 * @author Igor Usenko
 *         Date: 31.12.2008
 */
public class StandardAnalyser implements ChannelAnalyser {

    public ChannelDataHeader getHeader(final Modification _modification, final BatchLoader _batchLoader, final String _coverUrl) throws ChannelAnalyserException {
        Assert.notNull(_modification, "Modification is null");
        Assert.notNull(_batchLoader, "Page loader is null");
        Assert.notNull(_coverUrl, "Cover URL is null");

        try {
            String title = _modification.getTitle();

            if (title == null || title.isEmpty()) {
                String pageImage = ChannelDataTools.loadImage(_modification.getUrl(), _batchLoader);
                title = ChannelAnalyserTools.parseTitle(pageImage);
            }

            String host = ChannelAnalyserTools.getHostName(_modification.getUrl());

            return new ChannelDataHeader(title, host, ChannelAnalyserTools.DEFAULT_LAST_NAME, _modification.getUrl(), _coverUrl);
        } catch (Data.DataException e) {
            throw new ChannelAnalyserException(e);
        }
    }

}
