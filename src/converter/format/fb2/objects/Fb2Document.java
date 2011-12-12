package converter.format.fb2.objects;

import converter.format.fb2.Stringable;
import converter.format.fb2.footnotes.Fb2FootNoteList;
import converter.format.fb2.resource.Fb2ResourceBundle;
import util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2Document implements Stringable {

    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String TEMPLATE_START = "<FictionBook xmlns=\"http://www.gribuser.ru/xml/fictionbook/2.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:l=\"http://www.w3.org/1999/xlink\">";
    private static final String TEMPLATE_END = "</FictionBook>";

    private final Fb2Header header;
    private final Fb2Body body;
    private final Fb2FootNoteList notes;
    private final Fb2ResourceBundle resources;

    public Fb2Document(Fb2Header _header, Fb2Body _body, Fb2FootNoteList _notes, Fb2ResourceBundle _resources) {
        Assert.notNull(_header);
        Assert.notNull(_body);
        Assert.notNull(_notes);
        Assert.notNull(_resources);

        this.body = _body;
        this.notes = _notes;
        this.header = _header;
        this.resources = _resources;
    }

    public String[] getStrings() {
        List<String> result = newArrayList();

        result.add(XML_HEADER);
        result.add(TEMPLATE_START);

        String[] headerImage = this.header.getStrings();
        result.addAll(Arrays.asList(headerImage));

        String[] bodyImage = this.body.getStrings();
        result.addAll(Arrays.asList(bodyImage));

        String[] notesImage = this.notes.getStrings();
        result.addAll(Arrays.asList(notesImage));

        String[] resourcesImage = this.resources.getStrings();
        result.addAll(Arrays.asList(resourcesImage));

        result.add(TEMPLATE_END);

        return result.toArray(new String[result.size()]);
    }
}
