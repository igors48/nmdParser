package greader.profile;

import util.Assert;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 18.11.2011
 */
public class PasswordEncoder extends XmlAdapter<String,String> {
    
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
        return new StringBuilder(_value).reverse().toString();
    }

}
