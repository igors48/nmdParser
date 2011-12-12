package app.templater;

import util.Assert;

import java.util.List;

/**
 * Шаблон объекта верхнего уровня
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class Template {
    
    private final String name;
    private final List<String> image;

    public Template(final String _name, final List<String> _image) {
        Assert.isValidString(_name, "Template name is not valid");
        this.name = _name;

        Assert.notNull(_image, "Template image is null");
        this.image = _image;
    }

    public List<String> getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }
    
}
