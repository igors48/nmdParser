package constructor.objects.interpreter.core.standard;

import constructor.objects.interpreter.core.Page;
import constructor.objects.interpreter.core.PageListAnalyser;
import constructor.objects.interpreter.core.PageListItem;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ���������� ��� ����������� ������������� ������ �� ����� ��������
 *
 * @author Igor Usenko
 *         Date: 05.12.2008
 */
public class OnePageListAnalyser implements PageListAnalyser {

    public List<PageListItem> getPageList(final Page _page) {
        Assert.notNull(_page, "Page is null");

        List<PageListItem> result = newArrayList();

        PageListItem item = new PageListItem(_page.getModification(), _page.getModification().getUrl());
        result.add(item);

        return result;
    }
}
