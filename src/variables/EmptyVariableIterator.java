package variables;

/**
 * »тератор дл€ пустой(несуществующей) переменной
 *
 * @author Igor Usenko
 *         Date: 19.07.2009
 */
public class EmptyVariableIterator implements VariableIterator {

    public int getIndex() {
        return 0;
    }

    public int getKey() {
        return 0;
    }

    public int getSize() {
        return 0;
    }

    public boolean hasNext() {
        return false;
    }

    public Object next() {
        return null;
    }

    public void remove() {
        // empty
    }
}
