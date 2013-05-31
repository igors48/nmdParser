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

    public Resource(final String _base, final String _address) {
        Assert.isValidString(_base, "Base is not valid");
        Assert.isValidString(_address, "Address is not valid");

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
