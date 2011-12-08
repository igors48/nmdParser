package work.unit.greader;

import greader.profile.PasswordEncoder;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.11.2011
 */
public class PasswordEncoderTest extends TestCase {

    public PasswordEncoderTest(final String _name) {
        super(_name);
    }

    public void testSmoke() throws Exception {
        PasswordEncoder passwordEncoder = new PasswordEncoder();

        String fixture = "fixture";
        String coded = passwordEncoder.marshal(fixture);
        String encoded = passwordEncoder.unmarshal(coded);

        Assert.assertEquals(fixture, encoded);
    }
    
}
