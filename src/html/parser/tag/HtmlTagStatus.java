package html.parser.tag;

/**
 * Статус присвоенный тегу после попытки его разбора
 *
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public enum HtmlTagStatus {
    /**
     * разобран без ошибок
     */
    NORMAL,
    /**
     * попытка разбора не удалась
     */
    MALFORMED,
    /**
     * неизвестный тег
     */
    UNKNOWN
}
