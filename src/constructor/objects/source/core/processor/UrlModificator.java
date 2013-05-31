package constructor.objects.source.core.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.source.core.ModificationProcessor;
import dated.item.modification.Modification;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Модифицирует урл модификации с помощью процессора переменных
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class UrlModificator implements ModificationProcessor {

    private final VariableProcessor processor;

    public UrlModificator(final VariableProcessor _processor) {
        Assert.notNull(_processor);
        this.processor = _processor;
    }

    public List<Modification> process(List<Modification> _modifications) throws ModificationProcessorException {
        Assert.notNull(_modifications);

        try {
            List<Modification> result = newArrayList();

            for (Modification modification : _modifications) {
                Variables variables = new Variables();

                variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, modification.getUrl());
                this.processor.process(variables);
                VariableIterator iterator = variables.getIterator(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);

                /*
                if (!iterator.hasNext()){
                    throw new ModificationProcessorException("Null result returned from modification processor for url [ " + modification.getUrl() + " ].");
                }
                */

                while (iterator.hasNext()) {
                    String value = (String) iterator.next();

                    if (!value.isEmpty()) {
                        result.add(new Modification(modification.getDate(), value, modification.getTitle(), modification.getDescription()));
                    }
                }
            }

            return result;
        } catch (VariableProcessor.VariableProcessorException e) {
            throw new ModificationProcessorException(e);
        }
    }

}
