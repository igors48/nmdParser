package html.parser.tag;

import util.Assert;
import util.html.HtmlTagTools;

import java.util.HashMap;
import java.util.Map;

/**
 * Парсер HTML тега. На входе код - на выходе имя тега и список аттрибутов
 *
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public class HtmlTag {

    private String id;
    private Map<String, String> attributes;
    private HtmlTagStatus status;

    public HtmlTag(String _data, String[] _tags) {
        Assert.isValidString(_data);
        Assert.notNull(_tags);

        this.attributes = new HashMap<String, String>();

        decode(_data, _tags);
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public String getAttribute(String _attribute) {
        Assert.isValidString(_attribute);

        String result = this.attributes.get(_attribute);

        return result == null ? this.attributes.get(_attribute.toUpperCase()) : result;
    }

    public String getId() {
        return this.id;
    }

    public HtmlTagStatus getStatus() {
        return this.status;
    }

    private void decode(String _data, String[] _tags) {
        this.id = resolveId(_data, _tags);

        if (this.status == HtmlTagStatus.NORMAL) {
            String remain = _data.substring(this.id.length());

            if (remain.length() != 0) {
                resolveAttributes(remain);
            }
        }
    }

    private String resolveId(String _data, String[] _tags) {
        String tag = (_data.split(" "))[0];
        int index = findTag(tag, _tags);

        if (index == -1) {
            this.status = HtmlTagStatus.UNKNOWN;
            return tag;
        }

        this.status = HtmlTagStatus.NORMAL;
        return _tags[index];
    }

    private void resolveAttributes(String _data) {
        this.attributes = HtmlTagTools.parseAttributes(_data);
    }

    private int findTag(String _tag, String[] _tags) {
        String upperTag = _tag.toUpperCase();

        for (int index = _tags.length - 1; index >= 0; --index) {

            if (upperTag.startsWith(_tags[index])) {
                return index;
            }
        }
        return -1;
    }
}
