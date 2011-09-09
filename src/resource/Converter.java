package resource;

import downloader.Data;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public interface Converter {
    public Data convert(Data _data) throws ConverterException;

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
