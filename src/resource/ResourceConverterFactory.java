package resource;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public class ResourceConverterFactory implements ConverterFactory {

    private final String tempDirectory;

    public ResourceConverterFactory(final String _tempDirectory) {
        Assert.isValidString(_tempDirectory, "Temp directory is not valid");

        this.tempDirectory = _tempDirectory;
    }

    public Converter getConverter(final ResourceType _from, final ResourceType _to) {
        Assert.notNull(_from, "Origin type is null");
        Assert.notNull(_to, "Destination type is null");

        Converter result = null;

        if ((_from == ResourceType.IMAGE_GIF) && (_to == ResourceType.IMAGE_PNG)) {
            result = new GifToPngConverter(this.tempDirectory);
        }

        return result;
    }

}
