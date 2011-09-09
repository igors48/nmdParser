package app.workingarea.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.IOTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Igor Usenko
 *         Date: 16.04.2011
 */
public class StreamDumper extends Thread {

    private static final String OUTPUT_STREAM_DUMPER_THREAD_NAME = "OutputStreamDumperThread";
    private static final String ERROR_STREAM_DUMPER_THREAD_NAME = "ErrorStreamDumperThread";

    private final InputStream inputStream;
    private final StreamDumperType type;

    private final Log log;

    private StreamDumper(final InputStream _inputStream, final StreamDumperType _type) {
        this.inputStream = _inputStream;
        this.type = _type;

        this.log = LogFactory.getLog(getClass());

        setDaemon(true);
        setName(this.type == StreamDumperType.OUTPUT ? OUTPUT_STREAM_DUMPER_THREAD_NAME : ERROR_STREAM_DUMPER_THREAD_NAME);
    }

    public void run() {

        final InputStreamReader inputStreamReader = new InputStreamReader(this.inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = null;

        try {

            while ((line = bufferedReader.readLine()) != null) {
                logLine(line);
            }

        } catch (IOException e) {
            this.log.error(e);
        } finally {
            IOTools.close(bufferedReader);
            IOTools.close(inputStreamReader);
        }
    }

    private void logLine(final String _line) {

        if (this.type == StreamDumperType.OUTPUT) {
            this.log.info(_line);
        } else {
            this.log.error(_line);
        }
    }

    private enum StreamDumperType {
        ERROR,
        OUTPUT
    }

    public static StreamDumper createOutputStreamDumper(final InputStream _inputStream) {
        Assert.notNull(_inputStream, "Input stream is null");

        return new StreamDumper(_inputStream, StreamDumperType.OUTPUT);
    }

    public static StreamDumper createErrorStreamDumper(final InputStream _inputStream) {
        Assert.notNull(_inputStream, "Input stream is null");

        return new StreamDumper(_inputStream, StreamDumperType.ERROR);
    }

}