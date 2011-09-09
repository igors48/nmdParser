package dated;

import util.Assert;

/**
 * ”тилитный класс дл€ работы с Dated
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public final class DatedTools {

    /**
     * ¬озвращает самый поздний Dated из двух
     *
     * @param _first  первый Dated
     * @param _second второй Dated
     * @return самый поздний
     */
    public static Dated latest(final Dated _first, final Dated _second) {
        Assert.notNull(_first, "First dated item is null.");
        Assert.notNull(_second, "Second dated item is null.");

        return (new DatedComparator()).compare(_first, _second) == 1 ? _first : _second;
    }

    private DatedTools() {
        // empty
    }
}
