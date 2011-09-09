package debug;

import java.util.List;

/**
 * Преобразовывает снапшот в текстовое представление
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public interface SnapshotTextRenderer {

    /**
     * Формирует из снапшота список строк
     *
     * @param _snapshot подопытный снапшот
     * @return список строк
     */
    List<String> render(Snapshot _snapshot);

    /**
     * Возвращает префикс, который нужен для наглядности
     *
     * @return префикс
     */
    String getPrefix();
}
