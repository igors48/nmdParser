package resource;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public class ResourceConverterFactory implements ConverterFactory {

    private final String tempDirectory;

    public ResourceConverterFactory(String _tempDirectory) {
        Assert.isValidString(_tempDirectory);

        this.tempDirectory = _tempDirectory;
    }

    public Converter getConverter(ResourceType _from, ResourceType _to) {
        Assert.notNull(_from);
        Assert.notNull(_to);

        Converter result = null;

        if ((_from == ResourceType.IMAGE_GIF) && (_to == ResourceType.IMAGE_PNG)) {
            result = new GifToPngConverter(this.tempDirectory);
        }

        return result;
    }
}
