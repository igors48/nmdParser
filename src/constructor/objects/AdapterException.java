package constructor.objects;

/**
 * Исключение адаптера объекта. Сигнализирует о проблемах во
 * взаимодействии адаптера с конфигурацией или с системой
 *
 * @author Igor Usenko
 *         Date: 22.03.2009
 */
public class AdapterException extends Exception {
    public AdapterException() {
    }

    public AdapterException(String _s) {
        super(_s);
    }

    public AdapterException(String _s, Throwable _throwable) {
        super(_s, _throwable);
    }

    public AdapterException(Throwable _throwable) {
        super(_throwable);
    }
}
