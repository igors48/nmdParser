package constructor.objects.output.configuration;

/**
 * Перечисление возможных режимов формирования секции для каждой даты
 *
 * @author Igor Usenko
 *         Date: 15.11.2009
 */
public enum DateSectionMode {
    /**
     * секция даты не формируется
     */
    OFF,
    /**
     * секция для даты формируется, только если в документе дат больше одной
     */
    AUTO,
    /**
     * секция даты формируется всегда
     */
    ALWAYS
}
