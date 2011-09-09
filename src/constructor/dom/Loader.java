package constructor.dom;

import java.io.InputStream;

/**
 * Загрузчик объектов из потока
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Loader {

    /**
     * Загружает объект из потока
     *
     * @param _stream  поток
     * @param _factory фабрика конструкторов может понадобится для создания
     *                 вложенных объектов по ссылке
     * @return созданный объект
     * @throws LoaderException в случае ошибки
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