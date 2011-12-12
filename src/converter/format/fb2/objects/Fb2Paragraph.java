package converter.format.fb2.objects;

import converter.format.fb2.Stringable;
import converter.format.fb2.footnotes.Fb2FootNoteLink;
import converter.format.fb2.resource.Fb2ResourceLink;
import converter.format.fb2.text.Fb2CodeText;
import converter.format.fb2.text.Fb2EmphasisText;
import converter.format.fb2.text.Fb2SimpleText;
import converter.format.fb2.text.Fb2StrongText;
import util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2Paragraph implements Stringable {

    private static final String OPEN_TAG = "<p>";
    private static final String CLOSE_TAG = "</p>";

    private final List<Stringable> content;

    public Fb2Paragraph() {
        this.content = newArrayList();
    }

    public void insertSimpleText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new Fb2SimpleText(_text));
    }

    public void insertStrongText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new Fb2StrongText(_text));
    }

    public void insertEmphasisText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new Fb2EmphasisText(_text));
    }

    public void insertCodeText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new Fb2CodeText(_text));
    }

    public void insertResource(String _tag) {
        Assert.isValidString(_tag);

        this.content.add(new Fb2ResourceLink(_tag));
    }

    public void insertFootNoteLink(String _tag) {
        Assert.isValidString(_tag);

        this.content.add(new Fb2FootNoteLink(_tag));
    }

    private String[] getContentStrings() {
        List<String> result = newArrayList();

        for (Stringable current : this.content) {
            result.addAll(Arrays.asList(current.getStrings()));
        }

        return result.toArray(new String[result.size()]);
    }

    public String[] getStrings() {
        List<String> result = newArrayList();

        result.add(OPEN_TAG);
        result.addAll(Arrays.asList(getContentStrings()));
        result.add(CLOSE_TAG);

        return result.toArray(new String[result.size()]);
    }
}
