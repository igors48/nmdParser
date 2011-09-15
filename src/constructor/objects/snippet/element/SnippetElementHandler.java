package constructor.objects.snippet.element;

import constructor.dom.loader.MappedElementHandler;

/**
 * ���������� �������� snippet � XML �����
 *
 * @author Igor Usenko
 *         Date: 09.12.2009
 */
public class SnippetElementHandler extends MappedElementHandler {

    private static final String NO_RESOLVE_IMAGE_LINKS_KEY = "noResolveImageLinks";
    private static final String NO_LINKS_AS_FOOTNOTES_KEY = "noLinksAsFootnotes";
    private static final String NO_REMOVE_EXISTS_KEY = "noRemoveExists";
    private static final String FOR_EACH_KEY = "forEach";
    private static final String STORAGE_KEY = "storage";
    private static final String MANY_TO_ONE_KEY = "many-to-one";
    private static final String BRANCH_KEY = "branch";
    private static final String RSS_KEY = "rss";
    private static final String GENRE_KEY = "genre";
    private static final String BASE_KEY = "base";
    private static final String URL_KEY = "url";
    private static final String CONTENT_FILTER_KEY = "content-filter";
    private static final String XPATH_KEY = "xPath";
    private static final String REGEXP_KEY = "regExp";
    private static final String FORCED_KEY = "forced";
    private static final String LANG_KEY = "lang";
    private static final String FROM_NEW_TO_OLD_KEY = "fromNewToOld";
    private static final String FROM_OLD_TO_NEW_KEY = "fromOldToNew";
    private static final String COVER_KEY = "cover";

    public SnippetElementHandler() {
        super();

        this.elementHandlers.put(NO_RESOLVE_IMAGE_LINKS_KEY, new NoResolveImageLinksElementHandler());
        this.elementHandlers.put(NO_LINKS_AS_FOOTNOTES_KEY, new NoLinksAsFootnotesElementHandler());
        this.elementHandlers.put(NO_REMOVE_EXISTS_KEY, new NoRemoveExistsElementHandler());
        this.elementHandlers.put(FOR_EACH_KEY, new ForEachElementHandler());
        this.elementHandlers.put(STORAGE_KEY, new StorageElementHandler());
        this.elementHandlers.put(MANY_TO_ONE_KEY, new ManyToOneElementHandler());
        this.elementHandlers.put(BRANCH_KEY, new BranchElementHandler());
        this.elementHandlers.put(RSS_KEY, new RssElementHandler());
        this.elementHandlers.put(GENRE_KEY, new GenreElementHandler());
        this.elementHandlers.put(BASE_KEY, new BaseElementHandler());
        this.elementHandlers.put(URL_KEY, new UrlElementHandler());
        this.elementHandlers.put(CONTENT_FILTER_KEY, new ContentFilterElementHandler());
        this.elementHandlers.put(XPATH_KEY, new XPathElementHandler());
        this.elementHandlers.put(REGEXP_KEY, new RegExpElementHandler());
        this.elementHandlers.put(FORCED_KEY, new ForcedElementHandler());
        this.elementHandlers.put(LANG_KEY, new LangElementHandler());
        this.elementHandlers.put(FROM_NEW_TO_OLD_KEY, new FromNewToOldElementHandler());
        this.elementHandlers.put(FROM_OLD_TO_NEW_KEY, new FromOldToNewElementHandler());
        this.elementHandlers.put(COVER_KEY, new CoverElementHandler());
    }
}
