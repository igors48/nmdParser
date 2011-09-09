package converter;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public interface Converter {

    List<String> convert(ConverterContext _context) throws ConverterException;

    public class ConverterException extends Exception {
        public ConverterException() {
        }

        public ConverterException(String s) {
            super(s);
        }

        public ConverterException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ConverterException(Throwable throwable) {
            super(throwable);
        }
    }
}
