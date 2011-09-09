package converter.format.fb2.text;

import converter.format.fb2.Fb2Tools;
import converter.format.fb2.Stringable;
import util.Assert;

/**
 * Абстрактный предок для текстовых элементов.
 * Назначение чистка текста от нежелательного
 *
 * @author Igor Usenko
 *         Date: 02.05.2009
 */
public abstract class Fb2AbstractText implements Stringable {

    protected String content;

    public Fb2AbstractText(final String _content) {
        Assert.isValidString(_content);

        this.content = Fb2Tools.processEntities(_content);
    }

}
