package constructor.objects.processor;

import debug.Snapshot;
import variables.Variables;

/**
 * Интерфес обработчика переменной
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public interface VariableProcessor {

    /**
     * Посредством своей деятельности вносит кое-какие изменения
     * в предложенный список переменных
     *
     * @param _variables список переменных
     * @throws VariableProcessorException если возникли проблемы
     */
    public void process(Variables _variables) throws VariableProcessorException;

    /**
     * Возвращает снапшот процессора, нужный в отладочных целях
     *
     * @return снапшот
     */
    Snapshot getSnapshot();

    public class VariableProcessorException extends Exception {

        public VariableProcessorException() {
        }

        public VariableProcessorException(String _s) {
            super(_s);
        }

        public VariableProcessorException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public VariableProcessorException(Throwable _throwable) {
            super(_throwable);
        }
    }
}