package app.workingarea;

/**
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public interface ProcessWrapper {

    final int PROCESS_DONT_RUN = -1;

    final int PROCESS_TIMEOUT = -2;

    final int PROCESS_OK = 0;

    final int WAIT_FOREVER = -1;

    int call(String _id, String _cmd, long _wait);
}
