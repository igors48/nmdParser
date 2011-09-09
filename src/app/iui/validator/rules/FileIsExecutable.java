package app.iui.validator.rules;

import app.iui.validator.AbstractRule;

import java.io.File;

/**
 * @author Igor Usenko
 *         Date: 06.11.2010
 */
public class FileIsExecutable extends AbstractRule {

    protected boolean validate(final String _data) {
        return new File(_data).canExecute();
    }
}
