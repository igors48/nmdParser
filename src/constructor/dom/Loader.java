package constructor.dom;

import java.io.InputStream;

/**
 * ��������� �������� �� ������
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Loader {

    /**
     * ��������� ������ �� ������
     *
     * @param _stream  �����
     * @param _factory ������� ������������� ����� ����������� ��� ��������
     *                 ��������� �������� �� ������
     * @return ��������� ������
     * @throws LoaderException � ������ ������
     */
    Blank load(final InputStream _stream, final ConstructorFactory _factory) throws LoaderException;

    public class LoaderException extends Exception {
        public LoaderException() {
        }

        public LoaderException(String s) {
            super(s);
        }

        public LoaderException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public LoaderException(Throwable throwable) {
            super(throwable);
        }
    }
}