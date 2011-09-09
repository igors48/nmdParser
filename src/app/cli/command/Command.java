package app.cli.command;

/**
 * Интерфейс исполняемой команды.
 *
 * @author Igor Usenko
 *         Date: 14.04.2009
 */
public interface Command {

    /**
     * Выполняет команду
     *
     * @throws CommandExecutionException если возникли проблемы
     */
    void execute() throws CommandExecutionException;

    public class CommandExecutionException extends Exception {

        public CommandExecutionException() {
        }

        public CommandExecutionException(String _s) {
            super(_s);
        }

        public CommandExecutionException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public CommandExecutionException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
