package constructor.dom.locator;

import util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constructor.dom.locator.MaskUtils.accepted;
import static constructor.dom.locator.MaskUtils.excepted;

/**
 * Маскa отбора объектов по идентификатору
 *
 * @author Igor Usenko
 *         Date: 11.11.2009
 */
public class Mask {

    public static final Mask BYPASS = new Mask(new ArrayList<String>(), new ArrayList<String>(), false);

    private final List<String> accept;
    private final List<String> except;

    private final boolean exactly;

    public Mask(final List<String> _accept, final List<String> _except) {
        this(_accept, _except, false);
    }

    public Mask(final String _accept) {
        this(Arrays.asList(_accept), new ArrayList<String>(), true);
    }

    private Mask(final List<String> _accept, final List<String> _except, final boolean _exactly) {
        Assert.notNull(_accept, "Accept list is null");
        this.accept = _accept;

        Assert.notNull(_except, "Except list is null");
        this.except = _except;

        this.exactly = _exactly;
    }

    public boolean accept(final String _value) {
        Assert.isValidString(_value, "Value is not valid");

        return accepted(_value, this.accept, this.exactly) && !excepted(_value, this.except, this.exactly);
    }
}
