package work.unit.datautil;

import http.data.DataUtil;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Author : Igor Usenko ( igors48@gmail.com )
 * Date : 20.04.13
 */
public class DetectCharsetTest {

    @Test
    public void charsetDetectWithCharsetKey() {
        String data = "noisecharset=utf-8\"noise";

        String charset = DataUtil.detectCharSet(data);

        Assert.assertEquals("utf-8", charset);
    }

    @Test
    public void charsetDetectWithEncodingKey() {
        String data = "noiseencoding=\"utf-8\"noise";

        String charset = DataUtil.detectCharSet(data);

        Assert.assertEquals("utf-8", charset);
    }

    @Test
    public void ifKeysDoesNotFoundThenNullReturns() throws Exception {
        String data = "noisenoise";

        String charset = DataUtil.detectCharSet(data);

        Assert.assertNull(charset);
    }
}
