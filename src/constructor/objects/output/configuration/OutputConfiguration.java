package constructor.objects.output.configuration;

import app.workingarea.ProcessWrapper;
import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.storage.local.core.LocalStorage;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурация формирователя выходного документа
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public class OutputConfiguration implements Blank {

    private static final String NO_TOKEN = "no";
    private static final String ALWAYS_TOKEN = "ALWAYS";
    private static final String OFF_TOKEN = "OFF";
    private static final String AUTO_TOKEN = "AUTO";

    private String id;

    private String format;
    private String storage;
    private String branch;
    private String channel;

    private Composition composition;
    private String name;

    private boolean linksAsFootnotes;
    private boolean removeExists;
    private boolean resolveImageLinks;
    private DateSectionMode dateSectionMode;

    private String forEachPath;
    private String forEachCommand;
    private String forEachWait;

    private DocumentItemsSortMode fromNewToOld;

    public OutputConfiguration() {
        this.linksAsFootnotes = /*true*/false;
        this.removeExists = true;
        this.resolveImageLinks = true;
        this.dateSectionMode = DateSectionMode.AUTO;

        this.forEachPath = "";
        this.forEachCommand = "";
        this.forEachWait = "";

        this.branch = "";

        this.storage = LocalStorage.DEFAULT_STORAGE_ID;

        this.fromNewToOld = DocumentItemsSortMode.DEFAULT;
    }

    public void setFormat(final String _value) {
        Assert.isValidString(_value, "Format name is not valid.");
        this.format = _value;
    }

    public void setStorage(final String _value) {
        Assert.isValidString(_value, "Storage name is not valid.");
        this.storage = _value;
    }

    public void setBranch(final String _value) {
        Assert.isValidString(_value, "Branch name is not valid.");
        this.branch = _value;
    }

    public void setChannel(final String _value) {
        Assert.isValidString(_value, "Channel name is not valid.");
        this.channel = _value;
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Output id is not valid.");
        this.id = _id;
    }

    public List<UsedObject> getUsedObjects() {
        List<UsedObject> result = new ArrayList<UsedObject>();

        result.add(new UsedObject(this.channel, ObjectType.CHANNEL));

        return result;
    }

    public void setComposition(final Composition _composition) {
        Assert.notNull(_composition, "Document compostion is null.");
        this.composition = _composition;
    }

    public void setName(final String _name) {
        Assert.isValidString(_name, "Document name is not valid.");
        this.name = _name;
    }

    public void setLinksAsFootnotes(final String _mode) {
        Assert.isValidString(_mode, "Links mode name is invalid");

        this.linksAsFootnotes = !NO_TOKEN.equalsIgnoreCase(_mode);
    }

    public void setResolveImageLinks(String _mode) {
        Assert.isValidString(_mode, "Resolve image links mode name is invalid");

        this.resolveImageLinks = !NO_TOKEN.equalsIgnoreCase(_mode);
    }

    public void setRemoveExists(final String _mode) {
        Assert.isValidString(_mode, "Remove exists mode name is invalid");

        this.removeExists = !NO_TOKEN.equalsIgnoreCase(_mode);
    }

    public String getName() {
        return this.name;
    }

    public Composition getComposition() {
        return this.composition == null ? Composition.ONE_TO_ONE : this.composition;
    }

    public String getId() {
        return this.id;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getChannelId() {
        return this.channel;
    }

    public String getFormat() {
        return this.format;
    }

    public String getStorageId() {
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

    public void setDateSectionMode(final String _value) {
        Assert.isValidString(_value, "Date section mode name is not valid");

        if (ALWAYS_TOKEN.equalsIgnoreCase(_value)) {
            this.dateSectionMode = DateSectionMode.ALWAYS;
        }

        if (OFF_TOKEN.equalsIgnoreCase(_value)) {
            this.dateSectionMode = DateSectionMode.OFF;
        }

        if (AUTO_TOKEN.equalsIgnoreCase(_value)) {
            this.dateSectionMode = DateSectionMode.AUTO;
        }
    }

    public DateSectionMode getDateSectionMode() {
        return this.dateSectionMode;
    }

    public void setForEachCommand(final String _forEachCommand) {
        Assert.isValidString(_forEachCommand, "For each command is not valid");
        this.forEachCommand = _forEachCommand;
    }

    public void setForEachPath(final String _forEachPath) {
        Assert.isValidString(_forEachPath, "For each command path is not valid");
        this.forEachPath = _forEachPath;
    }

    public void setForEachWait(final String _forEachWait) {
        Assert.isValidString(_forEachWait, "For each command wait time is not valid");
        this.forEachWait = _forEachWait;
    }

    public String getForEachCommand() {
        return this.forEachCommand;
    }

    public String getForEachPath() {
        return this.forEachPath;
    }

    public long getForEachWait() {
        long result = ProcessWrapper.WAIT_FOREVER;

        try {
            result = Long.valueOf(this.forEachWait);
        } catch (Throwable t) {
            // empty
        }

        return result;
    }

    public void setFromNewToOld(final String _fromNewToOld) {
        Assert.isValidString(_fromNewToOld, "From new to old value is invalid");
        this.fromNewToOld = _fromNewToOld.equalsIgnoreCase(NO_TOKEN) ? DocumentItemsSortMode.FROM_OLD_TO_NEW : DocumentItemsSortMode.FROM_NEW_TO_OLD;
    }

    public DocumentItemsSortMode getFromNewToOld() {
        return this.fromNewToOld;
    }
}
