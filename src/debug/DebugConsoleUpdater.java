package debug;

import java.util.List;

/**
 * Интерфейс обновлятеля дебаг консоли
 *
 * @author Igor Usenko
 *         Date: 30.08.2009
 */
public interface DebugConsoleUpdater {

    /**
     * Обновляет отладочный вывод для указанного иденитификатором объекта
     *
     * @param _name  идентификатор объекта
     * @param _image образ отладочного вывода
     * @throws DebugConsoleUpdaterException если не получилось
     */
    void update(String _name, List<String> _image) throws DebugConsoleUpdaterException;

    /**
     * Очистка отладочного вывода перед новым сеансом
     */
    void clean();

    class DebugConsoleUpdaterException extends Exception {

        public DebugConsoleUpdaterException() {
            super();
        }

        public DebugConsoleUpdaterException(final String _s) {
            super(_s);
        }

        public DebugConsoleUpdaterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public DebugConsoleUpdaterException(final Throwable _throwable) {
            super(_throwable);
        }

    }
    
}
