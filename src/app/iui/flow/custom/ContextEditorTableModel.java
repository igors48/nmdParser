package app.iui.flow.custom;

import app.iui.StringResource;
import util.Assert;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 03.07.2010
 */
public class ContextEditorTableModel extends AbstractTableModel {

    private static final String CONTEXT_EDITOR_PARAMETER_NAME_HEADER_KEY = "iui.context.editor.parameter.name.header";
    private static final String CONTEXT_EDITOR_PARAMETER_VALUE_HEADER_KEY = "iui.context.editor.parameter.value.header";

    private final Map<String, String> context;
    private final String parameterHeader;
    private final String valueHeader;

    private final List<String> keys;

    public ContextEditorTableModel(final Map<String, String> _context, final StringResource _stringResource) {
        Assert.notNull(_context, "Context is null");
        //Assert.isFalse(_context.isEmpty(), "Context is empty");
        this.context = _context;

        Assert.notNull(_stringResource, "String resource is null");
        this.parameterHeader = _stringResource.getString(CONTEXT_EDITOR_PARAMETER_NAME_HEADER_KEY);
        this.valueHeader = _stringResource.getString(CONTEXT_EDITOR_PARAMETER_VALUE_HEADER_KEY);

        this.keys = Arrays.asList(_context.keySet().toArray(new String[_context.size()]));
    }

    public String getColumnName(final int _i) {
        return _i == 0 ? this.parameterHeader : this.valueHeader;
    }

    public int getRowCount() {
        return this.context.size();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(final int _rowIndex, final int _columnIndex) {
        Object result = null;

        if (_columnIndex == 0) {
            result = this.keys.get(_rowIndex);
        }

        if (_columnIndex == 1) {
            result = this.context.get(this.keys.get(_rowIndex));
        }

        return result;
    }

    public void setValueAt(final Object _value, final int _rowIndex, final int _columnIndex) {

        if (_columnIndex == 1) {
            this.context.put(this.keys.get(_rowIndex), (String) _value);
        }
    }

    public boolean isCellEditable(final int _rowIndex, final int _columnIndex) {
        return _columnIndex == 1;
    }
}
