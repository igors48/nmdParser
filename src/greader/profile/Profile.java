package greader.profile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static util.CollectionUtils.newArrayList;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 28.07.2011
 */
@XmlRootElement(name = "profile")
public class Profile {

    private Account account;
    private List<FeedConfiguration> feedConfigurations;

    public Profile(final Account _account) {
        setAccount(_account);
        this.feedConfigurations = newArrayList();
    }
    
    public Profile() {
        this(new Account());
    }

    @XmlElement(name = "account", nillable = false, required = true)
    public Account getAccount() {
        return this.account;
    }

    public void setAccount(final Account _account) {
        Assert.notNull(_account, "Account is null");
        this.account = _account;
    }

    @XmlElement(name = "feed", nillable = false, required = true)
    public List<FeedConfiguration> getFeedConfigurations() {
        return this.feedConfigurations;
    }

    public void setFeedConfigurations(final List<FeedConfiguration> _feedConfigurations) {
        Assert.notNull(_feedConfigurations, "Feed configurations is null");
        this.feedConfigurations = _feedConfigurations;
    }

    public void changePassword(final String _password) {
        Assert.isValidString(_password, "Password is not valid");

        this.account.setPassword(_password);
    }
}
