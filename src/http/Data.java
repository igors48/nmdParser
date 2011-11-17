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

    public class DataException extends Exception {
        public DataException() {
        }

        public DataException(String s) {
            super(s);
        }

        public DataException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public DataException(Throwable throwable) {
            super(throwable);
        }
    }
}
