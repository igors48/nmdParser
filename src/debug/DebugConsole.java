package debug;

/**
 * Интерфейс отладочной консоли
 *
 * @author Igor Usenko
 *         Date: 27.08.2009
 */
public interface DebugConsole {

    /**
     * Добавляет отладочный снапшот
     *
     * @param _snapshot снапшот
     */
    void appendSnapshot(Snapshot _snapshot);
}
