package util;

/**
 * @author Igor Usenko
 *         Date: 13.07.2010
 */
public class TimeoutTools {

    public static int getTimeout(final int _minTimeout, final int _maxTimeout, final int _currentTry, final int _maxTry) {
        Assert.greater(_minTimeout, 0, "Min timeout <= 0");
        Assert.greater(_maxTimeout, _minTimeout, "Max timeout <= Min timeout");
        Assert.greaterOrEqual(_currentTry, 0, "Current try < 0");
        Assert.greaterOrEqual(_maxTry, _currentTry, "Max try < Current try");

        return _minTimeout + ((_maxTimeout - _minTimeout) / _maxTry) * _currentTry;
    }

    private TimeoutTools() {
        // empty
    }
}
