package constructor.objects.output.core.processor;

import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.core.ChannelDataListProcessor;
import constructor.objects.output.core.linker.Builder;
import constructor.objects.output.core.linker.Linker;
import constructor.objects.output.core.linker.RootSection;
import dated.DatedItem;
import dated.DatedItemTools;
import flowtext.Body;
import flowtext.Document;
import flowtext.Header;
import timeservice.TimeService;
import util.Assert;

import java.util.Date;

/**
 * Простой обработчик данных канала. Из каждого списка каналов
 * берет данные первого интерпретатора. Тянет из них датированные
 * элементы. Формирует из них документ, группируя оных по дате.
 *
 * @author Igor Usenko
 *         Date: 25.04.2009
 */
public class StandardChannelDataListProcessorEx implements ChannelDataListProcessor {

    private static final String VERSION = "1.0";

    private static final String CAN_T_GET_TITLE_TEXT = "Can`t get title.";

    private final StandardChannelDataListProcessorContext context;

    public StandardChannelDataListProcessorEx(final StandardChannelDataListProcessorContext _context) {
        Assert.notNull(_context, "Standard channel data list processor context is null");
        this.context = _context;
    }

    public Document process(final ChannelDataList _data, final String _title, final TimeService _timeService) {
        Assert.notNull(_data, "Channel data is null.");
        Assert.notNull(_title, "Title is null.");
        Assert.notNull(_timeService, "Time service is null.");

        return new Document(createHeader(_data, _title, _timeService), createBody(_data, _timeService));
    }

    //todo замещение одного другим - как-то кривовато... и в тестах пришлось закомментить
    private Header createHeader(final ChannelDataList _data, final String _title, final TimeService _timeService) {
        String lastName = generateLastName(_data);

        // todo берем только из первого. а остальные?
        ChannelDataHeader channelDataHeader = _data.get(0).getHeader();

        if (lastName.length() == 0) {
            lastName = channelDataHeader.getLastName();
        }

        String title = _title;

        if (title.isEmpty()) {
            title = channelDataHeader.getTitle();
        }

        if (title.isEmpty()) {
            title = CAN_T_GET_TITLE_TEXT;
        }

        return new Header(channelDataHeader.getFirstName(), lastName, title,
                channelDataHeader.getCoverUrl(),
                new Date(_timeService.getCurrentTime()),
                channelDataHeader.getGenres(),
                ChannelDataProcessorUtils.getId(_timeService.getCurrentTime()),
                channelDataHeader.getLang(),
                this.context.getVersionInfo().getCliApplicationTitle(),
                VERSION);
    }

    private String generateLastName(final ChannelDataList _data) {
        String result = "";

        DatedItem latest = ChannelDataTools.getLatestItem(_data);

        if (latest != null) {
            result = DatedItemTools.getStringInfo(latest);
        }

        return result;
    }

    private Body createBody(final ChannelDataList _data, final TimeService _timeService) {
        RootSection rootSection = Linker.create(_data, this.context.getDateSectionMode(), this.context.isFromNewToOld());

        return Builder.build(rootSection, _timeService, this.context.isResolveImageLinks());
    }

}