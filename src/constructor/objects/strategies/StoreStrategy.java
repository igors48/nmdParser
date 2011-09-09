package constructor.objects.strategies;

import dated.Dated;

/**
 * Стратегия определния срока хранения датированных элементов
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public interface StoreStrategy {

    /**
     * Определяет необходимость дальнейшего хранения датированного элемента
     *
     * @param _dated датированный элемент
     * @return true если нужно хранить и дальше false если можно не хранить
     */
    boolean accepted(Dated _dated);
}
