package research.heuristic;

import util.Assert;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Igor Usenko
 *         Date: 16.03.2011
 */
public class Path {

    private final List<PathElement> elements;

    private int weight;

    public Path() {
        this.elements = new ArrayList<PathElement>();
        this.weight = 0;
    }

    public void append(final PathElement _element) {
        Assert.notNull(_element, "Element is null");
        this.elements.add(_element);
    }

    public int getWeight() {
        return this.weight;
    }

    public int getLength() {
        return this.elements.size();    
    }

    public List<PathElement> getElements() {
        return this.elements;
    }

    public void setWeight(final int _weight) {
        Assert.greaterOrEqual(_weight, 0, "Weight < 0");
        this.weight = _weight;
    }
}
