package constructor.objects.interpreter.core;

import constructor.objects.interpreter.core.data.InterpreterData;

/**
 * Интерфейс интерпретатора модификации
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public interface InterpreterEx {

    /**
     * Интерпретирует модификацию
     *
     * @return данные полученные в ходе интерпретации модификации
     * @throws InterpreterException если не получилось
     */
    InterpreterData process() throws InterpreterException;
}