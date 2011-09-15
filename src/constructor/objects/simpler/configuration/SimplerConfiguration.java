package constructor.objects.simpler.configuration;

import constructor.dom.Blank;
import constructor.dom.UsedObject;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.storage.local.core.LocalStorage;
import util.Assert;
import util.TextTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 06.07.2010
 */
public class SimplerConfiguration implements Blank {

    public static final String YES_TOKEN = "yes";
    public static final String NO_TOKEN = "no";

    private String id;

    private String feedUrl;
    private String storeDays;
    private String criterions;
    private boolean autoContentFiltering;
    private String branch;
    private String outName;
    private DocumentItemsSortMode fromNewToOld;
    private String coverUrl;
    private long pauseBetweenRequests;

    public SimplerConfiguration() {
        this.id = "";
        this.feedUrl = "";
        this.storeDays = "7";
        this.criterions = "";
        this.autoContentFiltering = false;
        this.branch = "";
        this.outName = "";
        this.fromNewToOld = DocumentItemsSortMode.DEFAULT;
        this.coverUrl = "";
        this.pauseBetweenRequests = 0;
    }

    public void setElementXPath(final String _criterions) {
        Assert.isValidString(_criterions, "XPath criterions is not valid");
        this.criterions = _criterions;
    }

    public void setAttributeStoreDays(final String _storeDays) {
        Assert.isValidString(_storeDays, "Store days value is invalid");
        this.storeDays = _storeDays;
    }

    public void setAttributeFeedUrl(final String _feedUrl) {
        Assert.isValidString(_feedUrl, "Feed Url is invalid");
        this.feedUrl = _feedUrl;
    }

    public void setAttributeBranch(final String _branch) {
        Assert.notNull(_branch, "Branch is null");
        this.branch = _branch;
    }

    public void setAttributeOutName(final String _outName) {
        Assert.isValidString(_outName, "Output name is invalid");
        this.outName = _outName;
    }

    public void setAttributeFromNewToOld(final String _fromNewToOld) {
        Assert.isValidString(_fromNewToOld, "From new to old value is invalid");
        this.fromNewToOld = _fromNewToOld.equalsIgnoreCase(NO_TOKEN) ? DocumentItemsSortMode.FROM_OLD_TO_NEW : DocumentItemsSortMode.FROM_NEW_TO_OLD;
    }

    public long getPauseBetweenRequests() {
        return this.pauseBetweenRequests;
    }

    public void setAttributePauseBetweenRequests(final String _pauseBetweenRequests) {
        Assert.isValidString(_pauseBetweenRequests, "Pause between requests attribute is invalid");
        this.pauseBetweenRequests = TextTools.parseLong(_pauseBetweenRequests);
    }

    public String getFeedUrl() {
        return this.feedUrl;
    }

    public String getCriterions() {
        return this.criterions;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getOutName() {
        return this.outName;
    }

    public DocumentItemsSortMode getFromNewToOld() {
        return this.fromNewToOld;
    }

    public String getStoreDays() {
        return this.storeDays;
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Id is not valid");
        this.id = _id;
    }

    public List<UsedObject> getUsedObjects() {
        return new ArrayList<UsedObject>();
    }

    public String getId() {
        return this.id;
    }

    public void setAttributeCoverUrl(final String _coverUrl) {
        Assert.notNull(_coverUrl, "Cover URL is null");
        this.coverUrl = _coverUrl;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public String getStorageId() {
        return LocalStorage.DEFAULT_STORAGE_ID;
    }

    public void setAutoContentFiltering() {
        this.autoContentFiltering = true;
    }

    public boolean isAutoContentFiltering() {
        return this.autoContentFiltering;
    }

    public static SimplerConfiguration getDefault() {
        return new SimplerConfiguration();
    }

}
