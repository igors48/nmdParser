package constructor.objects.channel.core.analyser;

import constructor.objects.channel.core.ChannelAnalyser;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.ChainProcessor;
import dated.item.modification.Modification;
import http.BatchLoader;
import http.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import variables.Variables;

/**
 * ������������ ������������� ���������� ������
 *
 * @author Igor Usenko
 *         Date: 06.08.2009
 */
public class CustomAnalyser implements ChannelAnalyser {

    private final ChainProcessor processor;

    private final Log log;

    public CustomAnalyser(final ChainProcessor _processor) {
        Assert.notNull(_processor, "Processor is null");
        this.processor = _processor;

        this.log = LogFactory.getLog(getClass());
    }

    public ChannelDataHeader getHeader(final Modification _modification, final BatchLoader _batchLoader, final String _coverUrl, long _pauseBetweenRequests) throws ChannelAnalyserException {
        Assert.notNull(_modification, "Modification is null");
        Assert.notNull(_batchLoader, "Page loader is null");
        Assert.notNull(_coverUrl, "Cover URL is null");
        Assert.greaterOrEqual(_pauseBetweenRequests, 0, "Pause between requests < 0");

        try {
            String pageImage = ChannelDataTools.loadImage(_modification.getUrl(), _batchLoader, _pauseBetweenRequests);

            String title = parseHeader(_modification, pageImage);
            String host = ChannelAnalyserTools.getHostName(_modification.getUrl());

            return new ChannelDataHeader(title, host, ChannelAnalyserTools.DEFAULT_LAST_NAME, _modification.getUrl(), _coverUrl);
        } catch (Data.DataException e) {
            throw new ChannelAnalyserException(e);
        }
    }

    private String parseHeader(final Modification _modification, final String _pageImage) {
        String result = ChannelAnalyserTools.parseTitle(_pageImage);

        try {
            Variables variables = new Variables();
            variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, _pageImage);
            variables.put(Variables.DEFAULT_URL_VARIABLE_NAME, _modification.getUrl());

            this.processor.process(variables);

            result = variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);
        } catch (VariableProcessor.VariableProcessorException e) {
            this.log.warn("Error parsing channel header.", e);
        }

        return result;
    }
}
