package dated.document;

import flowtext.Document;

/**
 * @author Igor Usenko
 *         Date: 09.10.2008
 */
public interface Converter {

    Document convert(DatedDocument _document) throws ConverterException;

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