package flowtext;

import util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Заголовок документа
 *
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Header {

    private final List<String> genres;
    private final String authorFirstName;
    private final String authorLastName;
    private final String bookTitle;
    private final String programUsed;
    private final String lang;
    private final String coverUrl;

    private final Date date;
    private final String id;
    private final String version;

    public Header(final String _authorFirstName, final String _authorLastName, final String _bookTitle, final String _coverUrl, final Date _date, final List<String> _genres, final String _id, final String _lang, final String _programUsed, final String _version) {
        Assert.notNull(_genres, "Genres list is null");
        Assert.isValidString(_authorFirstName, "First Name is not valid");
        Assert.isValidString(_authorLastName, "Last Name is not valid");
        Assert.isValidString(_bookTitle, "Book title is not valid");
        Assert.notNull(_coverUrl, "Cover URL is null");
        Assert.isValidString(_programUsed, "ProgramUsed is not valid");
        Assert.isValidString(_id, "Id is not valid");
        Assert.isValidString(_lang, "Lang is not valid");
        Assert.isValidString(_version, "Version is not valid");
        Assert.notNull(_date, "Date is null");

        this.authorFirstName = _authorFirstName;
        this.authorLastName = _authorLastName;
        this.bookTitle = _bookTitle;
        this.coverUrl = _coverUrl;
        this.date = _date;
        this.genres = _genres;
        this.id = _id;
        this.lang = _lang;
        this.programUsed = _programUsed;
        this.version = _version;
    }

    public String getAuthorFirstName() {
        return this.authorFirstName;
    }

    public String getAuthorLastName() {
        return this.authorLastName;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public Date getDate() {
        return this.date;
    }

    public List<String> getGenres() {
        return this.genres;
    }

    public String getId() {
        return this.id;
    }

    public String getLang() {
        return this.lang;
    }

    public String getProgramUsed() {
        return this.programUsed;
    }

    public String getVersion() {
        return this.version;
    }
}
