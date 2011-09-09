package constructor.objects.channel.core.analyser;

import constructor.objects.channel.core.ChannelAnalyser;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.ChannelDataTools;
import dated.item.modification.Modification;
import downloader.BatchLoader;
import downloader.Data;
import util.Assert;

/**
 * Стандартный анализатор информации канала. Формирует заголовок
 * канала из урла и заголовка веб-страницы соответствующей этому
 * урлу
 *
 * @author Igor Usenko
 *         Date: 31.12.2008
 */
public class StandardAnalyser implements ChannelAnalyser {

    public ChannelDataHeader getHeader(final Modification _modification, final BatchLoader _batchLoader, final String _coverUrl, long _pauseBetweenRequests) throws ChannelAnalyserException {
        Assert.notNull(_modification, "Modification is null");
        Assert.notNull(_batchLoader, "Page loader is null");
        Assert.notNull(_coverUrl, "Cover URL is null");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        try {
            String title = _modification.getTitle();

            if (title == null || title.isEmpty()) {
                String pageImage = ChannelDataTools.loadImage(_modification.getUrl(), _batchLoader, _pauseBetweenRequests);
                title = ChannelAnalyserTools.parseTitle(pageImage);
            }

            String host = ChannelAnalyserTools.getHostName(_modification.getUrl());

            return new ChannelDataHeader(title, host, ChannelAnalyserTools.DEFAULT_LAST_NAME, _modification.getUrl(), _coverUrl);
        } catch (Data.DataException e) {
            throw new ChannelAnalyserException(e);
        }
    }

}
