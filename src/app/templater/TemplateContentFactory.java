package app.templater;

import util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ������� ����������� �������� �������� �������� ������
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class TemplateContentFactory {

    public static final String RSS_URL_ID = "rss_url";
    public static final String NAME_ID = "name";

    private static final String RSS_URL_HOLDER = PlaceHolderUtils.getPlaceholder(RSS_URL_ID);
    private static final String NAME_HOLDER = PlaceHolderUtils.getPlaceholder(NAME_ID);

    private static final String INDENT = "    ";

    private static List<String> sourceTemplate;

    static {
        sourceTemplate = new ArrayList<String>();
        sourceTemplate.add("<source>");
        sourceTemplate.add(INDENT + "<store days=\"7\"/>");
        sourceTemplate.add(INDENT + "<rss>" + RSS_URL_HOLDER + "</rss>");
        sourceTemplate.add("</source>");
    }

    private static List<String> channelBriefTemplate;

    static {
        channelBriefTemplate = new ArrayList<String>();
        channelBriefTemplate.add("<channel source=\"" + NAME_HOLDER + ".source\"/>");
    }

    private static List<String> channelFullTemplate;

    static {
        channelFullTemplate = new ArrayList<String>();
        channelFullTemplate.add("<channel source=\"" + NAME_HOLDER + ".source\" interpreter=\"" + NAME_HOLDER + ".interpreter\"/>");
    }

    private static List<String> interpreterTemplate;

    static {
        interpreterTemplate = new ArrayList<String>();
        interpreterTemplate.add("<interpreter>");
        interpreterTemplate.add(INDENT + "<fragment content=\"" + NAME_HOLDER + ".content\"/>");
        interpreterTemplate.add("</interpreter>");
    }

    private static List<String> processorTemplate;

    static {
        processorTemplate = new ArrayList<String>();
        processorTemplate.add("<processor>");
        processorTemplate.add(INDENT + "<getGroup>");
        processorTemplate.add(INDENT + INDENT + "<pattern><![CDATA[<body(.+?)</body>]]></pattern> ");
        processorTemplate.add(INDENT + INDENT + "<occurrence>0</occurrence>");
        processorTemplate.add(INDENT + "</getGroup>");
        processorTemplate.add("<!--");
        processorTemplate.add(INDENT + "<xPath>");
        processorTemplate.add(INDENT + INDENT + "<expression>/body</expression>");
        processorTemplate.add(INDENT + "</xPath>");
        processorTemplate.add("-->");
        processorTemplate.add("</processor>");
    }

    private static List<String> outputTemplate;

    static {
        outputTemplate = new ArrayList<String>();
        outputTemplate.add("<output channel=\"" + NAME_HOLDER + ".channel\" branch=\"" + NAME_HOLDER + "\">");
        outputTemplate.add(INDENT + "<many-to-one name=\"" + NAME_HOLDER + "\"/>");
        outputTemplate.add("</output>");
    }

    private static Map<TemplateContentType, List<String>> templatesMap;

    static {
        templatesMap = new HashMap<TemplateContentType, List<String>>();
        templatesMap.put(TemplateContentType.SOURCE, sourceTemplate);
        templatesMap.put(TemplateContentType.BRIEF_CHANNEL, channelBriefTemplate);
        templatesMap.put(TemplateContentType.FULL_CHANNEL, channelFullTemplate);
        templatesMap.put(TemplateContentType.INTERPRETER, interpreterTemplate);
        templatesMap.put(TemplateContentType.CONTENT_PROCESSOR, processorTemplate);
        templatesMap.put(TemplateContentType.OUTPUT, outputTemplate);
    }

    /**
     * ���������� ������ ������� �������� ������ �� ��� ����
     *
     * @param _type ��� �������
     * @return ���������� �������
     */
    public List<String> getTemplateContent(final TemplateContentType _type) {
        Assert.notNull(_type, "Template type is null");

        return templatesMap.get(_type);
    }
}