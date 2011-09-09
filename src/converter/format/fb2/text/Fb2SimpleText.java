package converter.format.fb2.text;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2SimpleText extends Fb2AbstractText {

    public Fb2SimpleText(final String _content) {
        super(_content);
    }

    public String[] getStrings() {
        return new String[]{this.content};
    }

}
