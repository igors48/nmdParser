package constructor.objects;

/**
 * Исключение конфигурации. Сигналит об ошибке в конфигурационных
 * параметрах
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class ConfigurationException extends Exception {

    public ConfigurationException() {
    }

    public ConfigurationException(String _s) {
        super(_s);
    }

    public ConfigurationException(String _s, Throwable _throwable) {
        super(_s, _throwable);
    }

    public ConfigurationException(Throwable _throwable) {
        super(_throwable);
    }
}
