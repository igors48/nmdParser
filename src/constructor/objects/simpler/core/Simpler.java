package constructor.objects.simpler.core;

import constructor.dom.ObjectType;
import constructor.objects.channel.core.ChannelAdapter;
import constructor.objects.output.core.DocumentBuilderAdapter;
import constructor.objects.source.core.ModificationFetcher;
import constructor.objects.source.core.SourceAdapter;
import constructor.objects.source.core.fetcher.RssFeedFetcher;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 06.07.2010
 */
public class Simpler {

    private final SimplerAdapter adapter;

    public Simpler(final SimplerAdapter _adapter) {
        Assert.notNull(_adapter, "Adapter is null");
        this.adapter = _adapter;
    }

    public SourceAdapter getSourceAdapter() {
        ModificationFetcher fetcher = new RssFeedFetcher(this.adapter.getFeedUrl(), this.adapter.getTimeService(), this.adapter.getTryCount(), this.adapter.getTimeout(), this.adapter.getMinTimeout());

        return new SimplerSourceAdapter(createId(this.adapter.getId(), ObjectType.SOURCE), fetcher, this.adapter.getStoreDays(), this.adapter.getTimeService(), this.adapter.getController(), this.adapter.getPropertiesCloud(), this.adapter.getModificationListStorage());
    }

    public ChannelAdapter getChannelAdapter() {

        return new SimplerChannelAdapter(createId(this.adapter.getId(), ObjectType.CHANNEL),
                createId(this.adapter.getId(), ObjectType.SOURCE),
                this.adapter.getCriterions(),
                this.adapter.getCoverUrl(),
                this.adapter.getChannelDataListStorage(),
                this.adapter.getDownloader(),
                this.adapter.getModificationListStorage(),
                this.adapter.getForcedDays(),
                this.adapter.getTimeService(),
                this.adapter.getController(),
                this.adapter.getPrecachedItemsCount(),
                this.adapter.getPauseBetweenRequests(),
                this.adapter.getDebugConsole());
    }

    public DocumentBuilderAdapter getOutputAdapter() {

        return new SimplerDocumentBuilderAdapter(createId(this.adapter.getId(), ObjectType.OUTPUT),
                createId(this.adapter.getId(), ObjectType.CHANNEL),
                this.adapter.getDocumentName(),
                this.adapter.isFromNewToOld(),
                this.adapter.getMaxFileNameLength(),
                this.adapter.getBranch(),
                this.adapter.getTempDir(),
                this.adapter.isLinksAsFootnotes(),
                this.adapter.removeExists(),
                this.adapter.isResolveImageLinks(),
                this.adapter.getDownloader(),
                this.adapter.getConverterFactory(),
                this.adapter.getResourceCache(),
                this.adapter.getDummy(),
                this.adapter.getConversionContext(),
                this.adapter.getStorages(),
                this.adapter.getTimeService(),
                this.adapter.getPropertiesCloud(),
                this.adapter.getVersionInfo(),
                this.adapter.getController(),
                this.adapter.getForcedDays(),
                this.adapter.getChannelDataListStorage());
    }

    public static String createId(final String _id, final ObjectType _type) {
        Assert.isValidString(_id, "Simpler id is not valid");
        Assert.notNull(_type, "Object type is null");

        return _id + "." + _type.toString().toLowerCase();
    }

}
