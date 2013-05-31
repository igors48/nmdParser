package app.iui.tools;

import org.apache.commons.logging.Log;
import util.Assert;

import javax.swing.*;

/**
 * @author Igor Usenko
 *         Date: 05.10.2010
 */
public final class SwingTools {

    private static final String EXCEPTION_TOKEN = "Exception:";

    public static void invokeRunnable(final Runnable _runnable, final Log _log) {
        Assert.notNull(_runnable, "Runnable is null");
        Assert.notNull(_log, "Log is null");

        Thread thread = new Thread() {

            public void run() {

                try {
                    SwingUtilities.invokeAndWait(_runnable);
                } catch (Exception e) {
                    _log.error(e);
                }
            }
        };

        thread.start();
    }

    public static String createErrorText(final Throwable _cause) {
        String errorText = "";

        if (_cause != null) {
            String message = _cause.getMessage();

            if (message != null) {
                int index = message.lastIndexOf(EXCEPTION_TOKEN);

                errorText = index == -1 ? message : message.substring(index + EXCEPTION_TOKEN.length());
            }
        }

        return errorText.isEmpty() ? "" : ": '" + errorText + "'.";
    }

    private SwingTools() {
        // empty
    }

}
