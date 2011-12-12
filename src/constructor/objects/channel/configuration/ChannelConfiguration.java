package constructor.objects.channel.configuration;

import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.channel.core.ChannelDataTools;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Конфигурация канала
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public class ChannelConfiguration implements Blank {

    private final List<String> genres;

    private String id;
    private String sourceId;
    private String interpreterId;
    private String processorId;
    private int lastItemCount;
    private boolean forced;
    private String lang;
    private String cover;
    private long pauseBetweenRequests;

    private static final String YES_TOKEN = "yes";

    public ChannelConfiguration() {
        this.genres = newArrayList();
        this.lang = ChannelDataTools.DEFAULT_LANG;
        this.cover = "";
        this.pauseBetweenRequests = 0;
    }

    public int getLastItemCount() {
        return this.lastItemCount;
    }

    public String getProcessorId() {
        return this.processorId;
    }

    public void setProcessorId(final String _processorId) {
        Assert.isValidString(_processorId, "Processor id is not valid.");
        this.processorId = _processorId;
    }

    public void setLastItemCount(final String _lastItemCount) {
        Assert.isValidString(_lastItemCount, "Last item count is not valid");
        this.lastItemCount = Integer.valueOf(_lastItemCount);
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Channel id is not valid.");
        this.id = _id;
    }

    public List<UsedObject> getUsedObjects() {
        List<UsedObject> result = newArrayList();

        result.add(new UsedObject(this.sourceId, ObjectType.SOURCE));

        if (this.interpreterId != null) {
            result.add(new UsedObject(this.interpreterId, ObjectType.INTERPRETER));
        }

        if (this.processorId != null) {
            result.add(new UsedObject(this.processorId, ObjectType.PROCESSOR));
        }

        return result;
    }

    public void setSourceId(final String _value) {
        Assert.isValidString(_value, "Source id is not valid.");
        this.sourceId = _value;
    }

    public void setInterpreterId(final String _interpreterId) {
        Assert.isValidString(_interpreterId, "Interpreter id is not valid.");
        this.interpreterId = _interpreterId;
    }

    public String getInterpreterId() {
        return this.interpreterId;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public String getId() {
        return this.id;
    }

    public void setForced(final String _mode) {
        Assert.isValidString(_mode, "Update mode is not valid.");
        this.forced = YES_TOKEN.equalsIgnoreCase(_mode);
    }

    public boolean isForced() {
        return this.forced;
    }

    public void setLang(final String _value) {
        Assert.isValidString(_value, "Language value is not valid.");
        this.lang = _value;
    }

    public String getLang() {
        return this.lang;
    }

    public void addGenre(final String _value) {
        Assert.isValidString(_value, "Language value is not valid.");
        this.genres.add(_value);
    }

    public List<String> getGenres() {

        if (this.genres.isEmpty()) {
            this.genres.add(ChannelDataTools.DEFAULT_GENRE);
        }

        return this.genres;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(final String _cover) {
        Assert.isValidString(_cover, "Cover URL is not valid");
        this.cover = _cover;
    }

    public long getPauseBetweenRequests() {
        return this.pauseBetweenRequests;
    }

    public void setPauseBetweenRequests(final long _pauseBetweenRequests) {
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");
        this.pauseBetweenRequests = _pauseBetweenRequests;
    }
}
