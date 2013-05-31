package converter.format.fb2.footnotes;

import converter.format.fb2.Stringable;
import util.Assert;

import java.util.Arrays;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 07.01.2009
 */
public class Fb2FootNoteList implements Stringable {

    private static final String NOTES_START_LINE_1 = "<body name=\"notes\">";
    private static final String NOTES_START_LINE_2 = "<title><p>Notes</p></title>";
    private static final String NOTES_END = "</body>";

    private final List<Fb2FootNote> notes;

    public Fb2FootNoteList() {
        this.notes = newArrayList();
    }

    public String appendFootNote(final String _content) {
        Assert.isValidString(_content, "Foot note content is invalid");

        String id = find(_content);

        if (id == null) {
            id = String.valueOf(notes.size() + 1);
            this.notes.add(new Fb2FootNote(id, _content));
        }

        return id;
    }

    public String[] getStrings() {
        List<String> result = newArrayList();

        if (this.notes.size() > 0) {
            result.add(NOTES_START_LINE_1);
            result.add(NOTES_START_LINE_2);

            for (Fb2FootNote note : this.notes) {
                result.addAll(Arrays.asList(note.getStrings()));
            }

            result.add(NOTES_END);
        }

        return result.toArray(new String[result.size()]);
    }

    private String find(final String _content) {
        String result = null;

        for (Fb2FootNote note : this.notes) {

            if (note.getContent().equalsIgnoreCase(_content)) {
                result = note.getId();
            }
        }

        return result;
    }
}
