package log;

import org.apache.log4j.ConsoleAppender;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Поддержка вывода русских букв в лог консоль
 *
 * @author Igor Usenko
 *         Date: 19.09.2009
 */
public class CustomConsoleAppender extends ConsoleAppender {

    private static final String CONSOLE_CHARSET_PROPERTY = "console.charset";

    private static final String CP866_CHARSET = "CP866";

    public void activateOptions() {

        if (target.equals(SYSTEM_OUT)) {

            try {
                setWriter(new OutputStreamWriter(System.out, getCharsetName()));
            } catch (UnsupportedEncodingException e) {
                setWriter(new OutputStreamWriter(System.out));
            }
        } else {
            setWriter(new OutputStreamWriter(System.err));
        }
    }

    private String getCharsetName() {
        return System.getProperty(CONSOLE_CHARSET_PROPERTY, CP866_CHARSET);
    }
}
