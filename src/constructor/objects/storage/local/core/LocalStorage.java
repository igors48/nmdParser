package constructor.objects.storage.local.core;

import constructor.objects.output.core.ForEachPostProcessor;
import constructor.objects.storage.Storage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;
import util.PathTools;

import java.io.File;

//todo Все-таки должна быть нотификация, что итем сохранен или как-то. Потому как возможны очень дурацкие ситуации.

/**
 * Локальное хранилище файлов.
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public class LocalStorage implements Storage {

    public static final String DEFAULT_STORAGE_ID = "*default*";

    private static final String DIVIDER = "/";
    private static final String BRANCH_DIVIDER = ".";

    private String id;
    private String root;
    private Handler handler;
    private TimeService timeService;
    private long fileAge;

    private boolean started;

    private final Log log;

    public LocalStorage() {
        this.started = false;

        this.log = LogFactory.getLog(getClass());
    }

    public void setHandler(final Handler _handler) {
        Assert.notNull(_handler, "Handler is null.");
        this.handler = _handler;
    }

    public void setTimeService(final TimeService _timeService) {
        Assert.notNull(_timeService, "Time service is null");
        this.timeService = _timeService;
    }

    public void setFileAge(final long _age) {
        Assert.greaterOrEqual(_age, 0, "Local storage file age < 0");
        this.fileAge = _age;
    }

    public void configureRoot(final String _root) {
        Assert.isValidString(_root);
        this.root = PathTools.normalize(_root);
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Storage id is not valid.");
        this.id = _id;
    }

    public void close() {

        this.log.trace("in close");

        if (isStarted()) {
            setStarted(false);
        }
    }

    public void open() throws StorageException {

        try {

            if (!isStarted()) {

                if (!this.handler.directoryExists(this.root)) {
                    this.handler.createDirectory(this.root);
                }

                setStarted(true);
                clean();
            }
        } catch (Handler.HandlerException e) {
            throw new StorageException("Error opening Storage [ " + this.id + " ] :", e);
        }
    }

    public synchronized String store(final StoreItem _item, final ForEachPostProcessor _postProcessor)
            throws StorageException {
        Assert.notNull(_item, "Store item is null");
        Assert.notNull(_postProcessor, "For each postprocessor is null");

        if (!isStarted()) {
            throw new StorageException("Storage [ " + this.id + " ] did not started.");
        }

        return doStore(_item, _postProcessor);
    }

    public synchronized void remove(final String _branch, final String _fileName) throws StorageException {
        Assert.isValidString(_branch, "Branch is not valid");
        Assert.isValidString(_fileName, "File name is not valid");

        try {

            if (!isStarted()) {
                throw new StorageException("Storage [ " + this.id + " ] did not started.");
            }

            String branchPath = createBranchPath(_branch);

            if (this.handler.directoryExists(branchPath)) {
                this.handler.deleteFile(branchPath, _fileName);
            } else {
                throw new StorageException("Storage [ " + this.id + " ]. Can`t find branch [ " + _branch + " ] ");
            }
        } catch (Handler.HandlerException e) {
            throw new StorageException("Storage [ " + this.id + " ]. Can`t delete file [ " + _fileName + " ] from branch [ " + _branch + " ] ", e);
        }
    }

    private void clean() {
        this.log.debug("Storage cleaning started.");

        if (this.fileAge > 0) {
            long fileAge = this.timeService.getCurrentTime() - this.fileAge;
            this.handler.clean(this.root, fileAge);
        }

        this.log.debug("Storage cleaning ended.");
    }

    private String doStore(final StoreItem _item, final ForEachPostProcessor _postProcessor) {
        String name = "";

        try {
            String branchPath = createBranchPath(_item.getBranch());

            if (!this.handler.directoryExists(branchPath)) {
                this.handler.createDirectory(branchPath);
            }

            if (_item.getSize() < getFreeSpace(branchPath)) {
                String storeName = createName(_item.getName());

                if (this.handler.fileExists(branchPath, storeName)) {

                    if (_item.isRemoveExists()) {
                        this.handler.deleteFile(branchPath, storeName);
                    } else {
                        this.handler.renameExistentFile(branchPath, storeName);
                    }
                }

                this.handler.storeFile(branchPath, storeName, _item.getName());

                String fullBranchPath = (new File(branchPath)).getAbsolutePath();

                name = new File(fullBranchPath, storeName).getAbsolutePath();

                this.log.info("Item stored. Storage [ " + this.id + " ] Branch [ " + _item.getBranch()
                        + " ] File name [ " + name + " ] Size [ " + _item.getSize() + " ] bytes.");

                _postProcessor.process(fullBranchPath, storeName);

                if (_item.isRemove()) {

                    if (_item.remove()) {
                        this.log.info("Source file [ " + _item.getName() + " ] successfully deleted.");
                    } else {
                        this.log.error("Source file [ " + _item.getName() + " ] deletion error.");
                    }
                }

            } else {
                this.log.info("Item size [ " + _item.getSize() + " ] is greater then Storage [ " + this.id + " ] free space [ " + getFreeSpace(branchPath) + " ].");
            }

        } catch (Handler.HandlerException e) {
            this.log.error("Error storing file [ " + _item.getName()
                    + "] in storage [ " + this.id + " ] branch [ " + _item.getBranch() + " ].", e);
        }

        return name;
    }

    private long getFreeSpace(final String _branchPath) {
        return this.handler.freeSpace(_branchPath);
    }

    private String createName(final String _original) {
        return (new File(_original)).getName();
    }

    private String createBranchPath(final String _branch) {
        return this.root + normalizeBranch(_branch);
    }

    private String normalizePath(final String _path) {
        String result = _path;

        if (!_path.endsWith(DIVIDER)) {
            result += DIVIDER;
        }

        return result;
    }

    private String normalizeBranch(final String _path) {
        String result = _path.replace(BRANCH_DIVIDER, DIVIDER);

        if (result.startsWith(DIVIDER)) {
            result = result.substring(1);
        }

        return normalizePath(result);
    }

    private synchronized boolean isStarted() {
        return this.started;
    }

    private synchronized void setStarted(final boolean _started) {
        this.started = _started;

        this.log.info(this.started ? "Storage [ " + this.id + " ] started" : "Storage [ " + this.id + " ] stopped");
    }

}
