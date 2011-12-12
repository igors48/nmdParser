package app.workingarea.service;

import app.workingarea.ProcessWrapper;
import app.workingarea.ServiceManager;
import constructor.dom.Preprocessor;
import constructor.dom.preprocessor.NullPreprocessor;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.storage.Storage;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import debug.DebugConsole;
import debug.console.NullDebugConsole;
import greader.GoogleReaderAdapter;
import http.BatchLoader;
import resource.ConverterFactory;
import timeservice.TimeService;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * ������ ������ ��������
 *
 * @author Igor Usenko
 *         Date: 05.08.2009
 */
public final class NullServiceManager implements ServiceManager {

    public void setExternalContext(final Map<String, String> _context) {
    }

    public TimeService getTimeService() throws ServiceManagerException {
        return null;
    }

    public FetcherFactory getFetcherFactory() throws ServiceManagerException {
        return null;
    }

    public BatchLoader getBatchLoader() {
        return null;
    }

    public ConverterFactory getConverterFactory() throws ServiceManagerException {
        return null;
    }

    public DebugConsole getDebugConsole() {
        return new NullDebugConsole();
    }

    public Map<String, String> getExternalContext() {
        return newHashMap();
    }

    public ResourceCache getResourceCache() {
        return null;
    }

    public ProcessWrapper getProcessWrapper() {
        return null;
    }

    public Storage getDefaultStorage() {
        return null;
    }

    public void activateReflectionMode() {
    }

    public void deactivateReflectionMode() {
    }

    public Preprocessor getPreprocessor() {
        return new NullPreprocessor();
    }

    public GoogleReaderAdapter getGoogleReaderAdapter() {
        return null;
    }

    public void cleanup() {
    }

}
