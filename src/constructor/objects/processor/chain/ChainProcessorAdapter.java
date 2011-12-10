package constructor.objects.processor.chain;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.Debug;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;

import java.util.List;

/**
 * Интерфейс адаптера цепного процессора
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public interface ChainProcessorAdapter extends Debug {
    /**
     * Возвращает идентификатор процессора
     *
     * @return идентификатор
     */
    String getId();

    /**
     * Добавляет адаптер процессора в список
     *
     * @param _adapter адаптер
     */
    void addAdapter(VariableProcessorAdapter _adapter);

    /**
     * Возвращает цепочку процессоров
     *
     * @return цепочка
     * @throws constructor.objects.ConfigurationException
     *          если возникли проблемы
     */
    List<VariableProcessor> getProcessors() throws ConfigurationException;

    /**
     * Возвращает флаг отмены работы
     *
     * @return true не продолжать false - продолжать
     */
    boolean isCancelled();
}
