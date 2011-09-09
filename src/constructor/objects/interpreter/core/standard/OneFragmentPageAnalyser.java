package constructor.objects.interpreter.core.standard;

import constructor.objects.interpreter.core.Fragment;
import constructor.objects.interpreter.core.Page;
import constructor.objects.interpreter.core.PageAnalyser;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * јнализатор страницы по умолчанию. —читает,
 * что страница - это один фрагмент.
 *
 * @author Igor Usenko
 *         Date: 05.12.2008
 */
public class OneFragmentPageAnalyser implements PageAnalyser {

    public List<Fragment> getFragments(Page _page) {
        Assert.notNull(_page, "Page is null.");

        List<Fragment> result = new ArrayList<Fragment>();

        Fragment fragment = new Fragment(_page.getModification(), _page.getUrl(), _page.getImage(), _page.getBase());
        result.add(fragment);

        return result;
    }
}
