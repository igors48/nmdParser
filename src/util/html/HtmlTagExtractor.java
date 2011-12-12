package util.html;

import util.Assert;

import javax.swing.text.html.HTMLEditorKit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.CollectionUtils.newArrayList;
import static util.html.HtmlUtils.CLOSE_TAG_PATTERN;
import static util.html.HtmlUtils.OPEN_TAG_PATTERN;

/**
 * Утилитный класс содержит функцию извлечения тэга из документа
 *
 * @author Igor Usenko
 *         Date: 11.03.2009
 */
public class HtmlTagExtractor extends HTMLEditorKit.ParserCallback {


    /**
     * Возвращает тэг и его начальную и конечную позиции в документе. В случае
     * вложенных тегов - вернет все. Первым в списке будет самый внешний.
     * Последним - самый внутренний
     *
     * @param _pattern шаблон в формате регулярного выражения описывающий начало тэга
     * @param _image   образ документа
     * @return список экземпляров HtmlTagBounds, содержащих тэг и его начальную и конечную позицию в документе
     *         или null если не получилось
     */
    public static List<HtmlTagBounds> extractTag(final String _pattern, final String _image) {
        Assert.isValidString(_pattern);
        Assert.isValidString(_image);

        List<HtmlTagBounds> result = newArrayList();

        Pattern pattern = Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(_image);

        while (matcher.find()) {
            int start = matcher.start();
            HtmlTagBounds occurrence = getOccurrence(start, _pattern, _image);

            if (occurrence != null) {
                result.add(occurrence);
            }
        }

        return result;
    }

    private static HtmlTagBounds getOccurrence(final int _start, final String _pattern, final String _image) {
        HtmlTagBounds result = null;

        String workCopy = _image.substring(_start);

        Matcher matcher = OPEN_TAG_PATTERN.matcher(/*_pattern*/workCopy);

        if (matcher.find()) {
            String targetTag = matcher.group(1).toUpperCase();

            // поиск первого вхождения целевого тега
            int start = 0;
            TagImage openTag = getClosestOpenTag(workCopy, start);

            while (openTag != null) {
                start = openTag.getPosition() + openTag.getImage().length();

                if (openTag.getTag().equalsIgnoreCase(targetTag)) {
                    break;
                }
            }

            // теперь анализируем то, что осталось
            if (openTag != null) {
                int occurrences = 1;
                TagImage closeTag = null;

                TagImage playingTag = getPlayingTag(workCopy, start);

                while (playingTag != null) {
                    start = playingTag.getPosition() + playingTag.getImage().length();

                    if (playingTag.getTag().equalsIgnoreCase(targetTag)) {
                        occurrences = playingTag.isOpen() ? ++occurrences : --occurrences;
                    }

                    if (occurrences == 0) {
                        closeTag = playingTag;

                        break;
                    }

                    playingTag = getPlayingTag(workCopy, start);
                }

                if (closeTag != null) {
                    result = new HtmlTagBounds(openTag,
                            openTag.getPosition() + _start,
                            closeTag.getPosition() + closeTag.getImage().length() + _start,
                            openTag.getPosition() + openTag.getImage().length() + _start,
                            closeTag.getImage().length());
                } else {
                    result = new HtmlTagBounds(openTag,
                            openTag.getPosition() + _start,
                            _image.length(),
                            openTag.getPosition() + openTag.getImage().length() + _start,
                            0);
                }
            }

        }

        return result;
    }

    public static TagImage getPlayingTag(final String _image, final int _from) {
        TagImage result = null;

        TagImage openTag = getClosestOpenTag(_image, _from);
        TagImage closeTag = getClosestCloseTag(_image, _from);

        if (openTag != null && closeTag == null) {
            result = openTag;
        }

        if (openTag == null && closeTag != null) {
            result = closeTag;
        }

        if (openTag != null && closeTag != null) {

            if (openTag.getPosition() < closeTag.getPosition()) {
                result = openTag;
            } else {
                result = closeTag;
            }
        }

        return result;
    }

    public static TagImage getClosestOpenTag(final String _image, final int _from) {
        TagImage result = null;

        String workingCopy = _image.substring(_from);

        Matcher matcher = OPEN_TAG_PATTERN.matcher(workingCopy);

        while (matcher.find() && result == null) {
            String tag = matcher.group(1);

            if (!isSimpleTag(tag)) {
                String image = matcher.group(0);
                String attributes = matcher.group(2);
                int pos = _image.indexOf(image, _from);

                result = new TagImage(pos, tag, attributes, image, true);
            }
        }

        return result;
    }

    public static TagImage getClosestCloseTag(final String _image, final int _from) {
        TagImage result = null;

        String workingCopy = _image.substring(_from);

        Matcher matcher = CLOSE_TAG_PATTERN.matcher(workingCopy);

        if (matcher.find()) {
            String tag = matcher.group(1);
            String image = matcher.group(0);
            int pos = _image.indexOf(image, _from);

            result = new TagImage(pos, tag, "", image, false);
        }

        return result;
    }

    private static boolean isSimpleTag(final String _tag) {
        return false;
    }

    private HtmlTagExtractor() {
        // empty
    }

}
