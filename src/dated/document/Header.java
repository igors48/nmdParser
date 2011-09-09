package dated.document;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 09.10.2008
 */
public class Header {

    private final String authorFirstName;
    private final String authorLastName;
    private final String bookTitle;

    public Header(String _bookTitle, String _authorFirstName, String _authorLastName) {
        Assert.isValidString(_bookTitle);
        Assert.isValidString(_authorFirstName);
        Assert.isValidString(_authorLastName);

        this.bookTitle = _bookTitle;
        this.authorFirstName = _authorFirstName;
        this.authorLastName = _authorLastName;
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
}