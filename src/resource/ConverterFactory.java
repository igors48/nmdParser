package resource;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public interface ConverterFactory {
    Converter getConverter(ResourceType _from, ResourceType _to);
}
