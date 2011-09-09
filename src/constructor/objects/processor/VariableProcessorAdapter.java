package constructor.objects.processor;

import constructor.objects.ConfigurationException;

/**
 * Интерфейс конфигуратора процессора переменной
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public interface VariableProcessorAdapter {

    /**
     * Возвращает сконфигурированный процессор
     *
     * @return экземпляр процессора
     * @throws constructor.objects.ConfigurationException
     *          если не получилось
     */
    VariableProcessor getProcessor() throws ConfigurationException;
}
