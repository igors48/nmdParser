package converter.format.fb2.text;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2CodeText extends Fb2AbstractText {

    private static final String OPEN_TAG = "<code>";
    private static final String CLOSE_TAG = "</code>";

    public Fb2CodeText(final String _content) {
        super(_content);
    }

    public String[] getStrings() {
        StringBuffer result = new StringBuffer();

        result.append(OPEN_TAG);
        result.append(this.content);
        result.append(CLOSE_TAG);

        return new String[]{result.toString()};
    }
}
