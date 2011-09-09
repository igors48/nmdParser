package resource;

import downloader.Data;
import downloader.data.DataFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public class GifToPngConverter implements Converter {

    private final Log log;

    private final String tempDirectory;

    public GifToPngConverter(final String _tempDirectory) {
        Assert.isValidString(_tempDirectory, "Temp directory is not valid");

        this.tempDirectory = _tempDirectory;
        this.log = LogFactory.getLog(getClass());
    }

    public Data convert(final Data _data) throws ConverterException {
        Assert.notNull(_data, "Data is null");

        Data result;

        try {
            BufferedImage buffer = ImageIO.read(new ByteArrayInputStream(_data.getData()));

            String outFileName = this.tempDirectory + File.createTempFile("prf", "sfx", new File(this.tempDirectory)).getName() + ".jpg";
            ImageIO.write(buffer, "png", new File(outFileName));

            result = new DataFile(outFileName);

            this.log.debug("Convert to Png completed successfully.");
        } catch (Exception e) {
            this.log.error("Error converting resource to Png format.");
            throw new ConverterException(e);
        }

        return result;
    }
}
