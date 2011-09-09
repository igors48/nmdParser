package app.workingarea.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.IOTools;
import util.TextTools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Утилиты для менеджера процессов
 *
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public final class ProcessManagerUtils {

    public static final String DIRECTORY_KEY = "d";
    public static final String FILE_NAME_KEY = "f";
    public static final String FULL_FILE_NAME_KEY = "n";

    private static final String PLACEHOLDER_PREFIX = "%";

    private static final Log log = LogFactory.getLog(ProcessManagerUtils.class);

    /**
     * Заменяет плейсхолдеры значениями
     *
     * @param _template строка с плейсхолдерами
     * @param _parms    карта имя-значение
     * @return строка с замененными плейсхолдерами
     */
    public static String createCommandLine(final String _template, final Map<String, String> _parms) {
        Assert.isValidString(_template, "Template is not valid");
        Assert.notNull(_parms, "Parameter map is null");

        String result = _template;

        for (String name : _parms.keySet()) {
            result = TextTools.replaceAll(result, PLACEHOLDER_PREFIX + name, _parms.get(name));
        }

        return result;
    }

    public static List<String> getOutput(final InputStream _inputStream) {
        Assert.notNull(_inputStream, "Input stream is null");

        List<String> result = new ArrayList<String>();

        Reader reader = new InputStreamReader(_inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {

            while (bufferedReader.ready()) {
                result.add(bufferedReader.readLine());
            }

        } catch (IOException e) {
            log.error("Error reading process output", e);
        } finally {
            IOTools.close(bufferedReader);
            IOTools.close(reader);
        }

        return result;
    }

    private ProcessManagerUtils() {
        // empty
    }
}
