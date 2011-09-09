package app.iui;

/**
 * Ресурс получения строк. Используется для локализации
 *
 * @author Igor Usenko
 *         Date: 12.04.2010
 */
public interface StringResource {

    static final String OK_BUTTON_TITLE_KEY = "button.ok.title.key";
    static final String CANCEL_BUTTON_TITLE_KEY = "button.cancel.title.key";

    /**
     * Возвращает строку по указанному идентификатору
     *
     * @param _key идентификатор
     * @return строка из ресурса
     */
    String getString(String _key);
}
