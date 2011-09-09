package app.workingarea;

/**
 * Оболочка работы с внешними процессом.
 *
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public interface ProcessWrapper {

    /**
     * Код того, что внешний процесс не вызывался
     */
    public static final int PROCESS_DONT_RUN = -1;

    /**
     * Процесс был прерван по таймауту
     */
    public static final int PROCESS_TIMEOUT = -2;

    /**
     * Нормально все прошло с процессом
     */
    public static final int PROCESS_OK = 0;

    /**
     * Флаг ожидания завершения процесса
     */
    public static final int WAIT_FOREVER = -1;

    /**
     * Запускает внешний процесс, ожидает его завершения, возвращает exit code процесса
     *
     * @param _id   идентификатор процесса
     * @param _cmd  командная строка передаваемая процессу
     * @param _wait максимальное время ожидания в миллисекундах
     * @return системный exit code или собственный код если процесс не вызывался по какой-то причине
     *         либо был прерван по таймауту
     */
    int call(String _id, String _cmd, long _wait);
}
