package constructor.objects.source.core;

import dated.item.modification.Modification;

import java.util.List;

/**
 * Интерфейс получения списка модификаций откуда-нибудь
 *
 * @author Igor Usenko
 *         Date: 23.11.2008
 */
public interface ModificationFetcher {

    /**
     * Возвращает список полученных модификаций
     *
     * @return список полученных модификаций
     * @throws ModificationFetcherException если не получилось
     */
    List<Modification> getModifications() throws ModificationFetcherException;

    public class ModificationFetcherException extends Exception {

        public ModificationFetcherException() {
        }

        public ModificationFetcherException(String s) {
            super(s);
        }

        public ModificationFetcherException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ModificationFetcherException(Throwable throwable) {
            super(throwable);
        }
    }
}
