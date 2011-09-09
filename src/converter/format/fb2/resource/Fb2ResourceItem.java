package converter.format.fb2.resource;

import converter.format.fb2.Stringable;
import downloader.Data;
import downloader.data.EmptyData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import resource.ResourceType;
import resource.ResourceUtil;
import resource.split.ImageHelper;
import resource.split.ImageHelperContext;
import resource.split.ImageProcessingContext;
import resource.split.SplitItem;
import util.Assert;
import util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 06.09.2008
 */
public class Fb2ResourceItem implements Stringable {

    private static final String PREFIX = "<binary content-type=\"";

    private static final String INSERT01 = "\" id=\"";

    private static final String INSERT02 = "\">";

    private static final String POSTFIX = "</binary>";

    private final String tag;

    private final String base;
    private final String address;
    private final Fb2ResourceConversionContext conversionContext;

    private final Log log;

    private String resolvedAddress;
    private boolean fromCache;

    private Data data;

    //todo нужно создать отдельную абстракцию "внешний ресурс"
    public Fb2ResourceItem(final String _base, final String _address, final String _tag, final Fb2ResourceConversionContext _conversionContext) {
        Assert.notNull(_base, "Base is null");
        this.base = _base;

        Assert.isValidString(_address, "Address is not valid");
        this.address = _address;

        Assert.isValidString(_tag, "Tag is not valid");
        this.tag = _tag;

        Assert.notNull(_conversionContext, "Conversion context is null");
        this.conversionContext = _conversionContext;

        this.data = new EmptyData();
        this.fromCache = false;

        this.log = LogFactory.getLog(getClass());
    }

    public String getTag() {
        return this.tag;
    }

    public String getBase() {
        return this.base;
    }

