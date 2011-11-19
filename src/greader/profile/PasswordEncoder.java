package greader.profile;

import util.Assert;
import util.Base64;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 18.11.2011
 */
public class PasswordEncoder extends XmlAdapter<String,String> {
    private static final String UTF_8_CHARSET = "UTF-8";

    @Override
    public String unmarshal(final String _value) throws Exception {
        Assert.notNull(_value, "Value is null");

        return reverse(_value);
    }

    @Override
    public String marshal(final String _value) throws Exception {
        Assert.notNull(_value, "Value is null");

        return reverse(_value);
    }
    
    private String reverse(final String _value) {
        return _value;
        //return new StringBuilder(_value).reverse().toString();
    }

//    private String gzip(final String _value) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byteArrayOutputStream.write(_value.getBytes(UTF_8_CHARSET));
//        GZIPOutputStream gzipOutputStream = new GZIPOutputStream()
//    }

}
