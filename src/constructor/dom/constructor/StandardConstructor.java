package constructor.dom.constructor;

import constructor.dom.*;
import util.Assert;
import util.IOTools;

import java.io.InputStream;

/**
 * Стандартный конструктор.
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public class StandardConstructor implements Constructor {

    private final Locator locator;
    private final Loader loader;
    private final ConstructorFactory factory;
    private final Preprocessor preprocessor;

    public StandardConstructor(final Locator _locator, final Loader _loader, final ConstructorFactory _factory, final Preprocessor _preprocessor) {
        Assert.notNull(_locator, "Locator is null.");
        Assert.notNull(_loader, "Loader is null.");
        Assert.notNull(_factory, "Factory is null.");
        Assert.notNull(_preprocessor, "Preprocessor is null.");

        this.locator = _locator;
        this.loader = _loader;
        this.factory = _factory;
        this.preprocessor = _preprocessor;
    }

    public Blank create(final String _id, final ObjectType _type) throws ConstructorException {
        Assert.isValidString(_id, "Id not valid.");
        Assert.notNull(_type, "Object type is null");

        InputStream original = null;
        InputStream preprocessed = null;

        try {
            original = this.locator.locate(_id, _type);
            preprocessed = this.preprocessor.preprocess(original);
            Blank result = this.loader.load(preprocessed, this.factory);
            result.setId(_id);

            return result;
        } catch (Locator.LocatorException e) {
            throw new ConstructorException(e);
        } catch (Loader.LoaderException e) {
            throw new ConstructorException(e);
        } catch (Preprocessor.PreprocessorException e) {
            throw new ConstructorException(e);
        } finally {
            IOTools.close(preprocessed);
            IOTools.close(original);
        }
    }
}
