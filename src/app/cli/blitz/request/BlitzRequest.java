package app.cli.blitz.request;

import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.storage.local.core.LocalStorage;
import dated.item.modification.Modification;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * ������ ����-�������
 *
 * @author Igor Usenko
 *         Date: 28.10.2009
 */
public class BlitzRequest {

    private final RequestSourceType sourceType; // mandatory
    private final List<String> addresses; // mandatory
    private final List<Modification> modifications; // mandatory

    private CriterionType criterionType;
    private String criterionExpression;

    private String outName;

    private Composition composition;
    private String branch;
    private String storage;

    private boolean linksAsFootnotes;
    private boolean removeExists;
    private boolean resolveImageLinks;

    private static final String BODY_EXTRACTION_REGEXP = "<body(.+?)</body>";
    private static final String DEFAULT_OUT_NAME = "";

    private String forEachPath;
    private String forEachCommand;
    private long forEachWait;

    private boolean forced;

    private List<String> genres;

    private long maxAge;

    private DocumentItemsSortMode fromNewToOld;

    private String coverUrl;

    /**
     * ����� ��� ���������� ��������� ���������� timestamp ����� ���������(�� �������)
     * �� ������������ ATDC ���������. ����� ��������� -1 ���� � ���� ���������
     * ������� ������������ ��������� �� ����
     */
    private long latestItemTime;

    private String lang;

    public BlitzRequest(final List<Modification> _modifications) {
        this(RequestSourceType.MODIFICATIONS, new ArrayList<String>(), _modifications);
    }

    public BlitzRequest(final RequestSourceType _sourceType, final List<String> _addresses) {
        this(_sourceType, _addresses, new ArrayList<Modification>());
    }

    private BlitzRequest(final RequestSourceType _sourceType, final List<String> _addresses, final List<Modification> _modifications) {
        Assert.notNull(_sourceType, "Source type is null");
        this.sourceType = _sourceType;

        Assert.notNull(_addresses, "Addresses list is null");
        this.addresses = _addresses;

        Assert.notNull(_modifications, "Modifications list is null");
        this.modifications = _modifications;

        this.criterionType = CriterionType.REGEXP;
        this.criterionExpression = BODY_EXTRACTION_REGEXP;

        this.outName = DEFAULT_OUT_NAME;
        this.composition = Composition.ONE_TO_ONE;

        this.branch = "";
        this.storage = LocalStorage.DEFAULT_STORAGE_ID;

        this.linksAsFootnotes = true;
        this.removeExists = false;
        this.resolveImageLinks = true;

        this.forced = true;

        this.genres = new ArrayList<String>();
        this.genres.add(ChannelDataTools.DEFAULT_GENRE);

        this.lang = ChannelDataTools.DEFAULT_LANG;

        this.forEachPath = "";
        this.forEachCommand = "";
        this.forEachWait = 0;

        this.latestItemTime = -1;

        this.fromNewToOld = DocumentItemsSortMode.DEFAULT;

        this.coverUrl = "";
    }

    public void setBranch(final String _branch) {
        Assert.notNull(_branch, "Branch name is invalid");
        this.branch = _branch;
    }

    public void setComposition(final Composition _composition) {
        Assert.notNull(_composition, "Composition is null");
        this.composition = _composition;
    }

    public void setCriterionExpression(final String _criterionExpression) {
        Assert.isValidString(_criterionExpression, "Criterion expression is not valid");
        this.criterionExpression = _criterionExpression;
    }

    public void setCriterionType(final CriterionType _criterionType) {
        Assert.notNull(_criterionType, "Criterion type is null");
        this.criterionType = _criterionType;
    }

    public void setLinksAsFootnotes(final boolean linksAsFootnotes) {
        this.linksAsFootnotes = linksAsFootnotes;
    }

    public void setOutName(final String _outName) {
        Assert.isValidString(_outName, "Output name is not valid");
        this.outName = _outName;
    }

    public void setRemoveExists(final boolean _removeExists) {
        this.removeExists = _removeExists;
    }

    public void setResolveImageLinks(final boolean _resolveImageLinks) {
        this.resolveImageLinks = _resolveImageLinks;
    }

    public void setStorage(final String _storage) {
        Assert.isValidString(_storage, "Storage name is not valid");
        this.storage = _storage;
    }

    public List<String> getAddresses() {
        return this.addresses;
    }

    public List<Modification> getModifications() {
        return this.modifications;
    }

    public String getCriterionExpression() {
        return this.criterionExpression;
    }

    public CriterionType getCriterionType() {
        return this.criterionType;
    }

    public String getOutName() {
        return this.outName;
    }

    public RequestSourceType getSourceType() {
        return this.sourceType;
    }

    public String getBranch() {
        return this.branch;
    }

    public Composition getComposition() {
        return this.composition;
    }

    public String getStorage() {
        return this.storage;
    }

    public boolean isLinksAsFootnotes() {
        return this.linksAsFootnotes;
    }

    public boolean isRemoveExists() {
        return this.removeExists;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }

    public boolean isForced() {
        return this.forced;
    }

    public void setForced(final boolean _forced) {
        this.forced = _forced;
    }

    public List<String> getGenres() {
        return this.genres;
    }

    public void setGenres(final List<String> _genres) {
        Assert.notNull(_genres, "Genres list is null");
        this.genres = _genres;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(final String _lang) {
        Assert.notNull(_lang, "Lang value is not valid");
        this.lang = _lang;
    }

    public String getForEachCommand() {
        return this.forEachCommand;
    }

    public String getForEachPath() {
        return this.forEachPath;
    }

    public long getForEachWait() {
        return this.forEachWait;
    }

    public void setForEachCommand(final String _forEachCommand) {
        Assert.notNull(_forEachCommand, "For each command is null");
        this.forEachCommand = _forEachCommand;
    }

    public void setForEachPath(final String _forEachPath) {
        Assert.notNull(_forEachPath, "For each path is null");
        this.forEachPath = _forEachPath;
    }

    public void setForEachWait(final long _forEachWait) {
        Assert.greaterOrEqual(_forEachWait, -1, "For each wait < -1");
        this.forEachWait = _forEachWait;
    }

    public boolean expressionRemainsDefault() {
        return BODY_EXTRACTION_REGEXP.equals(this.criterionExpression);
    }

    public long getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(final long _maxAge) {
        Assert.greaterOrEqual(_maxAge, 0, "Max age < 0");
        this.maxAge = _maxAge;
    }

    public long getLatestItemTime() {
        return this.latestItemTime;
    }

    public void setLatestItemTime(final long _latestItemTime) {
        Assert.greaterOrEqual(_latestItemTime, 0, "Latest item time < 0");
        this.latestItemTime = _latestItemTime;
    }

    public DocumentItemsSortMode isFromNewToOld() {
        return this.fromNewToOld;
    }

    public void setFromNewToOld(final DocumentItemsSortMode _fromNewToOld) {
        Assert.notNull(_fromNewToOld, "DocumentItemsSortMode is null");
        this.fromNewToOld = _fromNewToOld;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(final String _coverUrl) {
        Assert.notNull(_coverUrl, "Cover URL is null");
        this.coverUrl = _coverUrl;
    }
}
