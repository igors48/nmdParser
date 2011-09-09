package constructor.objects.snippet.adapter;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import static constructor.objects.snippet.adapter.StandardSnippetProcessorAdapterUtils.getPropertiesFileName;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import constructor.objects.snippet.core.SnippetProcessorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;
import util.DateTools;
import util.IOTools;

import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Стандартный адаптер обработчика сниппетов
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public class StandardSnippetProcessorAdapter implements SnippetProcessorAdapter {

    private final String name;
    private final ConstructorFactory factory;
    private final TimeService timeService;

    private final Log log;

    private static final String LAST_UPDATE_TIME_KEY = "last.update.time";
    private static final String LAST_UPDATE_TIME_KEY_READABLE = "last.update.time.readable";

    public StandardSnippetProcessorAdapter(final String _name, final ConstructorFactory _factory, final TimeService _timeService) {
        Assert.isValidString(_name, "Snippet name is not valid");
        Assert.isTrue(new File(_name).exists(), "Snippet file [ " + _name + " ] does not exists");
        this.name = _name;

        Assert.notNull(_factory, "Factory is null");
        this.factory = _factory;

        Assert.notNull(_timeService, "Time service is null");
        this.timeService = _timeService;

        this.log = LogFactory.getLog(getClass());
    }

    public SnippetConfiguration getSnippetConfiguration() throws SnippetProcessorAdapterException {
        InputStream stream = null;

        try {
            stream = new FileInputStream(this.name);
            Constructor constructor = this.factory.getConstructor(stream);

            return (SnippetConfiguration) constructor.create(this.name, ObjectType.SNIPPET);
        } catch (FileNotFoundException e) {
            throw new SnippetProcessorAdapterException(e);
        } catch (Constructor.ConstructorException e) {
            throw new SnippetProcessorAdapterException(e);
        } finally {
            IOTools.close(stream);
        }
    }

    public long getLastUpdateTime() throws SnippetProcessorAdapterException {
        long result = -1;
        InputStream stream = null;

        try {
            stream = new FileInputStream(getPropertiesFileName(this.name));

            Properties properties = new Properties();
            properties.load(stream);

            result = Long.valueOf(properties.getProperty(LAST_UPDATE_TIME_KEY, "-1"));
        } catch (FileNotFoundException e) {
            this.log.warn("Error while retrieving last update time for [ " + this.name + " ]", e);
        } catch (IOException e) {
            this.log.warn("Error while retrieving last update time for [ " + this.name + " ]", e);
        } catch (NumberFormatException e) {
            this.log.warn("Error while retrieving last update time for [ " + this.name + " ]", e);
        } finally {
            IOTools.close(stream);
        }

        return result;
    }

    public void setLastUpdateTime(final long _time) throws SnippetProcessorAdapterException {
        Assert.greaterOrEqual(_time, 0, "Last update time < 0");

        OutputStream stream = null;

        try {
            stream = new FileOutputStream(getPropertiesFileName(this.name));

            Properties properties = new Properties();

            properties.put(LAST_UPDATE_TIME_KEY, String.valueOf(_time));
            properties.put(LAST_UPDATE_TIME_KEY_READABLE, DateTools.formatAsDigits(new Date(_time)));

            properties.store(stream, "This file store last update time for [ " + this.name + " ] snippet. Please don`t change it manually!");

        } catch (FileNotFoundException e) {
            this.log.warn("Error while storing last update time for [ " + this.name + " ]", e);
        } catch (IOException e) {
            this.log.warn("Error while storing last update time for [ " + this.name + " ]", e);
        } finally {
            IOTools.close(stream);
        }
    }

    public TimeService getTimeService() {
        return this.timeService;
    }
}
