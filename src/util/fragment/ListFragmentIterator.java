package util.fragment;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Итератор для получения списка по частям
 *
 * @author Igor Usenko
 *         Date: 28.06.2009
 */
public class ListFragmentIterator<E> {

    private final int fragmentSize;
    private final List<E> items;

    private int current;

    public ListFragmentIterator(final List<E> _items, final int _fragmentSize) {
        Assert.greater(_fragmentSize, 0, "Fragment size must be > 0.");
        Assert.notNull(_items, "Items is null");

        this.items = _items;
        this.fragmentSize = _fragmentSize;
    }

    public boolean hasNext() {
        return this.current < this.items.size();
    }

    public List<E> getNext() {
        List<E> result = newArrayList();

        for (int index = this.current; isCorrectIndex(index); ++index) {
            result.add(this.items.get(index));
        }
        this.current += this.fragmentSize;

        return result;
    }

    private boolean isCorrectIndex(final int _index) {
        return (_index < this.current + this.fragmentSize) && (_index < this.items.size());
    }
}
