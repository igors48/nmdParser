package constructor.objects.output.core.linker;

import dated.DatedItem;
import dated.DatedItemConverterContext;
import flowtext.Body;
import flowtext.FlowTextUtil;
import flowtext.Section;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.03.2011
 */
public class Builder {

    private static final String DATE_SECTION_ID_PREFIX = "DATE";
    private static final String SECTION_DATE_FORMAT = "d MMMM yyyy, EEEE";

    private static final Log log = LogFactory.getLog(Builder.class);

    public static Body build(final RootSection _rootSection, final TimeService _timeService, final boolean _resolveImageLinks) {
        Assert.notNull(_rootSection, "Root section is null");
        Assert.notNull(_timeService, "Time service is null");

        Body result = new Body();

        for (LinkerSection current : _rootSection.getChildren()) {

            if (current instanceof DateSection) {
                buildDateSection((DateSection) current, result, _timeService, _resolveImageLinks);
            } else {
                buildContentSection((ContentSection) current, result, _resolveImageLinks);
            }
        }

        return result;
    }

    private static void buildContentSection(final ContentSection _section, final Body _body, final boolean _resolveImageLinks) {
        List<Section> sections = createContentSections(_section.getContent(), new DatedItemConverterContext(_resolveImageLinks, false));

        for (Section currentSection : sections) {
            _body.insertSection(currentSection);
        }
    }

    private static void buildDateSection(final DateSection _section, final Body _body, final TimeService _timeService, final boolean _resolveImageLinks) {
        Section result = createEmptyDateSection(_section.getDate(), _timeService);

        List<Section> sections = createContentSections(_section.getContent(), new DatedItemConverterContext(_resolveImageLinks, true));

        for (Section current : sections) {
            insertSection(result, current);
        }

        _body.insertSection(result);
    }

    private static List<Section> createContentSections(final List<DatedItem> _items, final DatedItemConverterContext _context) {
        List<Section> result = newArrayList();

        for (DatedItem current : _items) {
            result.add(current.getSectionConverter().convert(current, _context));
        }

        return result;
    }

    private static void insertSection(final Section _destination, final Section _section) {

        try {
            _destination.insertSection(_section);
        } catch (Exception e) {
            log.error("Error inserting section.", e);
        }
    }

    private static Section createEmptyDateSection(final Date _date, final TimeService _timeService) {
        return createWorkSection(DATE_SECTION_ID_PREFIX, getDateSectionTitleString(_date), _timeService);
    }

    private static Section createWorkSection(final String _idPrefix, final String _title, final TimeService _timeService) {
        Section result = new Section(_idPrefix + _timeService.getNanoTime());

        if (_title.length() != 0) {
            result.insertTitle(FlowTextUtil.createTitle(_title));
        }

        return result;
    }

    private static String getDateSectionTitleString(final Date _date) {
        return new SimpleDateFormat(SECTION_DATE_FORMAT).format(_date);
    }

}
