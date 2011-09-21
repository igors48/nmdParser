package constructor.objects.interpreter.core.standard;

import app.controller.NullController;
import constructor.objects.AdapterException;
import constructor.objects.interpreter.core.*;
import constructor.objects.interpreter.core.data.InterpreterData;
import dated.DatedItem;
import dated.item.modification.Modification;
import http.Data;
import http.data.DataUtil;
import html.HttpData;
import util.Assert;
import util.fragment.ListFragmentIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ����������� �������������. �������� �� ���� ������ �����������.
 *
 * @author Igor Usenko
 *         Date: 09.04.2009
 */
public class StandardInterpreter implements InterpreterEx {

    private final InterpreterAdapter adapter;

    public StandardInterpreter(final InterpreterAdapter _adapter) {
        Assert.notNull(_adapter, "Interpreter adapter is null.");
        this.adapter = _adapter;
    }

    public InterpreterData process() throws InterpreterException {

        try {
            String mainPageImage = getMainPageImage(this.adapter.getModification());
            Page mainPage = new Page(this.adapter.getModification(), this.adapter.getModification().getUrl(), mainPageImage);
            List<PageListItem> pagesUrls = parseMainPage(mainPage);
            Collections.reverse(pagesUrls);
            List<DatedItem> items = new ArrayList<DatedItem>();

            ListFragmentIterator<PageListItem> iterator = new ListFragmentIterator<PageListItem>(pagesUrls, this.adapter.getPrecachedItemsCount());

            while (iterator.hasNext() && !enoughItems(items.size(), this.adapter.getLastItemCount())) {
                List<Page> pages = loadPages(iterator.getNext());
                List<DatedItem> parsedItems = parsePages(pages, items.size(), this.adapter.getLastItemCount());

                items.addAll(parsedItems);
            }

            return new InterpreterData(this.adapter.getId(), items);
        } catch (AdapterException e) {
            throw new InterpreterException(e);
        }
    }

    private boolean enoughItems(final int _count, final int _max) {
        return !(_max == 0 || _count <= _max);
    }

    private List<PageListItem> parseMainPage(final Page _page) throws AdapterException {
        return this.adapter.getPageListAnalyser().getPageList(_page);
    }

    private String getMainPageImage(final Modification _modification) throws AdapterException {
        HttpData data = this.adapter.getWebPageLoader().loadUrl(_modification.getUrl());
        Data mainPage = data.getData();

        return DataUtil.getDataImage(mainPage);
    }

    private List<Page> loadPages(final List<PageListItem> _items) throws AdapterException {
        List<Page> result = new ArrayList<Page>();

        List<String> urls = createUrlList(_items);

        Map<String, HttpData> data = this.adapter.getWebPageLoader().loadUrls(urls, this.adapter.getPauseBetweenRequests(), new NullController());

        for (PageListItem item : _items) {
            HttpData dataItem = data.get(item.getUrl());
            String image = DataUtil.getDataImage(dataItem.getData());

            // ����������� feed proxies. ������ ������ ����������� ����������� ����� ������ ������ �����
            Page page = new Page(item.getModification(), dataItem.getUrl(), image);
            result.add(page);
        }

        return result;
    }

    private List<String> createUrlList(final List<PageListItem> _items) {
        List<String> result = new ArrayList<String>();

        for (PageListItem item : _items) {
            result.add(item.getUrl());
        }

        return result;
    }

    private List<DatedItem> parsePages(final List<Page> _pages, final int _current, final int _max) throws AdapterException {
        List<DatedItem> result = new ArrayList<DatedItem>();

        for (Page page : _pages) {
            List<DatedItem> items = parsePage(page);
            result.addAll(items);

            if (enoughItems(_current + result.size(), _max)) {
                break;
            }
        }

        return result;
    }

    private List<DatedItem> parsePage(final Page _page) throws AdapterException {
        List<DatedItem> result = new ArrayList<DatedItem>();

        List<Fragment> fragments = this.adapter.getPageAnalyser().getFragments(_page);

        for (Fragment fragment : fragments) {
            DatedItem item = this.adapter.getFragmentAnalyser().getItem(fragment);
            result.add(item);
        }

        return result;
    }


}
