package app.iui;

/**
 * ������ ��������� �����. ������������ ��� �����������
 *
 * @author Igor Usenko
 *         Date: 12.04.2010
 */
public interface StringResource {

    static final String OK_BUTTON_TITLE_KEY = "button.ok.title.key";
    static final String CANCEL_BUTTON_TITLE_KEY = "button.cancel.title.key";

    /**
     * ���������� ������ �� ���������� ��������������
     *
     * @param _key �������������
     * @return ������ �� �������
     */
    String getString(String _key);
}
