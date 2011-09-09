package work.unit.greader;

import greader.profile.Profile;
import greader.profile.Profiles;
import junit.framework.TestCase;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 24.08.2011
 */
public class ProfilesTest extends TestCase {

    public ProfilesTest(final String _s) {
        super(_s);
    }

    public void testAddProfile() {
        Profiles profiles = new Profiles();

        profiles.addProfile("e", "p");
        profiles.addProfile("e1", "p1");

        assertTrue(profiles.profileExists("e"));
        assertTrue(profiles.profileExists("e1"));
    }

    public void testRemoveProfile() {
        Profiles profiles = new Profiles();

        profiles.addProfile("e", "p");
        profiles.addProfile("e1", "p1");

        profiles.removeProfile("e1");

        assertTrue(profiles.profileExists("e"));
        assertFalse(profiles.profileExists("e1"));
    }

    public void testFindProfile() {
        Profiles profiles = new Profiles();

        profiles.addProfile("e", "p");

        Profile found = profiles.find("e");

        assertEquals("e", found.getAccount().getEmail());
        assertEquals("p", found.getAccount().getPassword());
    }

    public void testChangeExistsProfilePasswordReturnsTrue() {
        Profiles profiles = new Profiles();

        profiles.addProfile("e", "p");

        boolean result = profiles.changeProfilePassword("e", "n");
        Profile found = profiles.find("e");

        assertTrue(result);
        assertEquals("n", found.getAccount().getPassword());
    }
    
    public void testChangeNotExistsProfilePasswordReturnsFalse() {
        Profiles profiles = new Profiles();

        profiles.addProfile("e", "p");

        boolean result = profiles.changeProfilePassword("e1", "n");
        Profile found = profiles.find("e");

        assertFalse(result);
        assertEquals("p", found.getAccount().getPassword());
    }
}
