package app.iui.flow.forms;

import app.iui.ApiService;
import app.iui.StringResource;
import app.iui.command.CommandExecutor;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.LoadingWorkspaceModel;
import app.iui.script.LoadWorkspaceAndFeedersScript;
import util.Assert;

import javax.swing.*;
import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 08.12.2010
 */
public class LoadingWorkspaceForm extends AbstractForm implements LoadWorkspaceAndFeedersScript.Listener {

    private static final String LOADING_LABEL = "iui.choose.feeders.form.loading.label";

    private final LoadingWorkspaceModel model;
    private final Listener listener;

    public LoadingWorkspaceForm(final JFrame _owner,
                                final int _width,
                                final int _height,
                                final StringResource _stringResource,
                                final LoadingWorkspaceModel _model,
                                final ApiService _service,
                                final CommandExecutor _executor,
                                final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        Assert.notNull(_service, "API service is null");
        Assert.notNull(_executor, "Executor is null");

        JLabel loadingLabel = new JLabel(MessageFormat.format(getString(LOADING_LABEL), this.model.getWorkspaceName()));
        getContentPanel().add(loadingLabel, createConstraint().maximumWidth().alignCenter().wrap().toString());

        JProgressBar progressBar = new JProgressBar(0);
        progressBar.setIndeterminate(true);

        getContentPanel().add(progressBar, createConstraint().maximumWidth().alignCenter().progressBarHeight().wrap().toString());

        _executor.execute(_service.loadWorkspaceAndFeeders(this.model.getWorkspaceName(), this), false);
    }

    protected void onApprove() {
        // empty
    }

    protected void onDiscard() {
        // empty
    }

    public void complete(final LoadWorkspaceAndFeedersScript _script) {
        Assert.notNull(_script, "Script is null");

        this.model.approve();
        this.model.setEntities(_script.getEntities());

        this.listener.submit(this.model);
    }

    public void fault(final LoadWorkspaceAndFeedersScript _script, Throwable _cause) {
        Assert.notNull(_script, "Script is null");

        this.model.reject();
        this.model.setCause(_cause);

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(LoadingWorkspaceModel _model);
    }
}
