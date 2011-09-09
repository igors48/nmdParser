package app.templater;

/**
 * Перечисление типов контента шаблонов
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public enum TemplateContentType {
    /**
     * Шаблон источника обновлений
     */
    SOURCE,
    /**
     * Шаблон канала краткий (полнотекстовая лента)
     */
    BRIEF_CHANNEL,
    /**
     * Шаблон канала полный (лента с анонсами)
     */
    FULL_CHANNEL,
    /**
     * Шаблон интерпретатора
     */
    INTERPRETER,
    /**
     * Шаблон контент процессора
     */
    CONTENT_PROCESSOR,
    /**
     * Шаблон генератора документа
     */
    OUTPUT
}
