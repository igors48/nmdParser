package constructor.objects.source.core.fetcher;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.source.core.ModificationFetcher;
import dated.item.modification.Modification;
import timeservice.TimeService;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import java.util.ArrayList;
import java.util.List;

/**
 * Определяемый пользователем источник модификаций
 *
 * @author Igor Usenko
 *         Date: 10.09.2009
 */
public class CustomFetcher implements ModificationFetcher {

    private final VariableProcessor processor;
    private final TimeService timeService;

    public CustomFetcher(final VariableProcessor _processor, final TimeService _timeService) {
        Assert.notNull(_processor, "Custom fetcher processor is null");
        this.processor = _processor;

        Assert.notNull(_timeService, "Time service is null");
        this.timeService = _timeService;
    }

    public List<Modification> getModifications() throws ModificationFetcherException {

        try {
            List<Modification> result = new ArrayList<Modification>();
            Variables variables = new Variables();

            this.processor.process(variables);

            VariableIterator iterator = variables.getIterator(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);

            while (iterator.hasNext()) {
                result.add(new Modification(this.timeService.getCurrentDate(), (String) iterator.next()));
            }

            return result;
        } catch (VariableProcessor.VariableProcessorException e) {
            throw new ModificationFetcherException(e);
        }
    }
}
