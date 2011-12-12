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

    class ConverterException extends Exception {

        public ConverterException() {
            super();
        }

        public ConverterException(final String _s) {
            super(_s);
        }

        public ConverterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public ConverterException(final Throwable _throwable) {
            super(_throwable);
        }

    }
    
}
