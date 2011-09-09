package app.iui.flow;

import util.Assert;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Igor Usenko
 *         Date: 28.11.2010
 */
public class Session {

    private final Deque<Model> history;

    public Session() {
        this.history = new LinkedList<Model>();
    }

    public void putToHistory(final Model _formRequest) {
        Assert.notNull(_formRequest, "Form request is null");

        this.history.offer(_formRequest);
    }

    public Model pollFromHistory() {
        this.history.pollLast();

        return this.history.peekLast();
    }

    public void resetHistory() {
        this.history.clear();
    }
}
