package greader;

import app.cli.blitz.BlitzRequestHandler;
import greader.profile.Profiles;

import java.util.List;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public interface GoogleReaderAdapter {

    void createProfile(String _email, String _password) throws GoogleReaderAdapterException;

    void removeProfile(String _email) throws GoogleReaderAdapterException;

    List<String> updateProfile(String _email, BlitzRequestHandler _handler) throws GoogleReaderAdapterException;

    List<String> testProfileFeed(String _email, String _feedUrl, BlitzRequestHandler _handler) throws GoogleReaderAdapterException;

    void changeProfilePassword(String _email, String _newPassword) throws GoogleReaderAdapterException;

    Profiles getRegisteredProfiles() throws GoogleReaderAdapterException;

    class GoogleReaderAdapterException extends Exception {

        public GoogleReaderAdapterException() {
        }

        public GoogleReaderAdapterException(final Throwable _cause) {
            super(_cause);
        }

        public GoogleReaderAdapterException(final String _message) {
            super(_message);
        }

        public GoogleReaderAdapterException(final String _message, final Throwable _cause) {
            super(_message, _cause);
        }
    }

}
