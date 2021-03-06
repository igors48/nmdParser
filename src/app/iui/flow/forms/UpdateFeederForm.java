package app.iui.flow.forms;

import app.iui.ApiService;
import app.iui.StringResource;
import app.iui.command.CommandExecutor;
import app.iui.command.UpdateFeederCommand;
import app.iui.flow.AbstractForm;
import app.iui.flow.custom.SingleProcessInfo;
import app.iui.flow.models.UpdateFeederModel;
import app.iui.tools.SwingTools;
import net.miginfocom.swing.MigLayout;
import util.Assert;
import util.TimeTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class UpdateFeederForm extends AbstractForm implements UpdateFeederCommand.Listener, ActionListener {

    private static final String CAPTION = "iui.update.feeder.form.caption";
    private static final String PREPAIRING = "iui.update.feeder.prepairing.caption";
    private static final String CANCEL = "iui.update.feeders.from.cancel.button.label";
    private static final String CANCELLING = "iui.update.feeders.from.canceling.button.label";
    private static final String ELAPSED = "iui.update.feeders.from.elapsed.time.label";
    private static final String FEEDER = "iui.update.feeders.from.feeder.label";

    private static final String PROCESS_DIALOG_ELAPSED_TIMER_NAME = "ElapsedTimer";
    private static final int ELAPSED_TIMER_PERIOD = 500;

    private static final String HTML_H2_PREFIX = "<html><h2>";
    private static final String H2_HTML_POSTFIX = "</h2></html>";

    private final UpdateFeederModel model;
    private final CommandExecutor executor;
    private final ApiService service;
    private final Listener listener;

    private final AtomicBoolean cancelled;

    private JLabel feederLabel;
    private JLabel progressLabel;
    private JProgressBar progressBar;
    private JLabel elapsedTimeLabel;
    private JButton cancelButton;

    private long startTime;
    private java.util.Timer timer;

    private class ElapsedTimerTask extends TimerTask {

        public void run() {
            SwingTools.invokeRunnable(new Runnable() {
                public void run() {
                    onElapsedTimer();
                }
            }, log);
        }
    }

    public UpdateFeederForm(final JFrame _owner,
                            final int _width,
                            final int _height,
                            final StringResource _stringResource,
                            final UpdateFeederModel _model,
                            final ApiService _service,
                            final CommandExecutor _executor,
                            final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_service, "API service is null");
        this.service = _service;

        Assert.notNull(_executor, "Executor is null");
        this.executor = _executor;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        this.cancelled = new AtomicBoolean(false);

        this.timer = new java.util.Timer(PROCESS_DIALOG_ELAPSED_TIMER_NAME, true);

        getContentPanel().setLayout(new MigLayout());

        setCaption(getString(CAPTION));

        updateFeeder();
    }

    protected void onApprove() {
        // empty
    }

    protected void onDiscard() {
        cancelUpdate();
    }

    public void complete(final UpdateFeederCommand _command) {
        Assert.notNull(_command, "Command is null");

        this.model.approve();
        this.model.setFiles(_command.getFiles());

        stopElapsedTimer();

        this.listener.submit(this.model);
    }

    public void fault(final UpdateFeederCommand _command, final Throwable _cause) {
        Assert.notNull(_command, "Command is null");

        this.model.setCause(_cause);
        this.model.reject();

        stopElapsedTimer();

        this.listener.submit(this.model);
    }

    public void progress(final UpdateFeederCommand _command, final SingleProcessInfo _info) {
        Assert.notNull(_command, "Command is null");

        SwingTools.invokeRunnable(
                new Runnable() {
                    public void run() {
                        showUpdateProgress(_info);
                    }
                }
                , log);
    }

    public boolean isCancelled() {
        return this.cancelled.get();
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.cancelButton) {
            cancelUpdate();
        }
    }

    private void onElapsedTimer() {
        this.elapsedTimeLabel.setText(MessageFormat.format(getString(ELAPSED), TimeTools.format(System.currentTimeMillis() - this.startTime)));
    }

    private void startElapsedTimer() {
        this.startTime = System.currentTimeMillis();

        this.timer.schedule(new ElapsedTimerTask(), ELAPSED_TIMER_PERIOD, ELAPSED_TIMER_PERIOD);
    }

    private void stopElapsedTimer() {
        this.timer.cancel();
    }

    private void cancelUpdate() {
        this.cancelled.set(true);
        this.cancelButton.setText(getString(CANCELLING));
    }

    private void updateFeeder() {
        setupControlsForUpdate();

        startElapsedTimer();

        this.executor.execute(this.service.updateFeeder(this.model.getFeeder(), this.model.getForcedPeriod(), this.model.getContext(), this), true);
    }

    private void setupControlsForUpdate() {
        this.feederLabel = new JLabel(MessageFormat.format(getString(FEEDER), this.model.getFeeder().getName()));
        getContentPanel().add(this.feederLabel, createConstraint().maximumWidth().wrap().toString());

        this.progressLabel = new JLabel(getString(PREPAIRING));
        getContentPanel().add(this.progressLabel, createConstraint().maximumWidth().wrap().toString());

        this.progressBar = new JProgressBar();
        getContentPanel().add(this.progressBar, createConstraint().maximumWidth().progressBarHeight().verticalGap().wrap().toString());

        this.elapsedTimeLabel = new JLabel(" ");
        getContentPanel().add(this.elapsedTimeLabel, createConstraint().alignCenter().verticalGap().wrap().toString());

        this.cancelButton = new JButton(getString(CANCEL));
        this.cancelButton.addActionListener(this);
        getContentPanel().add(this.cancelButton, createConstraint().largeButton().alignCenter().verticalGap().toString());
        setDefaultButton(this.cancelButton);
    }

    private void showUpdateProgress(final SingleProcessInfo _info) {
        progressLabel.setText(/*HTML_H2_PREFIX + */getString(_info.getCode())/* + H2_HTML_POSTFIX*/);

        if (_info.isIndeterminate()) {
            progressBar.setIndeterminate(true);

            progressBar.setStringPainted(false);
            progressBar.setMinimum(0);
            progressBar.setMaximum(0);
        } else {
            progressBar.setIndeterminate(false);

            progressBar.setStringPainted(true);
            progressBar.setMinimum(0);
            progressBar.setMaximum(_info.getTotal());
            progressBar.setValue(_info.getCurrent());
        }
    }

    public interface Listener {
        void submit(UpdateFeederModel _model);
    }
}
