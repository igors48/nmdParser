package constructor.objects.channel.core.stream;

import constructor.objects.StreamHelperBean;
import constructor.objects.channel.core.ChannelDataHeader;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогаетльный класс для сериализации/десериализации ChannelDataHeader
 *
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class ChannelDataHeaderHelperBean implements StreamHelperBean {

    private String title;
    private String firstName;
    private String lastName;

    private String sourceUrl;
    private String coverUrl;

    private List<String> genres;
    private String lang;

    public ChannelDataHeaderHelperBean() {
        this.genres = new ArrayList<String>();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String _firstName) {
        Assert.notNull(_firstName);
        this.firstName = _firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String _lastName) {
        Assert.notNull(_lastName);
        this.lastName = _lastName;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(final String _sourceUrl) {
        Assert.notNull(_sourceUrl);
        this.sourceUrl = _sourceUrl;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(final String _coverUrl) {
        Assert.notNull(_coverUrl);
        this.coverUrl = _coverUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String _title) {
        Assert.notNull(_title);
        this.title = _title;
    }

    public List<String> getGenres() {
        List<String> result = new ArrayList<String>();
        result.addAll(this.genres);

        return result;
    }

    public void setGenres(final List<String> _genres) {
        Assert.notNull(_genres);

        this.genres = new ArrayList<String>();
        this.genres.addAll(_genres);
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(final String _lang) {
        Assert.notNull(_lang);
        this.lang = _lang;
    }

    public void store(final Object _object) {
        Assert.notNull(_object);
        Assert.isTrue(_object instanceof ChannelDataHeader, "This is not channel data header.");

        ChannelDataHeader header = (ChannelDataHeader) _object;

        setFirstName(header.getFirstName());
        setLastName(header.getLastName());
        setSourceUrl(header.getSourceUrl());
        setCoverUrl(header.getCoverUrl());
        setTitle(header.getTitle());
        setGenres(header.getGenres());
        setLang(header.getLang());
    }

    public Object restore() {
        return new ChannelDataHeader(getTitle(), getFirstName(), getLastName(), getSourceUrl(), getCoverUrl(), getGenres(), getLang());
    }
}
