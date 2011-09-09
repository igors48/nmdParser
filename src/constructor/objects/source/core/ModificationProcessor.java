package constructor.objects.source.core;

import dated.item.modification.Modification;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 21.11.2008
 */
public interface ModificationProcessor {

    List<Modification> process(List<Modification> _modifications) throws ModificationProcessorException;

    public class ModificationProcessorException extends Exception {

        public ModificationProcessorException() {
        }

        public ModificationProcessorException(String s) {
            super(s);
        }

        public ModificationProcessorException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ModificationProcessorException(Throwable throwable) {
            super(throwable);
        }
    }
}
