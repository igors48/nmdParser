package http;

/**
 * @author Igor Usenko
 *         Date: 25.09.2008
 */
public interface Data {

    byte[] getData() throws DataException;

    byte[] getData(int _from, int _to) throws DataException;

    String getCharsetName();

    int size();

    class DataException extends Exception {

        public DataException() {
            super();
        }

        public DataException(final String _s) {
            super(_s);
        }

        public DataException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public DataException(final Throwable _throwable) {
            super(_throwable);
        }

    }

}
