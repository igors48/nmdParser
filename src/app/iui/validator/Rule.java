package app.iui.validator;

/**
 * @author Igor Usenko
 *         Date: 31.10.2010
 */
public interface Rule {

    boolean valid(String _data);

    String formatErrorMessage(String _template);
}
