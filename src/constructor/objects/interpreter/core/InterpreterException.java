package constructor.objects.interpreter.core;

/**
 * Исключение интерпретатора
 *
 * @author Igor Usenko
 *         Date: 09.04.2009
 */
public class InterpreterException extends Exception {

    public InterpreterException() {
    }

    public InterpreterException(String _s) {
        super(_s);
    }

    public InterpreterException(String _s, Throwable _throwable) {
        super(_s, _throwable);
    }

    public InterpreterException(Throwable _throwable) {
        super(_throwable);
    }
}
