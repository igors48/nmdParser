package constructor.objects.channel.core.analyser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Утилиты для анализатора канала
 *
 * @author Igor Usenko
 *         Date: 06.08.2009
 */
public final class ChannelAnalyserTools {

    public static final String DEFAULT_LAST_NAME = "nmd";
    public static final String TITLE_PATTERN = "<title>(.+?)</title>";
    public static final int TITLE_GROUP = 1;

    //todo где-то уже было
    public static String getHostName(final String _url) {
        String result = " ";

        try {
            URL url = new URL(_url);
            result = url.getHost();
        } catch (MalformedURLException e) {
            // empty
        }

        return result;
    }

    public static String parseTitle(String _pageImage) {
        return (new GetGroup(TITLE_PATTERN, TITLE_GROUP).process(_pageImage)).trim();
    }

    private ChannelAnalyserTools() {
        // empty            
    }
}
