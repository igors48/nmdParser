package constructor.objects.output.configuration;

/**
 * Варианты композиции данных канала в выходном документе
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public enum Composition {
    /**
     * Каждый экземпляр данных канала помещается в свой файл
     */
    ONE_TO_ONE,
    /**
     * Все экземпляры данных канала помещаются в один файл
     */
    MANY_TO_ONE
}
