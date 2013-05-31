package work.testutil;

import util.FileTools;

import java.io.File;
import java.io.IOException;

/**
 * Знает корневой каталог для компонентных тестов и
 * со всеми делится этим знанием
 *
 * @author Igor Usenko
 *         Date: 03.04.2009
 */
public final class CompTestsUtils {

    public static String getCompTestsRoot() {
        return "work/compTests/";
    }

    public static void cleanupDir(final String _dir) throws IOException {
        File dir = new File(_dir);
        FileTools.delete(dir, true);

        if (!dir.mkdir()) {
            throw new IOException();
        }
    }

    private CompTestsUtils() {
        // empty
    }
}
