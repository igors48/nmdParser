package flowtext;

import util.Assert;

/**
 * Text flow document
 *
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Document {
    private final Header header;
    private final Body body;

    public Document(final Header _header, final Body _body) {
        Assert.notNull(_body, "Body is null");
        Assert.notNull(_header, "Header is null");

        this.body = _body;
        this.header = _header;
    }

    public Body getBody() {
        return this.body;
    }

    public Header getHeader() {
        return this.header;
    }

}
