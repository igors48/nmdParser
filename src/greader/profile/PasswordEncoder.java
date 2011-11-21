package greader.profile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.Base64;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.UnsupportedEncodingException;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 18.11.2011
 */
public class PasswordEncoder extends XmlAdapter<String, String> {

    private static final String UTF_8_CHARSET = "UTF-8";

    private final Log log;

    public PasswordEncoder() {
        this.log = LogFactory.getLog(getClass());
    }

    @Override
    public String unmarshal(final String _value) throws Exception {
        Assert.notNull(_value, "Value is null");

        return decode(reverse(_value));
    }

    @Override
    public String marshal(final String _value) throws Exception {
        Assert.notNull(_value, "Value is null");

        return reverse(encode(_value));
    }

    private String reverse(final String _value) {
        return new StringBuilder(_value).reverse().toString();
    }

    private String encode(final String _value) {

        try {
            return Base64.encodeBytes(_value.getBytes(UTF_8_CHARSET));
        } catch (UnsupportedEncodingException e) {
            this.log.error("Error encoding", e);

            return _value;
        }
    }

    private String decode(final String _value) {

        try {
            return new String(Base64.decode(_value), UTF_8_CHARSET);
        } catch (UnsupportedEncodingException e) {
            this.log.error("Error decoding", e);

            return _value;
        }
    }

}
