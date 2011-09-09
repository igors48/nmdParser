package converter.format.fb2.text;

import converter.format.fb2.Stringable;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2EmptyLine implements Stringable {

    private static final String TAG = "<empty-line/>";

    public String[] getStrings() {
        return new String[]{TAG};
    }
}
