package constructor.objects.interpreter.core.standard;

import constructor.objects.interpreter.core.Fragment;
import constructor.objects.interpreter.core.FragmentAnalyser;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import dated.item.atdc.Author;
import dated.item.atdc.HtmlContent;
import util.Assert;

/**
 * Обработчик фрагмента по умолчанию.
 *
 * @author Igor Usenko
 *         Date: 07.03.2009
 */
public class OneFragmentAnalyser implements FragmentAnalyser {

    public DatedItem getItem(final Fragment _fragment) {
        Assert.notNull(_fragment, "Fragment is null.");

        return new AtdcItem(new Author("", "", ""),
                _fragment.getModification().getTitle(),
                _fragment.getModification().getDate(),
                new HtmlContent(_fragment.getUrl(), _fragment.getImage(), _fragment.getBase()));
    }
}
