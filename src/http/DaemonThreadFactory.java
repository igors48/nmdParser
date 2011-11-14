package http;

import util.Assert;

import java.util.concurrent.ThreadFactory;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 30.08.2011
 */
public class DaemonThreadFactory implements ThreadFactory {

    private final String namePrefix;

    public DaemonThreadFactory(final String _namePrefix) {
        Assert.isValidString(_namePrefix);
        this.namePrefix = _namePrefix;
    }

    public Thread newThread(final Runnable _runnable) {
        Assert.notNull(_runnable);

        final Thread result = new Thread(_runnable);

        result.setDaemon(true);
        result.setName(namePrefix);

        return result;
    }

}
