package app.iui.validator;

import app.iui.StringResource;
import app.iui.validator.rules.*;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 04.11.2010
 */
public final class ValidatorFactory {

    public static String FIELD_MUST_NOT_BE_EMPTY = "validation.field.must.not.be.empty";
    public static String FIELD_MUST_CONTAIN_LATIN_SYMBOLS_AND_DIGITS = "validation.field.must.contain.latin.symbols.and.digits";
    public static String FIELD_MUST_CONTAIN_VALID_URL = "validation.field.must.contain.valid.url";
    public static String FIELD_MUST_CONTAIN_EXECUTABLE_FILE_NAME = "validation.field.must.contain.executable.file.name";
    public static String FIELD_MUST_CONTAIN_INTEGER_IN_RANGE = "validation.field.must.contain.integer.in.range";
    public static String FIELD_LENGTH_MUST_BE_IN_RANGE = "validation.field.length.must.be.in.range";
    public static String FIELD_MUST_CONTAIN_VALID_FILE_NAME = "validation.field.must.contain.valid.file.name";

    private static final String LATIN_SYMBOLS_AND_DIGITS_PATTERN = "^[a-zA-Z\\d]+$";
    private static final String VALID_FILE_NAME_PATTERN = "^[^?*:;{}\\/]+$";

    private final StringResource stringResource;

    public ValidatorFactory(final StringResource _stringResource) {
        Assert.notNull(_stringResource, "String resource is null");
        this.stringResource = _stringResource;
    }

    public Validator notEmpty() {
        final Rule rule = new NotEmpty();
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_MUST_NOT_BE_EMPTY));

        return new Validator(rule, message);
    }

    public Validator latinSymbolsAndDigits() {
        final Rule rule = new RegularExpressionMatch(LATIN_SYMBOLS_AND_DIGITS_PATTERN);
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_MUST_CONTAIN_LATIN_SYMBOLS_AND_DIGITS));

        return new Validator(rule, message);
    }

    public Validator validFileName() {
        final Rule rule = new RegularExpressionMatch(VALID_FILE_NAME_PATTERN);
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_MUST_CONTAIN_VALID_FILE_NAME));

        return new Validator(rule, message);
    }

    public Validator urlValid() {
        final Rule rule = new UrlValid();
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_MUST_CONTAIN_VALID_URL));

        return new Validator(rule, message);
    }

    public Validator fileIsExecutable() {
        final Rule rule = new FileIsExecutable();
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_MUST_CONTAIN_EXECUTABLE_FILE_NAME));

        return new Validator(rule, message);
    }

    public Validator integerInRange(final int _min, final int _max) {
        Assert.greaterOrEqual(_max, _min, "Min > Max");

        final Rule rule = new IntegerInRange(_min, _max);
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_MUST_CONTAIN_INTEGER_IN_RANGE));

        return new Validator(rule, message);
    }

    public Validator lengthInRange(final int _min, final int _max) {
        Assert.greaterOrEqual(_max, _min, "Min > Max");

        final Rule rule = new LengthInRange(_min, _max);
        final String message = rule.formatErrorMessage(this.stringResource.getString(FIELD_LENGTH_MUST_BE_IN_RANGE));

        return new Validator(rule, message);
    }
}
