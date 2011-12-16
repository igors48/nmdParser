package app.workingarea;

import constructor.dom.Preprocessor;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.storage.Storage;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import debug.DebugConsole;
import greader.GoogleReaderAdapter;
import http.BatchLoader;
import resource.ConverterFactory;
import timeservice.TimeService;

import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public interface ServiceManager {

    void setExternalContext(final Map<String, String> _context);

    TimeService getTimeService() throws ServiceManagerException;

    FetcherFactory getFetcherFactory() throws ServiceManagerException;

    BatchLoader getBatchLoader();

    ConverterFactory getConverterFactory() throws ServiceManagerException;

    DebugConsole getDebugConsole();

    ResourceCache getResourceCache();

    ProcessWrapper getProcessWrapper();

    Storage getDefaultStorage() throws ServiceManagerException;

    void activateReflectionMode();

    void deactivateReflectionMode();

    Preprocessor getPreprocessor();

    GoogleReaderAdapter getGoogleReaderAdapter() throws ServiceManagerException;

    void cleanup();

    public class ServiceManagerException extends Exception {

        public ServiceManagerException() {
        }

        public ServiceManagerException(String _s) {
            super(_s);
        }

        public ServiceManagerException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public ServiceManagerException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
