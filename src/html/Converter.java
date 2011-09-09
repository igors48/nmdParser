package html;

import dated.item.atdc.HtmlContent;
import flowtext.Section;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 11.09.2008
 */
public interface Converter {

    List<Section> convert(HtmlContent _content) throws ConverterException;

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