    public String getAddress() {
        return this.address;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(final Data _data) {
        Assert.notNull(_data, "Resource data is null");

        this.data = _data;
    }

    public String[] getStrings() {
        StringBuffer result = new StringBuffer();

        ImageTypeAndData typeAndData = getImageDataAndType(this.conversionContext);

        // это на случай если приехала битая картинка
        if (!typeAndData.getData().isEmpty()) {
            result.append(PREFIX);
            result.append(ResourceType.getType(typeAndData.getType()));
            result.append(INSERT01);
            result.append(this.tag);
            result.append(INSERT02);
            result.append(typeAndData.getData());
            result.append(POSTFIX);
        }

        return new String[]{result.toString()};
    }

    private List<ImageTypeAndData> getImages() {
        List<ImageTypeAndData> result = new ArrayList<ImageTypeAndData>();

        BufferedImage originalImage;
        byte[] data;

        try {
            data = this.data.getData();
            originalImage = ImageIO.read(new ByteArrayInputStream(this.data.getData()));

            if (originalImage != null) {
                ImageProcessingContext imageProcessingContext = createProcessingContext(originalImage);
                result.addAll(processImage(originalImage, imageProcessingContext));
            } else {
                this.log.debug("Image format not recognized. Image not preprocessed.");
            }

        } catch (Throwable e) {
            this.log.error("Error creating Base64 resource.", e);
            //result = Base64.encodeBytes(this.conversionContext.getDummy());
        } finally {
            originalImage = null;
            data = null;
            /*
            generatedImage = null;

            if (graphics2D != null) {
                graphics2D.dispose();
            }

            graphics2D = null;
            */
            System.gc();
        }

        return result;
    }

    private List<ImageTypeAndData> processImage(final BufferedImage _image, final ImageProcessingContext _processingContext) {
        List<ImageTypeAndData> result = new ArrayList<ImageTypeAndData>();

        for (SplitItem current : _processingContext.getSplitItems()) {
            result.add(processClipItem(_image, current.getTop(), current.getLeft(), current.getWidth(), current.getHeight(), _processingContext.getScale(), _processingContext.getRotate(), this.conversionContext.isGrayscale()));
        }

        return result;
    }

    private ImageTypeAndData processClipItem(final BufferedImage _image, final int _top, final int _left, final int _width, final int _height, final double _scale, final int _rotate, final boolean _grayscale) {
        BufferedImage generatedImage = new BufferedImage((int) (_width * _scale), (int) (_height * _scale), _grayscale ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = generatedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        boolean processed = graphics2D.drawImage(_image, _top, _left, _width, _height, Color.WHITE, null);

        if (processed) {
            ByteArrayOutputStream generatedOutput = new ByteArrayOutputStream();
            //ImageIO.write(generatedImage, "jpeg", generatedOutput);
            //data = generatedOutput.toByteArray();
            //type = ResourceType.IMAGE_JPEG;
        } else {
            this.log.debug("Draw image returns false");
        }

        graphics2D.dispose();

        return new ImageTypeAndData(" ", ResourceType.IMAGE_JPEG);
    }

    private ImageProcessingContext createProcessingContext(final BufferedImage _originalImage) {
        ImageHelperContext imageHelperContext = new ImageHelperContext(_originalImage.getWidth(),
                _originalImage.getHeight(),
                this.conversionContext.getMaxWidth(),
                this.conversionContext.getMaxHeight(),
                this.conversionContext.getScaleTreshold(),
                this.conversionContext.isRotateEnabled(),
                this.conversionContext.isSplitEnabled(),
                this.conversionContext.getMinOverlap());

        return ImageHelper.handle(imageHelperContext);
    }

    private ImageTypeAndData getImageDataAndType(final Fb2ResourceConversionContext _conversionContext) {
        String result = "";
        ResourceType type = ResourceUtil.getResourceType(this.data);

        BufferedImage originalImage;
        BufferedImage generatedImage;
        Graphics2D graphics2D = null;
        byte[] data;

        try {
            data = this.data.getData();
            originalImage = ImageIO.read(new ByteArrayInputStream(this.data.getData()));

            if (originalImage != null) {
                int adjHeight = originalImage.getHeight();
                int adjWidth = originalImage.getWidth();

                float ratio = ((float) adjWidth) / ((float) adjHeight);

                if (adjHeight > _conversionContext.getMaxHeight()) {
                    adjHeight = _conversionContext.getMaxHeight();
                    adjWidth = (int) (adjHeight * ratio);
                }

                if (adjWidth > _conversionContext.getMaxWidth()) {
                    adjWidth = _conversionContext.getMaxWidth();
                    adjHeight = (int) (adjWidth / ratio);
                }

                if ((adjWidth > 1) && (adjHeight > 1)) {
                    generatedImage = new BufferedImage(adjWidth, adjHeight, _conversionContext.isGrayscale() ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB);

                    graphics2D = generatedImage.createGraphics();
                    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                    boolean processed = graphics2D.drawImage(originalImage, 0, 0, generatedImage.getWidth(), generatedImage.getHeight(), Color.WHITE, null);

                    if (processed) {
                        ByteArrayOutputStream generatedOutput = new ByteArrayOutputStream();
                        ImageIO.write(generatedImage, "jpeg", generatedOutput);
                        data = generatedOutput.toByteArray();
                        type = ResourceType.IMAGE_JPEG;
                    } else {
                        this.log.debug("Draw image returns false");
                    }

                    graphics2D.dispose();

                } else {
                    this.log.debug("adjWidth < 1 or adjHeight < 1");
                }
            } else {
                this.log.debug("Image format not recognized. Image not preprocessed.");
            }

            result = Base64.encodeBytes(data);

        } catch (Throwable e) {
            this.log.error("Error creating Base64 resource.", e);
            result = Base64.encodeBytes(this.conversionContext.getDummy());
        } finally {
            originalImage = null;
            generatedImage = null;
            data = null;

            if (graphics2D != null) {
                graphics2D.dispose();
            }

            graphics2D = null;

            System.gc();
        }

        return new ImageTypeAndData(result, type);
    }

    public String getResolvedAddress() {
        return this.resolvedAddress;
    }

    public void setResolvedAddress(final String _resolvedAddress) {
        Assert.isValidString(_resolvedAddress, "Resolved address is not valid");
        this.resolvedAddress = _resolvedAddress;
    }

    public boolean isFromCache() {
        return this.fromCache;
    }

    public void setFromCache(final boolean _fromCache) {
        this.fromCache = _fromCache;
    }

    private class ImageTypeAndData {
        private final ResourceType type;
        private final String data;

        public ImageTypeAndData(final String _data, final ResourceType _type) {
            this.data = _data;
            this.type = _type;
        }

        public String getData() {
            return this.data;
        }

        public ResourceType getType() {
            return this.type;
        }
    }
}
