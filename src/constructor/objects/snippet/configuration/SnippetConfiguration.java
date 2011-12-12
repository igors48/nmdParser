package constructor.objects.snippet.configuration;

import app.cli.blitz.request.CriterionType;
import app.cli.blitz.request.RequestSourceType;
import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.storage.local.core.LocalStorage;
import util.Assert;
import util.sequense.SequenceGenerationParams;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ������������ ��������
 *
 * @author Igor Usenko
 *         Date: 09.12.2009
 */
public class SnippetConfiguration implements Blank {

    private String id;

    private RequestSourceType requestSourceType;
    private String base;
    private List<SequenceGenerationParams> urlGenerationPatterns;

    private CriterionType criterionType;
    private String criterionExpression;

    private String storage;
    private String branch;
    private Composition composition;
    private String outName;

    private String path;
    private String command;
    private String wait;

    private boolean linksAsFootnotes;
    private boolean removeExists;
    private boolean resolveImageLinks;

    private boolean forced;

    private List<String> genres;
    private String lang;

    private String from;
    private String to;
    private String step;
    private String mult;
    private String len;
    private String padd;

    private String coverUrl;

    private DocumentItemsSortMode documentItemsSortMode;

    public SnippetConfiguration() {
        this.linksAsFootnotes = true;
        this.removeExists = true;
        this.resolveImageLinks = true;

        this.composition = Composition.ONE_TO_ONE;
        this.storage = LocalStorage.DEFAULT_STORAGE_ID;
        this.branch = "";

        this.base = "";
        this.urlGenerationPatterns = newArrayList();

        this.genres = newArrayList();

        this.criterionType = CriterionType.REGEXP;
        this.criterionExpression = "";

        this.path = "";
        this.command = "";
        this.wait = "";

        this.lang = "";

        this.forced = false;

        this.documentItemsSortMode = DocumentItemsSortMode.DEFAULT;

        this.coverUrl = "";

        setSequenceGenerationDefaults();
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Id is not valid");

        this.id = _id;
    }

    public String getId() {
        return this.id;
    }

    public List<UsedObject> getUsedObjects() {
        List<UsedObject> result = newArrayList();

        if (!this.storage.isEmpty()) {
            result.add(new UsedObject(this.storage, ObjectType.STORAGE));
        }

        return result;
    }

    public boolean isLinksAsFootnotes() {
        return this.linksAsFootnotes;
    }

    public void setLinksAsFootnotes(final boolean _linksAsFootnotes) {
        this.linksAsFootnotes = _linksAsFootnotes;
    }

    public boolean isRemoveExists() {
        return this.removeExists;
    }

    public void setRemoveExists(final boolean _removeExists) {
        this.removeExists = _removeExists;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }

    public void setResolveImageLinks(final boolean _resolveImageLinks) {
        this.resolveImageLinks = _resolveImageLinks;
    }

    public boolean isForced() {
        return this.forced;
    }

