package constructor.objects.snippet.core;

import constructor.objects.snippet.configuration.SnippetConfiguration;
import timeservice.TimeService;

/**
 * Интерфейс адаптера обработчика сниппетов
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public interface SnippetProcessorAdapter {

    /**
     * Возвращает конфигурацию сниппета
     *
     * @return конфигурация сниппета
     * @throws SnippetProcessorAdapterException
     *          если не получилось
     */
    SnippetConfiguration getSnippetConfiguration() throws SnippetProcessorAdapterException;

    /**
     * Возвращает время последнего обновления сниппета
     *
     * @return время последнего обновления. -1 если определить не удалось
     * @throws SnippetProcessorAdapterException
     *          если не получилось
     */
    long getLastUpdateTime() throws SnippetProcessorAdapterException;

    /**
     * Устанавливает время последнего обновления сниппета
     *
     * @param _time устанавливаемое время
     * @throws SnippetProcessorAdapterException
     *          если не получилось
     */
    void setLastUpdateTime(long _time) throws SnippetProcessorAdapterException;

    /**
     * Возвращает используемый тайм сервис
     *
     * @return тайм сервис
     */
    TimeService getTimeService();

    public class SnippetProcessorAdapterException extends Exception {

        public SnippetProcessorAdapterException() {
        }

        public SnippetProcessorAdapterException(final String _s) {
            super(_s);
        }

        public SnippetProcessorAdapterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public SnippetProcessorAdapterException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
