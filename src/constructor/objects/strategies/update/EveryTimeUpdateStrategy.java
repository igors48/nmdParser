package constructor.objects.strategies.update;

import constructor.objects.strategies.UpdateStrategy;

/**
 * ��������� ���������� - "������"
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class EveryTimeUpdateStrategy implements UpdateStrategy {

    public boolean needsUpdate() {
        return true;
    }
}
