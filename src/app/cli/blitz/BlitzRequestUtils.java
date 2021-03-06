package app.cli.blitz;

import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ������� ��� ������ � ���� ���������
 *
 * @author Igor Usenko
 *         Date: 12.12.2009
 */
public final class BlitzRequestUtils {

    public static List<String> appendBase(final String _base, final List<String> _urls) {
        Assert.isValidString(_base, "Base is not valid");
        Assert.notNull(_urls, "Urls list is null");
        Assert.isFalse(_urls.isEmpty(), "Urls list is empty");

        List<String> result = newArrayList();

        for (String url : _urls) {
            result.add(_base.concat(url));
        }

        return result;
    }

    private BlitzRequestUtils() {
        // empty
    }
}
