package work.unit.placeholderpreprocessor;

import constructor.dom.Preprocessor;
import constructor.dom.preprocessor.PlaceholderPreprocessor;
import junit.framework.TestCase;
import util.IOTools;
import work.testutil.SaxLoaderTestUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 09.10.2009
 */
public class PlaceholderPreprocessorTest extends TestCase {

    public PlaceholderPreprocessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws Preprocessor.PreprocessorException {
        Map<String, String> values = new HashMap<String, String>();
        values.put("key01", "value01");
        values.put("key02", "value02");

        InputStream stream = null;
        InputStream result = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;

        try {
            String fixture = "text${key01}\n\rtest${key02}postfix";
            stream = SaxLoaderTestUtils.createStream(fixture);

            PlaceholderPreprocessor preprocessor = new PlaceholderPreprocessor(values);
            result = preprocessor.preprocess(stream);

            reader = new InputStreamReader(result);
            bufferedReader = new BufferedReader(reader);

            assertEquals("textvalue01", bufferedReader.readLine());
            assertEquals("testvalue02postfix", bufferedReader.readLine());
        } catch (IOException e) {
            fail();
        } finally {
            IOTools.close(bufferedReader);
            IOTools.close(reader);
            IOTools.close(result);
            IOTools.close(stream);
        }

    }

    // если не хватает данных - выбрасывается исключение

    public void testNotEnoughDataException() {
        Map<String, String> values = new HashMap<String, String>();
        values.put("key01", "value01");
        values.put("key02", "value02");

        InputStream stream = null;
        InputStream result = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;

        try {
            String fixture = "text${key01}\n\rtest${key02}${key03}postfix";
            stream = SaxLoaderTestUtils.createStream(fixture);

            PlaceholderPreprocessor preprocessor = new PlaceholderPreprocessor(values);
            result = preprocessor.preprocess(stream);

            fail();
        } catch (Preprocessor.PreprocessorException e) {
            System.out.println(e.getMessage());
        } finally {
            IOTools.close(bufferedReader);
            IOTools.close(reader);
            IOTools.close(result);
            IOTools.close(stream);
        }

    }
}
