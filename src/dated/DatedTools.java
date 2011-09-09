package dated;

import util.Assert;

/**
 * ��������� ����� ��� ������ � Dated
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public final class DatedTools {

    /**
     * ���������� ����� ������� Dated �� ����
     *
     * @param _first  ������ Dated
     * @param _second ������ Dated
     * @return ����� �������
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
