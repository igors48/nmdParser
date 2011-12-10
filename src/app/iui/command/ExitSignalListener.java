package app.iui.command;

/**
 * Слушатель главной формы ГУИ приложения
 *
 * @author Igor Usenko
 *         Date: 13.04.2010
 */
public interface ExitSignalListener {

    /**
     * Нотификация о подтвержденном закрытии
     */
    void onNormalExit();

    /**
     * Нотификация об аварийном закрытии
     */
    void onFailureExit(Throwable _cause);

}
