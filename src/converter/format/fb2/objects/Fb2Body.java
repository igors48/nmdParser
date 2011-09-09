package converter.format.fb2.objects;

import converter.format.fb2.Stringable;
import util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2Body implements Stringable {

    private static final String TEMPLATE_START = "<body>";

    private static final String TEMPLATE_END = "</body>";

    private final List<Fb2Section> sections;

    public Fb2Body() {
        this.sections = new ArrayList<Fb2Section>();
    }

    public void insertSection(Fb2Section _section) {
        Assert.notNull(_section);

        this.sections.add(_section);
    }

    public String[] getStrings() {
        List<String> result = new ArrayList<String>();

        result.add(TEMPLATE_START);

        for (Fb2Section current : this.sections) {
            String[] image = current.getStrings();
            result.addAll(Arrays.asList(image));
        }

        result.add(TEMPLATE_END);

        return result.toArray(new String[result.size()]);
    }
}
