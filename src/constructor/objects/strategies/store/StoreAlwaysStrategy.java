package constructor.objects.strategies.store;

import constructor.objects.strategies.StoreStrategy;
import dated.Dated;

/**
 * Стратегия "вечного" хранения
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class StoreAlwaysStrategy implements StoreStrategy {

    public boolean accepted(final Dated _dated) {
        return true;
    }
}
