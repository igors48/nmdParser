package app.cli.blitz;

import app.VersionInfo;
import app.cli.blitz.request.BlitzRequest;
import app.workingarea.ServiceManager;
import app.workingarea.Settings;
import app.workingarea.Workspace;
import constructor.objects.channel.core.Channel;
import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.core.DocumentBuilder;
import constructor.objects.source.core.Source;
import dated.DatedItem;
import dated.item.modification.stream.ModificationList;
import util.Assert;

import java.util.List;

/**
 * ���������� ���� �������
 *
 * @author Igor Usenko
 *         Date: 28.10.2009
 */
public class BlitzRequestProcessor {

    private final BlitzRequest request;
    private final ServiceManager serviceManager;
    private final Settings settings;
    private final Workspace workspace;
    private final VersionInfo versionInfo;

    public BlitzRequestProcessor(final BlitzRequest _request, final ServiceManager _serviceManager, final Settings _settings, final Workspace _workspace, final VersionInfo _versionInfo) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;

        Assert.notNull(_settings, "Settings is null");
        this.settings = _settings;

        Assert.notNull(_workspace, "Workspace is null");
        this.workspace = _workspace;

        Assert.notNull(_versionInfo, "Version information is null");
        this.versionInfo = _versionInfo;
    }

    public List<String> process() throws BlitzRequestProcessorException {

        try {
            ModificationList modificationList = getModificationList();
            ChannelDataList channelDataList = getChannelDataList(modificationList);

            DatedItem latest = ChannelDataTools.getLatestItem(channelDataList);

            if (latest != null) {
                this.request.setLatestItemTime(latest.getDate().getTime());
            }

            return createDocument(channelDataList);
        } catch (Source.SourceException e) {
            throw new BlitzRequestProcessorException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new BlitzRequestProcessorException(e);
        } catch (Channel.ChannelException e) {
            throw new BlitzRequestProcessorException(e);
        } catch (DocumentBuilder.DocumentBuilderException e) {
            throw new BlitzRequestProcessorException(e);
        }
    }

    private List<String> createDocument(final ChannelDataList _channelDataList) throws DocumentBuilder.DocumentBuilderException {
        BlitzDocumentBuilderAdapter adapter = new BlitzDocumentBuilderAdapter(this.request, _channelDataList, this.serviceManager, this.settings, this.workspace, this.versionInfo);
        DocumentBuilder builder = new DocumentBuilder(adapter);

        return builder.process();
    }

    private ModificationList getModificationList() throws Source.SourceException, ServiceManager.ServiceManagerException {
        BlitzSourceAdapter adapter = new BlitzSourceAdapter(this.request, this.serviceManager.getTimeService(), this.serviceManager.getBatchLoader(), this.settings);
        Source source = new Source(adapter);

        source.process();

        return adapter.getResult();
    }

    private ChannelDataList getChannelDataList(final ModificationList _modificationList) throws ServiceManager.ServiceManagerException, Channel.ChannelException {
        BlitzChannelAdapter adapter = new BlitzChannelAdapter(this.request, _modificationList, this.serviceManager, this.settings.getPrecachedItemsCount());
        Channel channel = new Channel(adapter);

        channel.process();

        return adapter.getResult();
    }

    public class BlitzRequestProcessorException extends Exception {

        public BlitzRequestProcessorException() {
        }

        public BlitzRequestProcessorException(final String _s) {
            super(_s);
        }

        public BlitzRequestProcessorException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public BlitzRequestProcessorException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
