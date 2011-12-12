package app.iui.command;

import app.api.ApiFacade;
import app.iui.entity.Entity;
import app.metadata.ObjectMetaData;
import app.metadata.ObjectMetaDataTools;
import constructor.dom.locator.Mask;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class LoadFeedersCommand implements Command {

    private final ApiFacade api;
    private final Listener listener;

    private List<Entity> entities;

    public LoadFeedersCommand(final ApiFacade _api, final Listener _listener) {
        Assert.notNull(_api, "API is null");
        this.api = _api;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        this.entities = newArrayList();
    }

    public String toString() {
        return "iui_LoadFeeders()";
    }

    public void execute() {

        try {
            this.entities = getKeyEntities();

            this.listener.complete(this);
        } catch (ApiFacade.FatalException e) {
            this.listener.fault(this, e);
        }
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    private List<Entity> getKeyEntities() throws ApiFacade.FatalException {
        List<Entity> result = newArrayList();
        List<ObjectMetaData> metaDatas = this.api.getObjectsMetaData();

        parseOutputs(result, metaDatas);
        parseSimplers(result, metaDatas);

        return result;
    }

    private void parseSimplers(List<Entity> result, List<ObjectMetaData> metaDatas) throws ApiFacade.FatalException {
        List<String> simplers = this.api.getSimplersNames(Mask.BYPASS);

        for (String simpler : simplers) {
            Entity entity = ObjectMetaDataTools.getEntityFromSimpler(simpler, metaDatas);

            if (entity != null) {
                result.add(entity);
            }
        }
    }

    private void parseOutputs(List<Entity> result, List<ObjectMetaData> metaDatas) throws ApiFacade.FatalException {
        List<String> outputs = this.api.getOutputsNames(Mask.BYPASS);

        for (String output : outputs) {
            Entity entity = ObjectMetaDataTools.getEntityFromOutput(output, metaDatas);

            if (entity != null) {
                result.add(entity);
            }
        }
    }

    public interface Listener {
        void complete(LoadFeedersCommand _command);

        void fault(LoadFeedersCommand _command, Throwable _cause);
    }
}