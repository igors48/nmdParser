package app.workingarea.workspace;

import app.workingarea.ServiceManager;
import app.workingarea.Workspace;
import cloud.FilesPropertiesCloud;
import cloud.PropertiesCloud;
import constructor.dom.*;
import constructor.dom.constructor.StandardComponentFactory;
import constructor.dom.constructor.StandardConstructorFactory;
import constructor.dom.loader.DomStreamLoader;
import constructor.dom.locator.JavaStyleLocator;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.channel.core.storage.ChannelDataListFileStorage;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.source.core.storage.ModificationListFileStorage;
import constructor.objects.storage.Storage;
import constructor.objects.storage.local.configuration.LocalStorageConfiguration;
import constructor.objects.storage.local.core.LocalHandler;
import constructor.objects.storage.local.core.LocalStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.PathTools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Рабочее пространство расположенное в каталоге
 *
 * @author Igor Usenko
 *         Date: 17.04.2009
 */
public class DirectoryWorkspace implements Workspace {

    private static final String LOCATOR_DIRECTORY = "locator/";
    private static final String CLOUD_DIRECTORY = "cloud/";
    private static final String MODIFICATIONS_DIRECTORY = "modifications/";
    private static final String CHANNELS_DIRECTORY = "channels/";

    private final String root;
    private final ServiceManager serviceManager;

    private final Map<String, Storage> storages;

    private Locator locator;
    private Loader loader;
    private ConstructorFactory factory;
    private PropertiesCloud cloud;
    private ModificationListStorage modifications;
    private ChannelDataListStorage channels;

    private final Log log;

    public DirectoryWorkspace(final String _root, final ServiceManager _serviceManager) {
        Assert.isValidString(_root, "Root path is not valid.");
        Assert.isTrue(new File(_root).exists(), "Root path does not exists.");
        this.root = PathTools.normalize(_root);

        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;

        this.storages = new HashMap<String, Storage>();

        this.log = LogFactory.getLog(getClass());
    }

    public Locator getLocator() throws WorkspaceException {

        if (this.locator == null) {
            this.locator = new JavaStyleLocator(getLocatorDirectory());
        }

        return this.locator;
    }

    public ConstructorFactory getConstructorFactory() throws WorkspaceException {

        if (this.factory == null) {
            this.factory = new StandardConstructorFactory(getLocator(), getLoader(), this.serviceManager);
        }

        return this.factory;
    }

    public PropertiesCloud getCloud() throws WorkspaceException {

        if (this.cloud == null) {
            this.cloud = new FilesPropertiesCloud(getCloudDirectory());
        }

        return this.cloud;
    }

    public ModificationListStorage getModificationListStorage() throws WorkspaceException {

        if (this.modifications == null) {
            this.modifications = new ModificationListFileStorage(getModificationsDirectory());
        }

        return this.modifications;
    }

    public ChannelDataListStorage getChannelDataListStorage() throws WorkspaceException {

        if (this.channels == null) {
            this.channels = new ChannelDataListFileStorage(getChannelsDirectory());
        }

        return this.channels;
    }

    public Storage getStorage(final String _id) throws WorkspaceException {
        Assert.isValidString(_id, "Storage id is not valid.");

        Storage result = null;

        try {

            result = this.serviceManager.getDefaultStorage();

            if (!LocalStorage.DEFAULT_STORAGE_ID.equalsIgnoreCase(_id)) {

                if (this.storages.get(_id) == null) {
                    Storage newStoraqe = createStorage(_id);
                    newStoraqe.open();

                    this.storages.put(_id, newStoraqe);
                }

                result = this.storages.get(_id);
            }

        } catch (Constructor.ConstructorException e) {
            this.log.error("Error creating storage [ " + _id + " ]. Default storage will be used.");
            this.log.debug(e);
        } catch (Storage.StorageException e) {
            this.log.error("Error creating storage [ " + _id + " ]. Default storage will be used.");
            this.log.debug(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new WorkspaceException(e);
        }

        return result;
    }

    public void cleanup() {

        for (String name : this.storages.keySet()) {
            closeStorage(this.storages.get(name));
        }
    }

    private void closeStorage(final Storage _storage) {
        _storage.close();
    }

    private Storage createStorage(final String _id) throws WorkspaceException, Constructor.ConstructorException, ServiceManager.ServiceManagerException {
        ConstructorFactory factory = getConstructorFactory();
        Constructor constructor = factory.getConstructor();

        LocalStorageConfiguration configuration = (LocalStorageConfiguration) constructor.create(_id, ObjectType.STORAGE);

        LocalStorage result = new LocalStorage();

        // todo странная последовательность
        result.setId(configuration.getId());
        result.setHandler(new LocalHandler());
        result.setTimeService(this.serviceManager.getTimeService());
        result.configureRoot(configuration.getRoot());
        result.setFileAge(configuration.getAge());

        return result;
    }

    private Loader getLoader() {

        if (this.loader == null) {
            this.loader = new DomStreamLoader(new StandardComponentFactory(this.serviceManager));
        }

        return this.loader;
    }

    private String getLocatorDirectory() {
        return PathTools.normalize(this.root + LOCATOR_DIRECTORY);
    }

    private String getCloudDirectory() {
        return PathTools.normalize(this.root + CLOUD_DIRECTORY);
    }

    private String getModificationsDirectory() {
        return PathTools.normalize(this.root + MODIFICATIONS_DIRECTORY);
    }

    private String getChannelsDirectory() {
        return PathTools.normalize(this.root + CHANNELS_DIRECTORY);
    }

    public static void create(final String _root, final String _name) throws WorkspaceException {
        Assert.isValidString(_root, "Target directory name is not valid.");
        Assert.isValidString(_name, "Workspace name is not valid.");

        String root = PathTools.normalize(_root);

        if (!(new File(root).exists())) {
            throw new WorkspaceException("Target directory [ " + root + " ] does not exists.");
        }

        String work = PathTools.normalize(_root + _name);

        if (new File(work).exists()) {
            throw new WorkspaceException("Workspace directory [ " + work + " ] already exists.");
        }

        makeDirectory(work, "");
        makeDirectory(work, LOCATOR_DIRECTORY);
        makeDirectory(work, CLOUD_DIRECTORY);
        makeDirectory(work, MODIFICATIONS_DIRECTORY);
        makeDirectory(work, CHANNELS_DIRECTORY);
    }

    private static void makeDirectory(final String _root, final String _name) throws WorkspaceException {
        String directory = PathTools.normalize(_root + _name);

        if (!(new File(directory).mkdir())) {
            throw new WorkspaceException("Can not create directory [ " + directory + " ].");
        }
    }
}
