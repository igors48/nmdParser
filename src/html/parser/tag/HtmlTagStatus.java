package html.parser.tag;

/**
 * ������ ����������� ���� ����� ������� ��� �������
 *
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public enum HtmlTagStatus {
    /**
     * �������� ��� ������
     */
    NORMAL,
    /**
     * ������� ������� �� �������
     */
    MALFORMED,
    /**
     * ����������� ���
     */
    UNKNOWN
}
