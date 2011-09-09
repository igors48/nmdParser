package constructor.dom;

import java.io.InputStream;

/**
 * Интерфейс предобработки потока перед передачей его конструктору
 *
 * @author Igor Usenko
 *         Date: 09.10.2009
 */
public interface Preprocessor {

    /**
     * Руководствуясь собственной логикой вносит изменения в содержимое потока.
     * Возвращает измененный поток
     *
     * @param _stream исходный поток
     * @return измененный поток
     * @throws PreprocessorException если не получилось
     */
    InputStream preprocess(InputStream _stream) throws PreprocessorException;

    public class PreprocessorException extends Exception {

        public PreprocessorException() {
        }

        public PreprocessorException(final String _s) {
            super(_s);
        }

        public PreprocessorException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public PreprocessorException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
