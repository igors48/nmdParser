package work.unit.http;

import http.HttpTools;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 20.09.2011
 */
public class HttpToolsTest extends TestCase {

    public HttpToolsTest(final String _name) {
        super(_name);
    }

    public void testSmoke() {
        final String charset = HttpTools.getCharset("Content-Type: text/html; charset=UTF-8");

        Assert.assertEquals("UTF-8", charset);
    }

    public void testWithSemicolon() {
        final String charset = HttpTools.getCharset("Content-Type: charset=UTF-8; text/html");

        Assert.assertEquals("UTF-8", charset);
    }

    public void testPasswordHideIfFound() {
        final String result = HttpTools.removePasswordFromString("asdfgpass=pass");

        Assert.assertEquals("asdfg[*HIDDEN*]", result);
    }

    public void testStringNotChangedIfPasswordNotFound() {
        final String result = HttpTools.removePasswordFromString("asdfgpast=past");

        Assert.assertEquals("asdfgpast=past", result);
    }

}
