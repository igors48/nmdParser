package constructor.objects.interpreter.core.standard;

import constructor.objects.dateparser.core.DateParser;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.interpreter.core.Fragment;
import constructor.objects.interpreter.core.FragmentAnalyser;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import dated.item.atdc.Author;
import dated.item.atdc.HtmlContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;
import util.TextTools;

import java.util.Date;

import static constructor.objects.interpreter.core.standard.StandardFragmentAnalyserUtils.callProcessorForList;
import static constructor.objects.interpreter.core.standard.StandardFragmentAnalyserUtils.callProcessorForString;

/**
 * Стандартный анализатор фрагмента. Для извлечения нужных данных
 * использует соответствующие процессоры.
 *
 * @author Igor Usenko
 *         Date: 28.04.2009
 */
public class StandardFragmentAnalyser implements FragmentAnalyser {

    private final FragmentAnalyserConfiguration configuration;
    private final TimeService refTimeService;

    private final Log log;

    private static final String CLOSING_BRACE = " ].";
    private static final String WELDING_DIVIDER = "<br>";

    public StandardFragmentAnalyser(final FragmentAnalyserConfiguration _configuration, final TimeService _refTimeService) {
        Assert.notNull(_configuration, "Fragment analyser configuration is null.");
        this.configuration = _configuration;

        Assert.notNull(_refTimeService, "Reference time service is null");
        this.refTimeService = _refTimeService;

        this.log = LogFactory.getLog(getClass());
    }

    public DatedItem getItem(final Fragment _fragment) {
        Assert.notNull(_fragment, "Fragment is null.");

        String authorNick = getAuthorNick(_fragment);
        String authorInfo = getAuthorInfo(_fragment);
        String authorAvatar = getAuthorAvatar(_fragment);
        String fragmentTitle = getFragmentTitle(_fragment);
        Author author = new Author(authorNick, authorInfo, authorAvatar);

        Date date = getDate(_fragment);

        if (fragmentTitle.isEmpty()) {
            fragmentTitle = authorNick.isEmpty() ? _fragment.getModification().getTitle() : "";
        }

        String contentString = getContent(_fragment);

        if (contentString.isEmpty()) {
            contentString = "Empty data received from address [ " + _fragment.getUrl() + CLOSING_BRACE;
        }

        HtmlContent content = new HtmlContent(_fragment.getUrl(), contentString, _fragment.getBase());

        return new AtdcItem(author, fragmentTitle, date, content);
    }

    private Date getDate(final Fragment _fragment) {
        Date result = _fragment.getModification().getDate();

        try {

            if (this.configuration.getDateParserAdapter() != null) {

                String extracted = callProcessorForString(_fragment, this.configuration.getDateProcessor());

                if (!extracted.isEmpty()) {
                    DateParser parser = this.configuration.getDateParserAdapter().getDateParser();
                    Date parsed = parser.parse(extracted, this.configuration.getTimeCorrection(), this.refTimeService);

                    if (parsed != null) {
                        result = parsed;
                    }
                }
            }
            //todo спорное решение
        } catch (Exception e) {
            this.log.error("Error while extracting date from [ " + _fragment.getUrl() + CLOSING_BRACE, e);
        }

        return result;
    }

    private String getContent(final Fragment _fragment) {
        String result = "";

        try {
            result = TextTools.weld(callProcessorForList(_fragment, this.configuration.getContentProcessor()), WELDING_DIVIDER);
        } catch (Exception e) {
            this.log.error("Error while extracting content from [ " + _fragment.getUrl() + CLOSING_BRACE, e);
        }

        return result;
    }

    private String getAuthorNick(final Fragment _fragment) {
        String result = "";

        try {
            result = callProcessorForString(_fragment, this.configuration.getNickProcessor());
        } catch (Exception e) {
            this.log.error("Error while extracting author nick from [ " + _fragment.getUrl() + CLOSING_BRACE, e);
        }

        return result;
    }

    private String getAuthorInfo(final Fragment _fragment) {
        String result = "";

        try {
            result = callProcessorForString(_fragment, this.configuration.getInfoProcessor());
        } catch (Exception e) {
            this.log.error("Error while extracting author info from [ " + _fragment.getUrl() + CLOSING_BRACE, e);
        }

        return result;
    }

    private String getAuthorAvatar(final Fragment _fragment) {
        String result = "";

        try {
            result = callProcessorForString(_fragment, this.configuration.getAvatarProcessor());
        } catch (Exception e) {
            this.log.error("Error while extracting author avatar from [ " + _fragment.getUrl() + CLOSING_BRACE, e);
        }

        return result;
    }

    private String getFragmentTitle(final Fragment _fragment) {
        String result = "";

        try {
            result = callProcessorForString(_fragment, this.configuration.getTitleProcessor());
        } catch (Exception e) {
            this.log.error("Error while extracting fragment title from [ " + _fragment.getUrl() + CLOSING_BRACE, e);
        }

        return result;
    }

}
