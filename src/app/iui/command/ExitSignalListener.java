package app.iui.command;

/**
 * ��������� ������� ����� ��� ����������
 *
 * @author Igor Usenko
 *         Date: 13.04.2010
 */
public interface ExitSignalListener {

    /**
     * ����������� � �������������� ��������
     */
    void onNormalExit();

    /**
     * ����������� �� ��������� ��������
     */
    void onFailureExit(Throwable _cause);

}
