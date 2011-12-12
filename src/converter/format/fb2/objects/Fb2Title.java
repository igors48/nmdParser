package converter.format.fb2.objects;

import converter.format.fb2.Stringable;
import converter.format.fb2.text.Fb2EmptyLine;
import util.Assert;

import java.util.Arrays;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 07.09.2008
 */
public class Fb2Title implements Stringable {

    private static final String OPEN_TAG = "<title>";

    private static final String CLOSE_TAG = "</title>";

    private final List<Stringable> content;

    public Fb2Title() {
        this.content = newArrayList();
    }

    public String[] getStrings() {
        List<String> result = newArrayList();
        StringBuffer buffer = new StringBuffer();

        result.add(OPEN_TAG);
        result.addAll(Arrays.asList(getContentStrings()));
        result.add(CLOSE_TAG);

        return result.toArray(new String[result.size()]);
    }

    public void insertParagraph(Fb2Paragraph _paragraph) {
        Assert.notNull(_paragraph);

        this.content.add(_paragraph);
    }

    public void insertEmptyLine() {
        this.content.add(new Fb2EmptyLine());
    }

    private String[] getContentStrings() {
        List<String> result = newArrayList();

        for (Stringable current : this.content) {
            String[] image = current.getStrings();
            result.addAll(Arrays.asList(image));
        }

        return result.toArray(new String[result.size()]);
    }

}
