package greader.profile;

import util.Assert;
import util.IOTools;
import util.JaxbCodec;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 29.07.2011
 */
public class ProfilesStorage {

    private static final String PROFILES_FILE_NAME = "google-reader-profiles.xml";
    private static final String UTF_8 = "UTF-8";

    private final File root;
    private final JaxbCodec<Profiles> codec;

    public ProfilesStorage(final String _root) throws ProfilesStorageException {

        try {
            Assert.isValidString(_root, "Root is not valid");

            this.root = new File(_root);

            Assert.isTrue(this.root.exists(), "Root not exists");
            Assert.isTrue(this.root.canRead(), "Root not readable");
            Assert.isTrue(this.root.canWrite(), "Root not writable");

            this.codec = new JaxbCodec<Profiles>(Profiles.class);
        } catch (JAXBException e) {
            throw new ProfilesStorageException(e);
        }
    }

    public void store(final Profiles _profiles) throws ProfilesStorageException {
        Assert.notNull(_profiles, "Profiles is null");

        try {
            final String encoded = this.codec.encode(_profiles);

            IOTools.writeFile(createProfilesFile(), encoded, UTF_8);
        } catch (Exception e) {
            throw new ProfilesStorageException(e);
        }
    }

    public Profiles load() throws ProfilesStorageException {

        try {
            File profilesFile = createProfilesFile();
            
            return profilesFile.exists() ? this.codec.decode(IOTools.readFile(profilesFile, UTF_8)) : new Profiles();
        } catch (Exception e) {
            throw new ProfilesStorageException(e);
        }
    }

    private File createProfilesFile() {
        return new File(this.root, PROFILES_FILE_NAME);
    }

    public class ProfilesStorageException extends Exception {

        public ProfilesStorageException(Throwable cause) {
            super(cause);
        }
    }
}
