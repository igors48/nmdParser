package app.iui.validator;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 31.10.2010
 */
public class Validators {

    private final List<Validator> validators;

    public Validators() {
        this.validators = new ArrayList<Validator>();
    }

    public void addValidator(final Validator _validator) {
        Assert.notNull(_validator, "Validator is null");

        this.validators.add(_validator);
    }

    public String valid(final String _data) {
        String result = "";

        for (Validator current : this.validators) {

            if (!current.valid(_data)) {
                result = current.getMessage();
                break;
            }
        }

        return result;
    }
}
