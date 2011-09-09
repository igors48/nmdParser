package downloader.hostadapter;

import downloader.AdapterRequest;
import downloader.Data;
import downloader.Destination;
import downloader.Result;
import downloader.data.DataFile;
import downloader.data.EmptyData;
import downloader.data.MemoryData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class HostAdapterThread extends Thread {

    private final Log log;

    private final AdapterRequest request;

    private Data data;
    private Result result;

    private static final String HOST_ADAPTER_THREAD_NAME = "HostAdapterThread";

    public HostAdapterThread(AdapterRequest _request) {
        super(HOST_ADAPTER_THREAD_NAME);

        Assert.notNull(_request);

        this.request = _request;
        this.data = new EmptyData();
        this.result = Result.OK;

        this.log = LogFactory.getLog(getClass());

        this.log.debug("Created HostAdapterThread for " + this.request.getDescription());
    }

    public Data getData() {
        return this.data;
    }

    public Result getResult() {
        return this.result;
    }

    public long getRequestId() {
        return this.request.getId();
    }

    public String getRequestDescription() {
        return this.request.getDescription();
    }

    public void run() {
        this.log.debug("HostAdapterThread started for " + this.request.getDescription());

        File dataFile = new File(this.request.getAddress());

        if (!dataFile.exists()) {
            this.result = Result.ERROR;
            this.log.error("Can`t find file." + this.request.getDescription());
            return;
        }

        if (this.request.getDestination() == Destination.FILE) {
            this.data = new DataFile(this.request.getAddress());
        }

        if (this.request.getDestination() == Destination.MEMORY) {
            this.data = loadFile(dataFile);
        }

        this.log.debug("HostAdapterThread ended for " + this.request.getDescription() + " Data size : " + this.data.size());
    }

    private Data loadFile(File _dataFile) {
        try {
            RandomAccessFile dataFile = new RandomAccessFile(this.request.getAddress(), "r");

            byte[] result = new byte[(int) dataFile.length()];
            dataFile.readFully(result);

            dataFile.close();

            return new MemoryData(result);
        } catch (Exception e) {
            this.log.error("Error loading file : " + _dataFile.getAbsolutePath(), e);
            this.result = Result.ERROR;
            return new EmptyData();
        }

    }
}
