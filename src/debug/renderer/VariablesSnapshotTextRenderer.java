package debug.renderer;

import debug.Snapshot;
import debug.SnapshotTextRenderer;
import debug.snapshot.ValueChangedPair;
import debug.snapshot.VariablesSnapshot;
import util.Assert;

import java.util.*;

import static util.CollectionUtils.newArrayList;

/**
 * �������� �������� ����������
 *
 * @author Igor Usenko
 *         Date: 19.08.2009
 */
public class VariablesSnapshotTextRenderer implements SnapshotTextRenderer {
    public static final String NO_CHANGED_PREFIX = "    ";
    public static final String CHANGED_PREFIX = " >> ";
    public static final String INDEX_VALUE_DIVIDER = " : ";

    private static final String PREFIX = "V:";

    public List<String> render(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Snapshot is null");
        Assert.isTrue(_snapshot instanceof VariablesSnapshot, "This is not variables snapshot");

        List<String> result = newArrayList();

        Map<String, Map<Integer, ValueChangedPair>> items = ((VariablesSnapshot) _snapshot).getItems();
        List<String> names = getVariablesList(items);

        for (String name : names) {
            renderVariable(name, items.get(name), result);
        }

        return result;
    }

    public String getPrefix() {
        return PREFIX;
    }

    private List<String> getVariablesList(final Map<String, Map<Integer, ValueChangedPair>> _items) {
        List<String> result = Arrays.asList(_items.keySet().toArray(new String[_items.size()]));
        Collections.sort(result);

        return result;
    }

    private void renderVariable(final String _name, final Map<Integer, ValueChangedPair> _elements, final List<String> _result) {
        List<Integer> indexes = Arrays.asList(_elements.keySet().toArray(new Integer[_elements.size()]));
        Collections.sort(indexes);

        _result.add(_name);

        for (Integer index : indexes) {
            ValueChangedPair pair = _elements.get(index);
            renderElement(index, pair.getValue(), pair.isChanged(), _result);
        }
    }

    private void renderElement(final int _index, final String _value, final boolean _changed, final List<String> _result) {
        StringBuffer buffer = new StringBuffer();

        //TODO consider repalce with ternary
        if (_changed) {
            buffer.append(CHANGED_PREFIX);
        } else {
            buffer.append(NO_CHANGED_PREFIX);
        }

        buffer.append(_index).append(INDEX_VALUE_DIVIDER).append(_value);

        _result.add(buffer.toString());
    }
}
