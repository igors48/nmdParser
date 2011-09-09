package app.cli.command;

/**
 * ��������� ����������� �������.
 *
 * @author Igor Usenko
 *         Date: 14.04.2009
 */
public interface Command {

    /**
     * ��������� �������
     *
     * @throws CommandExecutionException ���� �������� ��������
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
