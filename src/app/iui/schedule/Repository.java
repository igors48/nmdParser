package app.iui.schedule;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public interface Repository {

    void store(ScheduleAdapter _schedule) throws RepositoryException;

    ScheduleAdapter load() throws RepositoryException;

    public class RepositoryException extends Exception {

        public RepositoryException() {
        }

        public RepositoryException(final String _s) {
            super(_s);
        }

        public RepositoryException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public RepositoryException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
