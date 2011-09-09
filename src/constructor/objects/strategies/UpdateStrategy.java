package constructor.objects.strategies;

/**
 * Стратегия проведения обновления
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public interface UpdateStrategy {

    /**
     * Возвращает "необходимость" обновления
     *
     * @return true если обновление необходимо false если нет
     */
    boolean needsUpdate();
}
