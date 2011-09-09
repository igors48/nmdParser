package app.metadata;

import app.templater.PlaceHolderInfo;
import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Контейнер для данных о найденном объекте
 *
 * @author Igor Usenko
 *         Date: 08.03.2010
 */
public class ObjectMetaData {
    private final String id;
    private final ObjectType type;

    private final Blank blank;

    private List<PlaceHolderInfo> placeholders;
    private List<UsedObject> dependencies;

    private String sourceFile;

    public ObjectMetaData(final String _id, final ObjectType _type, final Blank _blank) {
        Assert.isValidString(_id, "Object id is invalid");
        this.id = _id;

        Assert.notNull(_type, "Object type is null");
        this.type = _type;

        Assert.notNull(_blank, "Blank is null");
        this.blank = _blank;

        this.placeholders = new ArrayList<PlaceHolderInfo>();
        this.dependencies = new ArrayList<UsedObject>();

        this.sourceFile = "";
    }

    public String getId() {
        return this.id;
    }

    public ObjectType getType() {
        return this.type;
    }

    public List<UsedObject> getDependencies() {
        return this.dependencies;
    }

    public void addDependency(final UsedObject _dependency) {
        Assert.notNull(_dependency, "Dependency is null");
        this.dependencies.add(_dependency);
    }

    public List<PlaceHolderInfo> getPlaceholders() {
        return this.placeholders;
    }

    public void setPlaceholders(final List<PlaceHolderInfo> _placeholders) {
        Assert.notNull(_placeholders, "Placeholders list is null");
        this.placeholders = _placeholders;
    }

    public String getSourceFile() {
        return this.sourceFile;
    }

    public void setSourceFile(final String _sourceFile) {
        Assert.notNull(_sourceFile, "Source file is null");
        this.sourceFile = _sourceFile;
    }

    public Blank getBlank() {
        return this.blank;
    }
}
