package app.iui.command;

import app.api.ApiFacade;
import app.controller.Controller;
import app.iui.entity.Entity;
import app.iui.flow.custom.SingleProcessInfo;
import util.Assert;
import util.UpdateContextTools;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class UpdateFeederCommand implements Command, Controller {

    private final ApiFacade api;
    private final int forcedPeriod;
    private final Map<String, String> context;
    private final Entity entity;
    private final ExternalConverterContext externalConverterContext;
    private final Listener listener;

    private List<String> files;

    public UpdateFeederCommand(final ApiFacade _api, final Entity _entity, final int _forcedPeriod, final Map<String, String> _context, final ExternalConverterContext _externalConverterContext, final Listener _listener) {
        Assert.notNull(_api, "Api is null");
        this.api = _api;

        Assert.notNull(_entity, "Entity is null");
        this.entity = _entity;

        Assert.greaterOrEqual(_forcedPeriod, -1, "Forced period < -1");
        this.forcedPeriod = _forcedPeriod;

        Assert.notNull(_context, "Context is null");
        this.context = _context;

        Assert.notNull(_externalConverterContext, "External converter context is null");
        this.externalConverterContext = _externalConverterContext;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        this.files = newArrayList();
    }

    public List<String> getFiles() {
        return this.files;
    }

    public String toString() {
        return MessageFormat.format("iui_UpdateFeeder({0}, {1}, {2})", this.entity.getName(), UpdateContextTools.contextToString(this.context), this.forcedPeriod);
    }

    public void execute() {

        try {
            this.files = this.api.updateFullSet(this.entity.getSourceId(), this.entity.getChannelId(), this.entity.getOutputId(), this.forcedPeriod, this, this.context, this.externalConverterContext);
            this.listener.complete(this);
        } catch (Throwable t) {
            this.listener.fault(this, t);
        }
    }

    public void onStart() {
        // empty
    }

    public void onProgress(final SingleProcessInfo _info) {
        Assert.notNull(_info, "Process info is null");

        this.listener.progress(this, _info);
    }

    public void onComplete() {
        // empty
    }

    public void onFault() {
        // empty
    }

    public void onCancel() {
        // empty
    }

    public boolean isCancelled() {
        return this.listener.isCancelled();
    }

    public interface Listener {
        void complete(UpdateFeederCommand _command);

        void fault(UpdateFeederCommand _command, Throwable _cause);

        void progress(UpdateFeederCommand _command, SingleProcessInfo _info);

        boolean isCancelled();
    }
}
