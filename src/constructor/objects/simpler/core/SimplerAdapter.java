package constructor.objects.simpler.core;

import app.VersionInfo;
import app.controller.Controller;
import cloud.PropertiesCloud;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.storage.Storage;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import debug.DebugConsole;
import http.BatchLoader;
import resource.ConverterFactory;
import timeservice.TimeService;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 06.07.2010
 */
public interface SimplerAdapter {

    String getId();

    String getFeedUrl();

    int getStoreDays();

    TimeService getTimeService();

    PropertiesCloud getPropertiesCloud();

    Controller getController();

    ModificationListStorage getModificationListStorage();

    int getTryCount();

    int getTimeout();

    int getMinTimeout();

    String getCoverUrl();

    ChannelDataListStorage getChannelDataListStorage();

    BatchLoader getBatchLoader();

    int getForcedDays();

    int getPrecachedItemsCount();

    long getPauseBetweenRequests();

    String getDocumentName();

    boolean isFromNewToOld();

    int getMaxFileNameLength();

    String getBranch();

    String getTempDir();

    boolean isLinksAsFootnotes();

    boolean removeExists();

    boolean isResolveImageLinks();

    ConverterFactory getConverterFactory();

    ResourceCache getResourceCache();

    String getDummy();

    Fb2ResourceConversionContext getConversionContext();

    List<Storage> getStorages();

    VersionInfo getVersionInfo();

    String getCriterions();

    DebugConsole getDebugConsole();

    boolean isAutoContentFiltering();
}
