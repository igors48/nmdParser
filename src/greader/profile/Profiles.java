package greader.profile;

import util.Assert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Iterator;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 28.07.2011
 */
@XmlRootElement(name = "profiles")
public class Profiles {

    private List<Profile> profiles;

    public Profiles() {
        this.profiles = newArrayList();
    }

    @XmlElement(name = "profile", nillable = false, required = true)
    public List<Profile> getProfiles() {
        return this.profiles;
    }

    public void setProfiles(final List<Profile> _profiles) {
        Assert.notNull(_profiles, "Profiles is null");
        this.profiles = _profiles;
    }

    public Profile find(final String _email) {
        Assert.isValidString(_email, "EMail is not valid");

        Profile result = null;

        Iterator<Profile> iterator = this.profiles.iterator();

        while (iterator.hasNext() && result == null) {
            Profile profile = iterator.next();

            if (profile.getAccount().getEmail().equalsIgnoreCase(_email)) {
                result = profile;
            }
        }

        return result;
    }

    public boolean profileExists(final String _email) {
        Assert.isValidString(_email, "EMail is not valid");

        return find(_email) != null;
    }

    public Profile addProfile(final String _email, final String _password) {
        Assert.isValidString(_email, "EMail is not valid");
        Assert.isValidString(_password, "Password is not valid");

        Profile profile = new Profile(new Account(_email, _password));

        this.profiles.add(profile);

        return profile;
    }

    public boolean changeProfilePassword(final String _email, final String _password) {
        Assert.isValidString(_email, "EMail is not valid");
        Assert.isValidString(_password, "Password is not valid");

        Profile found = find(_email);

        if (found == null) {
            return false;
        }

        found.changePassword(_password);

        return true;
    }

    public void removeProfile(final String _email) {
        Assert.isValidString(_email, "EMail is not valid");

        Profile found = find(_email);

        if (found != null) {
            this.profiles.remove(found);
        }
    }
}
