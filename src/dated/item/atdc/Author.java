package dated.item.atdc;

import util.Assert;

/**
 * Контейнерный класс. Описатель автора
 *
 * @author Igor Usenko
 *         Date: 09.10.2008
 */
public class Author {
    private final String nick;
    private final String info;
    private final String avatar;

    public Author() {
        this("", "", "");
    }

    public Author(String _nick, String _info, String _avatar) {
        Assert.notNull(_nick);
        Assert.notNull(_info);
        Assert.notNull(_avatar);

        this.nick = _nick;
        this.info = _info;
        this.avatar = _avatar;
    }

    public String getInfo() {
        return this.info;
    }

    public String getNick() {
        return this.nick;
    }

    public String getAvatar() {
        return this.avatar;
    }

}