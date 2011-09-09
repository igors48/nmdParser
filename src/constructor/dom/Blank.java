package constructor.dom;

import java.util.List;

/**
 * ������ �������� ������������ ������� � ���������������
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public interface Blank {

    /**
     * ������������� ������������� �������
     *
     * @param _id �������������
     */
    void setId(String _id);

    /**
     * ���������� ������ ��������������� ������������ ��������
     *
     * @return ������ ���������������
     */
    List<UsedObject> getUsedObjects();
}
