package constructor.objects.output.core.forEach;

import constructor.objects.output.core.ForEachPostProcessor;
import util.Assert;

/**
 * Заглушка для ForEachProcessor
 *
 * @author Igor Usenko
 *         Date: 25.11.2009
 */
public class NullForEachPostProcessor implements ForEachPostProcessor {

    public void process(final String _dir, final String _name) {
        Assert.isValidString(_dir, "Directory is not valid");
        Assert.isValidString(_name, "Name is not valid");

        // empty
    }
}
