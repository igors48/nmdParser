package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Утилиты работы с потоком
 *
 * @author Igor Usenko
 *         Date: 14.03.2010
 */
public final class StreamTools {

    public static String readStream(final InputStream _stream) {
        Assert.notNull(_stream, "Input stream is null");

        StringBuilder result = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(_stream));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            // empty
        } finally {
            IOTools.close(reader);
        }

        return result.toString();
    }

    private StreamTools() {
        // empty
    }
}
