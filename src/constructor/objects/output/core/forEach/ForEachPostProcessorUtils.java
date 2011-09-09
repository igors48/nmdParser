package constructor.objects.output.core.forEach;

import app.workingarea.ProcessWrapper;
import constructor.objects.output.core.ForEachPostProcessor;
import util.Assert;

/**
 * Утилиты для работы с постпроцессором документов
 *
 * @author Igor Usenko
 *         Date: 12.12.2009
 */
public final class ForEachPostProcessorUtils {

    public static ForEachPostProcessor createForEachPostProcessor(final ProcessWrapper _processWrapper, final String _path, final String _command, final long _wait) {
        Assert.notNull(_processWrapper, "Process wrapper is null");
        Assert.notNull(_command, "Command is null");

        return _path == null || _path.isEmpty() ? new NullForEachPostProcessor() : new ExternalForEachPostProcessor(_processWrapper, _path, _command, _wait);
    }

    private ForEachPostProcessorUtils() {
        // empty
    }
}
