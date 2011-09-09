package constructor.objects.strategies.store;

import constructor.objects.strategies.StoreStrategy;
import dated.Dated;

/**
 * ��������� ����������
 *
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public class StoreNeverStrategy implements StoreStrategy {

    public boolean accepted(final Dated _dated) {
        // ����������� true ����� ����������� ������������ ���������� � SourceUtils.java 
        return true;
    }

}
