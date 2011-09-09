package constructor.dom.preprocessor;

import app.templater.PlaceHolderUtils;
import constructor.dom.Preprocessor;
import util.Assert;
import util.IOTools;

import java.io.*;
import java.util.Map;

/**
 * Препроцессор обработки плейсхолдеров типа ${name}
 *
 * @author Igor Usenko
 *         Date: 09.10.2009
 */
public class PlaceholderPreprocessor implements Preprocessor {

    private final Map<String, String> values;

    public PlaceholderPreprocessor(final Map<String, String> _values) {
        Assert.notNull(_values, "Values map is null");
        this.values = _values;
    }

    public InputStream preprocess(final InputStream _stream) throws PreprocessorException {
        Assert.notNull(_stream, "Stream for preprocessing is null");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(output);

        Reader reader = null;
        BufferedReader bufferedReader = null;

        try {
            reader = new InputStreamReader(_stream);
            bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                if (!line.isEmpty()) {
                    line = PlaceHolderUtils.replace(line, this.values);

                    if (PlaceHolderUtils.containsPlaceholder(line)) {
                        throw new PreprocessorException("Not enough data to complete template [ " + line.trim() + " ]");
                    }

                    writer.println(line);
                }
            }

            writer.flush();

            return new ByteArrayInputStream(output.toByteArray());

        } catch (IOException e) {
            throw new PreprocessorException(e);
        } finally {
            IOTools.close(bufferedReader);
            IOTools.close(reader);
            IOTools.close(writer);
            IOTools.close(output);
        }
    }
}
