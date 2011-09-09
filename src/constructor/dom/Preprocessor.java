package constructor.dom;

import java.io.InputStream;

/**
 * ��������� ������������� ������ ����� ��������� ��� ������������
 *
 * @author Igor Usenko
 *         Date: 09.10.2009
 */
public interface Preprocessor {

    /**
     * �������������� ����������� ������� ������ ��������� � ���������� ������.
     * ���������� ���������� �����
     *
     * @param _stream �������� �����
     * @return ���������� �����
     * @throws PreprocessorException ���� �� ����������
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
