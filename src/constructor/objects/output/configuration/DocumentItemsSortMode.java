package constructor.objects.output.configuration;

/**
 * Режим сортировки элементов по дате в выходном документе
 *
 * @author Igor Usenko
 *         Date: 06.03.2010
 */
public enum DocumentItemsSortMode {
    /**
     * новые идут первыми
     */
    FROM_NEW_TO_OLD,
    /**
     * старые идут первыми
     */
    FROM_OLD_TO_NEW,
    /**
     * используется значение из "большой" конфигурации
     */
    DEFAULT
}
