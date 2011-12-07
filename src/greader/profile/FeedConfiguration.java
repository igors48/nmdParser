package greader.profile;

import util.Assert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 28.07.2011
 */
@XmlRootElement(name = "feed")
public class FeedConfiguration {

    private boolean active;
    private String url;
    private String coverUrl;
    private boolean autoContentFiltering;
    private String criterions;
    private String branch;
    private String name;
    private boolean rewrite;

    public FeedConfiguration() {
        setActive(true);
        setUrl("");
        setCoverUrl("");
        setAutoContentFiltering(false);
        setCriterions("");
        setBranch("");
        setName("");
        setRewrite(true);
    }

    @XmlElement(name = "active", nillable = false, required = false)
    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @XmlElement(name = "url", nillable = false, required = true)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String _url) {
        Assert.notNull(_url, "Url is null");
        this.url = _url;
    }

    @XmlElement(name = "cover", nillable = false, required = true)
    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(final String _coverUrl) {
        Assert.notNull(_coverUrl, "Cover url is null");
        this.coverUrl = _coverUrl;
    }

    @XmlElement(name = "content-filter", nillable = false, required = false)
    public boolean isAutoContentFiltering() {
        return this.autoContentFiltering;
    }

    public void setAutoContentFiltering(final boolean _autoContentFiltering) {
        this.autoContentFiltering = _autoContentFiltering;
    }

    @XmlElement(name = "criterions", nillable = false, required = true)
    public String getCriterions() {
        return this.criterions;
    }

    public void setCriterions(final String _criterions) {
        Assert.notNull(_criterions, "Criterions is null");
        this.criterions = _criterions;
    }

    @XmlElement(name = "branch", nillable = false, required = true)
    public String getBranch() {
        return this.branch;
    }

    public void setBranch(final String _branch) {
        Assert.notNull(_branch, "Branch is null");
        this.branch = _branch;
    }

    @XmlElement(name = "name", nillable = false, required = true)
    public String getName() {
        return this.name;
    }

    public void setName(final String _name) {
        Assert.notNull(_name, "Name is null");
        this.name = _name;
    }

    @XmlElement(name = "rewrite", nillable = false, required = true)
    public boolean isRewrite() {
        return this.rewrite;
    }

    public void setRewrite(final boolean _rewrite) {
        this.rewrite = _rewrite;
    }

    public static FeedConfiguration createForUrlAndName(final String _url, final String _name, final String _branch) {
        Assert.isValidString(_url, "Url is not valid");
        Assert.isValidString(_name, "Name is not valid");
        Assert.notNull(_branch, "Branch is null");

        FeedConfiguration result = new FeedConfiguration();

        result.setUrl(_url);
        result.setName(_name);
        result.setBranch(_branch);

        return result;
    }
}
