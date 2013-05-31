package app.iui.flow;

/**
 * @author Igor Usenko
 *         Date: 19.12.2010
 */
public class ModelHandlerException extends Exception {

    public ModelHandlerException() {
        super();
    }

    public ModelHandlerException(final String _s) {
        super(_s);
    }

    public ModelHandlerException(final String _s, final Throwable _throwable) {
        super(_s, _throwable);
    }

    public ModelHandlerException(final Throwable _throwable) {
        super(_throwable);
    }
}
