package html.parser;

import flowtext.DocumentBuilder;
import html.parser.tag.HtmlTag;

/**
 * @author Igor Usenko
 *         Date: 19.09.2008
 */
public interface TagHandler {
    void handle(HtmlTag _tag, DocumentBuilder _builder) throws TagHandlerException;

    public class TagHandlerException extends Exception {
        public TagHandlerException() {
        }

        public TagHandlerException(String s) {
            super(s);
        }

        public TagHandlerException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public TagHandlerException(Throwable throwable) {
            super(throwable);
        }
    }
}
