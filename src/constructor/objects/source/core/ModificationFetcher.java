package constructor.objects.source.core;

import dated.item.modification.Modification;

import java.util.List;

/**
 * ��������� ��������� ������ ����������� ������-������
 *
 * @author Igor Usenko
 *         Date: 23.11.2008
 */
public interface ModificationFetcher {

    /**
     * ���������� ������ ���������� �����������
     *
     * @return ������ ���������� �����������
     * @throws ModificationFetcherException ���� �� ����������
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
