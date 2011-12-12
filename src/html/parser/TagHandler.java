package html.parser;

import flowtext.DocumentBuilder;
import html.parser.tag.HtmlTag;

/**
 * @author Igor Usenko
 *         Date: 19.09.2008
 */
public interface TagHandler {
    
    void handle(HtmlTag _tag, DocumentBuilder _builder) throws TagHandlerException;

    class TagHandlerException extends Exception {

        public TagHandlerException() {
            super();
        }

        public TagHandlerException(final String _s) {
            super(_s);
        }

        public TagHandlerException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public TagHandlerException(final Throwable _throwable) {
            super(_throwable);
        }

    }
    
}
