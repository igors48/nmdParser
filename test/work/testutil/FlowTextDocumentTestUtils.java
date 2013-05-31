package work.testutil;

import constructor.objects.channel.core.ChannelData;
import flowtext.*;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.12.2008
 */
public final class FlowTextDocumentTestUtils {

    public static FlowTextObject getObject(Section _section, int _contIndex, int _objIndex) {
        return ((Paragraph) getObject(_section, _contIndex)).getContent().get(_objIndex);
    }

    public static FlowTextObject getObject(Section _section, int _contIndex) {
        return (_section.getContent().get(_contIndex));
    }

    public static String getString(Section _section, int _contIndex, int _objIndex) {
        return ((FlowTextObjectText) getObject(_section, _contIndex, _objIndex)).getText();
    }

    public static boolean isDocumentHeaderCorrectEx(Header _header, List<ChannelData> _data) {
        boolean result = true;

        if (!_header.getAuthorFirstName().equalsIgnoreCase(_data.get(0).getHeader().getFirstName())) {
            result = false;
        } else if (!_header.getBookTitle().equalsIgnoreCase(_data.get(0).getHeader().getTitle())) {
            result = false;
        }

        return result;
    }

    public static boolean bodyStructureValid(Body _body, List<ChannelData> _channelData) {
        return _body.getSections().size() == _channelData.size();
    }

    public static int getChannelSectionCount(Body _body) {
        return _body.getSections().size();
    }

    public static Section getChannelSection(Body _body, int _index) {
        return (Section) _body.getSections().get(0)/*.getContent().get(_index)*/;
    }

    public static int getInterpreterSectionCount(Body _body, int _channelIndex) {
        return getChannelSection(_body, _channelIndex).getContent().size();
    }

    public static Section getInterpreterSection(Body _body, int _channelIndex, int _interpreterIndex) {
        return (Section) getChannelSection(_body, _channelIndex).getContent().get(_interpreterIndex);
    }

    public static int getDateSectionCount(Body _body, int _channelIndex, int _interpreterIndex) {
        return getInterpreterSection(_body, _channelIndex, _interpreterIndex).getContent().size();
    }

    public static Section getDateSection(Body _body, int _channelIndex, int _interpreterIndex, int _dateIndex) {
        return (Section) getInterpreterSection(_body, _channelIndex, _interpreterIndex).getContent().get(_dateIndex);
    }

    public static int getItemSectionCount(Body _body, int _channelIndex, int _interpreterIndex, int _dateIndex) {
        return getDateSection(_body, _channelIndex, _interpreterIndex, _dateIndex).getContent().size();
    }

    private FlowTextDocumentTestUtils() {
        // empty
    }
}
