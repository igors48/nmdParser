package greader.profile;

import util.Assert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 28.07.2011
 */
public class Account {

    private String email;
    private String password;

    public Account(final String _email, final String _password) {
        setEmail(_email);
        setPassword(_password);
    }

    public Account() {
        this("", "");
    }

    @XmlElement(name = "email", nillable = false, required = true)
    public String getEmail() {
        return this.email;
    }

    public final void setEmail(final String _email) {
        Assert.notNull(_email, "Email is null");
        this.email = _email;
    }

    @XmlElement(name = "password", nillable = false, required = true)
    @XmlJavaTypeAdapter(value = PasswordEncoder.class)
    public String getPassword() {
        return this.password;
    }

    public final void setPassword(final String _password) {
        Assert.notNull(_password, "Password is null");
        this.password = _password;
    }
}
