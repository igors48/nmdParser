package flowtext.resource;

import flowtext.FlowTextObject;
import flowtext.FlowTextType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 06.09.2008
 */
public class Resource implements FlowTextObject {

    private final String address;
    private final String base;

    public Resource(String _base, String _address) {
        Assert.isValidString(_base);
        Assert.isValidString(_address);

        this.address = _address;
        this.base = _base;
    }

    public FlowTextType getType() {
        return FlowTextType.RESOURCE;
    }

    public String getAddress() {
        return this.address;
    }

    public String getBase() {
        return this.base;
    }
}
