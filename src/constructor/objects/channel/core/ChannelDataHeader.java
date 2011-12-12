package constructor.objects.channel.core;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 31.12.2008
 */
public class ChannelDataHeader {

    private final String title;
    private final String firstName;
    private final String lastName;

    private final String sourceUrl;
    private final String coverUrl;

    private List<String> genres;
    private String lang;

    public ChannelDataHeader(final String _title, final String _firstName, final String _lastName, final String _sourceUrl, final String _coverUrl, final List<String> _genres, final String _lang) {
        Assert.notNull(_title, "Title is null");
        this.title = _title;

        Assert.notNull(_firstName, "First name is null");
        this.firstName = _firstName;

        Assert.notNull(_lastName, "Last name is null");
        this.lastName = _lastName;

        Assert.notNull(_sourceUrl, "Source URL is null");
        this.sourceUrl = _sourceUrl;

        Assert.notNull(_coverUrl, "Cover URL is null");
        this.coverUrl = _coverUrl;

        Assert.notNull(_genres, "Genres list is null");
        this.genres = _genres;

        Assert.notNull(_lang, "Lang is null");
        this.lang = _lang;
    }

    public ChannelDataHeader(final String _title, final String _firstName, final String _lastName, final String _sourceUrl, final String _coverUrl) {
        Assert.notNull(_title, "Title is null");
        this.title = _title;

        Assert.notNull(_firstName, "First name is null");
        this.firstName = _firstName;

        Assert.notNull(_lastName, "Last name is null");
        this.lastName = _lastName;

        Assert.notNull(_sourceUrl, "Source URL is null");
        this.sourceUrl = _sourceUrl;

        Assert.notNull(_coverUrl, "Cover URL is null");
        this.coverUrl = _coverUrl;

        this.genres = ChannelDataTools.DEFAULT_GENRES;
        this.lang = ChannelDataTools.DEFAULT_LANG;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getGenres() {

        if (this.genres == null || this.genres.isEmpty()) {
            genres = newArrayList();
            genres.add(ChannelDataTools.DEFAULT_GENRE);
        }

        return this.genres;
    }

    public String getLang() {

        if (this.lang == null || this.lang.isEmpty()) {
            this.lang = ChannelDataTools.DEFAULT_LANG;
        }

        return this.lang;
    }

    public void setGenres(final List<String> _genres) {
        Assert.notNull(_genres, "Genres is null");
        this.genres = _genres;
    }

    public void setLang(final String _lang) {
        Assert.isValidString(_lang, "Language value is not valid");
        this.lang = _lang;
    }
}
