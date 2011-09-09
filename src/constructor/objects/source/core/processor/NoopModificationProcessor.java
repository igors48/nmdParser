package constructor.objects.source.core.processor;

import constructor.objects.source.core.ModificationProcessor;
import dated.item.modification.Modification;
import util.Assert;

import java.util.List;

/**
 * Пустой процессор модификаций
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class NoopModificationProcessor implements ModificationProcessor {

    public List<Modification> process(final List<Modification> _modifications) throws ModificationProcessorException {
        Assert.notNull(_modifications);

        return _modifications;
    }
}
