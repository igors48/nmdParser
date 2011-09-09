package app.iui.entity;

import app.metadata.ObjectMetaData;
import constructor.dom.Blank;
import constructor.dom.ObjectType;
import util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Контейнер с информацией о сущности
 *
 * @author Igor Usenko
 *         Date: 17.04.2010
 */
public class Entity {
    private String name;

    private ObjectMetaData source;
    private ObjectMetaData channel;
    private ObjectMetaData output;

    private List<String> sourceFiles;
    private Map<String, String> placeHolders;

    private final boolean simpler;

    public Entity(final String _name, final ObjectMetaData _simpler, final String _sourceFile, final Map<String, String> _placeHolders) {
        setName(_name);

        this.source = _simpler;
        this.channel = _simpler;
        this.output = _simpler;

        Assert.isValidString(_sourceFile, "Source file is not valid");
        setSourceFiles(Arrays.asList(_sourceFile));

        setPlaceHolders(_placeHolders);

        this.simpler = true;
    }

    public Entity(final String _name, final ObjectMetaData _source, final ObjectMetaData _channel, final ObjectMetaData _output, final List<String> _sourceFiles, final Map<String, String> _placeHolders) {
        setName(_name);

        setSource(_source);
        setChannel(_channel);
        setOutput(_output);

        setSourceFiles(_sourceFiles);
        setPlaceHolders(_placeHolders);

        this.simpler = false;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getSourceFiles() {
        return this.sourceFiles;
    }

    private void setSourceFiles(final List<String> _sourceFiles) {
        Assert.notNull(_sourceFiles, "Source files list is null");
        this.sourceFiles = _sourceFiles;
    }

    public String getSourceId() {
        return this.source.getId();
    }

    public String getChannelId() {
        return this.channel.getId();
    }

    public String getOutputId() {
        return this.output.getId();
    }

    public Map<String, String> getPlaceHolders() {
        return this.placeHolders;
    }

    public boolean isPlaceholdersUsed() {
        return !this.placeHolders.isEmpty();
    }

    public boolean isSimpler() {
        return this.simpler;
    }

    public Blank getBlank() {
        return this.output.getBlank();
    }

    private void setName(final String _name) {
        Assert.isValidString(_name, "Entity name is not valid");
        this.name = _name;
    }

    private void setChannel(final ObjectMetaData _channel) {
        Assert.notNull(_channel, "Channel meta data is null");
        Assert.isTrue(_channel.getType() == ObjectType.CHANNEL, "Item type is not type of CHANNEL");

        this.channel = _channel;
    }

    private void setOutput(final ObjectMetaData _output) {
        Assert.notNull(_output, "Output meta data is null");
        Assert.isTrue(_output.getType() == ObjectType.OUTPUT, "Item type is not type of OUTPUT");

        this.output = _output;
    }

    private void setSource(final ObjectMetaData _source) {
        Assert.notNull(_source, "Source meta data is null");
        Assert.isTrue(_source.getType() == ObjectType.SOURCE, "Item type is not type of SOURCE");

        this.source = _source;
    }

    private void setPlaceHolders(final Map<String, String> _placeHolders) {
        Assert.notNull(_placeHolders, "Placeholders map is null");
        this.placeHolders = _placeHolders;
    }

}
