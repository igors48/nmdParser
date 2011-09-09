package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 25.08.2011
 */
public class Subscriptions {

    private Subscription[] subscriptions;

    public Subscription[] getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(final Subscription[] _subscriptions) {
        Assert.notNull(_subscriptions, "Subscriptions is null");
        this.subscriptions = _subscriptions;
    }

}
