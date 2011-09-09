package constructor.objects.snippet.core;

import app.cli.blitz.request.BlitzRequest;
import util.Assert;

/**
 * Обработчик сниппета
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public class SnippetProcessor {

    public static BlitzRequest process(final SnippetProcessorAdapter _adapter) throws SnippetProcessorException {
        Assert.notNull(_adapter, "Snippet processor adapter is null");

        try {
            BlitzRequest blitzRequest = SnippetConverter.convert(_adapter.getSnippetConfiguration());
            blitzRequest.setMaxAge(_adapter.getTimeService().getCurrentTime() - _adapter.getLastUpdateTime());

            return blitzRequest;
        } catch (SnippetConverter.SnippetConverterException e) {
            throw new SnippetProcessorException("Error processing snippet", e);
        } catch (SnippetProcessorAdapter.SnippetProcessorAdapterException e) {
            throw new SnippetProcessorException("Error processing snippet", e);
        }
    }

    public static class SnippetProcessorException extends Exception {

        public SnippetProcessorException() {
        }

        public SnippetProcessorException(final String _s) {
            super(_s);
        }

        public SnippetProcessorException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public SnippetProcessorException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
