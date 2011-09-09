package util.html;

import util.Assert;

/**
 * Образ тега, его аттрибутов и положение в документе
 *
 * @author Igor Usenko
 *         Date: 12.06.2009
 */
public class TagImage {
    private final int position;
    private final String tag;
    private final String attributes;
    private final String image;
    private final boolean open;

    public TagImage(final int _position, final String _tag, final String _attributes, final String _image, final boolean _open) {
        Assert.greaterOrEqual(_position, 0, "Position < 0.");
        Assert.isValidString(_tag, "Tag is invalid.");
        Assert.notNull(_attributes, "Attributes is null.");
        Assert.isValidString(_image, "Image is invalid.");

        this.position = _position;
        this.tag = _tag;
        this.attributes = _attributes;
        this.image = _image;
        this.open = _open;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public int getPosition() {
        return this.position;
    }

    public String getTag() {
        return this.tag;
    }

    public String getImage() {
        return this.image;
    }

    public boolean isOpen() {
        return this.open;
    }
}
