package converter.format.fb2.text;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2SubscriptText extends Fb2AbstractText {

    private static final String OPEN_TAG = "<sub>";
    private static final String CLOSE_TAG = "</sub>";

    public Fb2SubscriptText(final String _content) {
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
