package constructor.objects.processor;

import debug.Snapshot;

/**
 * Интерфейс работы с отладочной информацией
 *
 * @author Igor Usenko
 *         Date: 27.06.2010
 */
public interface Debug {
    /**
     * Добавляет отладочный снапшот
     *
     * @param _snapshot снапшот
     */
    void appendSnapshot(Snapshot _snapshot);
}
