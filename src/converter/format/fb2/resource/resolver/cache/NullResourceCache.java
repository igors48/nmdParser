package converter.format.fb2.resource.resolver.cache;

import http.Data;
import util.Assert;

/**
 * �������� ���� ��������
 *
 * @author Igor Usenko
 *         Date: 08.11.2009
 */
public class NullResourceCache implements ResourceCache {

    public void put(final String _url, final Data _data) {
        Assert.isValidString(_url, "URL is not valid");
        Assert.notNull(_data, "Data is null");
        // empty
    }

    public Data get(final String _url) {
        Assert.isValidString(_url, "URL is not valid");

        return null;
    }

    public void commitToc() {
        // empty
    }

    public void stop() {
        // empty
    }
}
