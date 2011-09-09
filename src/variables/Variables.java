package variables;

import debug.Snapshot;
import debug.snapshot.VariablesSnapshot;
import util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Область хранения для пар "имя переменной - список значений".
 * Пока мы поддерживаем только строковый тип
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public class Variables {

    public static final String DEFAULT_INPUT_VARIABLE_NAME = "input";
    public static final String DEFAULT_OUTPUT_VARIABLE_NAME = "output";
    public static final String DEFAULT_URL_VARIABLE_NAME = "url";

    private final Map<String, Variable> variables;

    private final ChangeLog changeLog;

    public Variables() {
        this.variables = new HashMap<String, Variable>();
        this.changeLog = new ChangeLog();
    }

    public void put(final String _name, final String _value) {
        Assert.isValidString(_name, "Incorrect variable name.");
        Assert.notNull(_value, "Variable value is null.");

        put(_name, 0, _value);
    }

    public String get(final String _name) {
        Assert.isValidString(_name, "Incorrect variable name.");

        return get(_name, 0);
    }

    public void remove(final String _name) {
        Assert.isValidString(_name, "Incorrect variable name.");

        if (this.variables.get(_name) != null) {
            this.variables.remove(_name);
        }
    }

    public void put(final String _name, final int _index, final String _value) {
        Assert.isValidString(_name, "Incorrect variable name.");
        Assert.notNull(_value, "Variable value is null.");
        Assert.greaterOrEqual(_index, 0, "Variable index < 0.");

        Variable variable = getVariable(_name);

        variable.put(_index, _value);
        storeChange(_name, _index);
    }

    public void putAll(final String _name, final List<String> _values) {
        Assert.isValidString(_name, "Incorrect variable name.");
        Assert.notNull(_values, "Values list is null.");

        if (!_values.isEmpty()) {
            int index = 0;

            for (String value : _values) {
                put(_name, index++, value);
            }
        }
    }

    public void appendAll(final String _name, final List<String> _values) {
        Assert.isValidString(_name, "Incorrect variable name.");
        Assert.notNull(_values, "Values list is null.");

        if (!_values.isEmpty()) {
            Variable variable = getVariable(_name);
            int index = getMaxIndex(variable);

            for (String current : _values) {
                put(_name, ++index, current);
            }
        }
    }

    public String get(final String _name, final int _index) {
        Assert.isValidString(_name, "Incorrect variable name.");
        Assert.greaterOrEqual(_index, 0, "Variable index < 0.");

        Variable variable = this.variables.get(_name);

        return variable == null ? null : variable.get(_index);
    }

    public int getSize(final String _name) {
        Assert.isValidString(_name, "Incorrect variable name.");

        Variable variable = this.variables.get(_name);

        return variable == null ? 0 : variable.size();
    }

    public VariableIterator getIterator(final String _name) {
        Assert.isValidString(_name, "Incorrect variable name.");

        Variable variable = this.variables.get(_name);

        return variable == null ? new EmptyVariableIterator() : new CommonVariableIterator(variable);
    }

    public Snapshot getSnapshot() {
        VariablesSnapshot result = new VariablesSnapshot();

        for (String name : this.variables.keySet()) {
            createVariableSnapshot(name, result);
        }

        this.changeLog.reset();

        return result;
    }

    public Variable getVariable(final String _name) {
        Variable variable = this.variables.get(_name);

        if (variable == null) {
            variable = new Variable();
            this.variables.put(_name, variable);
        }

        return variable;
    }

    private int getMaxIndex(Variable _variable) {
        return _variable.keySet().isEmpty() ? -1 : Collections.max(_variable.keySet());
    }

    private void createVariableSnapshot(final String _name, final VariablesSnapshot _result) {
        Variable elements = this.variables.get(_name);

        for (Integer index : elements.keySet()) {
            _result.addElement(_name, index, elements.get(index), this.changeLog.isChanged(_name, index));
        }
    }

    private void storeChange(final String _name, final int _index) {
        this.changeLog.add(_name, _index);
    }
}