    public void setForced(final boolean _forced) {
        this.forced = _forced;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(final String _path) {
        Assert.isValidString(_path, "Path is not valid");

        this.path = _path;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(final String _command) {
        Assert.isValidString(_command, "Command is not valid");

        this.command = _command;
    }

    public String getWait() {
        return this.wait;
    }

    public void setWait(final String _wait) {
        Assert.isValidString(_wait, "Wait value is not valid");

        this.wait = _wait;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(final String _branch) {
        Assert.isValidString(_branch, "Branch is not valid");

        this.branch = _branch;
    }

    public String getStorage() {
        return this.storage;
    }

    public void setStorage(final String _storage) {
        Assert.isValidString(_storage, "Storage is not valid");

        this.storage = _storage;
    }

    public Composition getComposition() {
        return this.composition;
    }

    public String getOutName() {
        return this.outName;
    }

    public void setManyToOne(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        this.composition = Composition.MANY_TO_ONE;
        this.outName = _name;
    }

    public void setRssUrl(final String _url) {
        Assert.isValidString(_url, "RSS url is not valid");

        this.requestSourceType = RequestSourceType.RSS;
        this.urlGenerationPatterns.clear();
        setSequenceGenerationDefaults();
        this.urlGenerationPatterns.add(getSequenceGenerationParams(_url));
    }

    public List<SequenceGenerationParams> getUrlGenerationPatterns() {
        return this.urlGenerationPatterns;
    }

    public RequestSourceType getRequestSourceType() {
        return this.requestSourceType;
    }

    public void addGenre(final String _genre) {
        Assert.isValidString(_genre, "Genre is not valid");

        this.genres.add(_genre);
    }

    public List<String> getGenres() {
        return this.genres;
    }

    public void setBase(final String _base) {
        Assert.isValidString(_base, "Base is not valid");

        this.base = _base;
    }

    public String getBase() {
        return this.base;
    }

    public void addUrl(final String _url) {
        Assert.isValidString(_url, "Url is not valid");

        this.requestSourceType = RequestSourceType.URLS;
        this.urlGenerationPatterns.add(getSequenceGenerationParams(_url));
        setSequenceGenerationDefaults();
    }

    public void setAutoContentFiltering() {
        this.criterionType = CriterionType.FILTER;
    }

    public void setXPath(final String _expression) {
        Assert.isValidString(_expression, "XPath expression is not valid");

        this.criterionType = CriterionType.XPATH;
        this.criterionExpression = _expression;
    }

    public void setRegExp(final String _expression) {
        Assert.isValidString(_expression, "RegExp expression is not valid");

        this.criterionType = CriterionType.REGEXP;
        this.criterionExpression = _expression;
    }

    public String getCriterionExpression() {
        return this.criterionExpression;
    }

    public CriterionType getCriterionType() {
        return this.criterionType;
    }

    public void setLang(String _lang) {
        Assert.isValidString(_lang, "Lang is not valid");

        this.lang = _lang;
    }

    public String getLang() {
        return this.lang;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String _from) {
        Assert.isValidString(_from, "From value is not valid");
        this.from = _from;
    }

    public String getMult() {
        return this.mult;
    }

    public void setMult(final String _mult) {
        Assert.isValidString(_mult, "Mult value is not valid");
        this.mult = _mult;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(final String _step) {
        Assert.isValidString(_step, "Step value is not valid");
        this.step = _step;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(final String _to) {
        Assert.isValidString(_to, "To value is not valid");
        this.to = _to;
    }

    public String getLen() {
        return this.len;
    }

    public void setLen(final String _len) {
        Assert.isValidString(_len, "Length value is not valid");
        this.len = _len;
    }

    public String getPadd() {
        return this.padd;
    }

    public void setPadd(final String _padd) {
        Assert.isValidString(_padd, "Padding value is not valid");
        this.padd = _padd;
    }

    public DocumentItemsSortMode getDocumentItemsSortMode() {
        return this.documentItemsSortMode;
    }

    public void setDocumentItemsSortMode(final DocumentItemsSortMode _documentItemsSortMode) {
        Assert.notNull(_documentItemsSortMode, "Document items sort mode is null");
        this.documentItemsSortMode = _documentItemsSortMode;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(final String _coverUrl) {
        Assert.notNull(_coverUrl, "Cover URL is null");
        this.coverUrl = _coverUrl;
    }

    private void setSequenceGenerationDefaults() {
        this.from = "";
        this.to = "";
        this.step = "1";
        this.mult = "1";
        this.len = "";
        this.padd = "";
    }

    private SequenceGenerationParams getSequenceGenerationParams(final String _url) {
        return new SequenceGenerationParams(_url,
                parse(this.from),
                parse(this.to),
                parse(this.step),
                parse(this.mult),
                parse(this.len),
                this.padd);
    }

    private int parse(final String _value) {
        int result = 0;

        try {
            result = Integer.valueOf(_value);
        } catch (Exception e) {
            // empty
        }

        return result;
    }
}
