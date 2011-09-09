package constructor.dom.preprocessor;

import constructor.dom.Preprocessor;
import util.Assert;

import java.io.InputStream;

/**
 * @author Igor Usenko
 *         Date: 10.10.2009
 */
public class NullPreprocessor implements Preprocessor {

    public InputStream preprocess(final InputStream _stream) throws PreprocessorException {
        Assert.notNull(_stream, "Stream is null");
        return _stream;
    }
}
