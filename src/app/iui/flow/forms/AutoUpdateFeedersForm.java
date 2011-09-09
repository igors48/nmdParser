package app.iui.flow.forms;

import app.iui.ApiService;
import app.iui.StringResource;
import app.iui.command.CommandExecutor;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.AutoUpdateFeedersModel;
import app.iui.schedule.Item;
import app.iui.schedule.ScheduledItems;
import app.iui.script.LoadWorkspaceAndFeedersScript;
import app.iui.tools.SwingTools;
import util.Assert;
import util.TimeTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.TimerTask;

/**
 * @author Igor Usenko
 *         Date: 07.05.2011
 */
public class AutoUpdateFeedersForm extends AbstractForm implements LoadWorkspaceAndFeedersScript.Listener, ActionListener {

    // если итема не обновлена -- апи не возвращает имя файла

    private static final String CAPTION = "iui.auto.update.feeders.form.caption";
    private static final String ELAPSED = "iui.auto.update.feeders.form.elapsed.time.label";
    private static final String OVERALL = "iui.auto.update.feeders.form.overall.progress.label";
    private static final String WORKSPACE = "iui.auto.update.feeders.form.current.workspace.label";
    private static final String ITEM = "iui.auto.update.feeders.form.current.item.label";
    private static final String PREPAIRING = "iui.auto.update.feeders.form.prepairing.caption";
    private static final String CANCEL = "iui.auto.update.feeders.form.cancel.button.label";
    private static final String CANCELLING = "iui.auto.update.feeders.form.canceling.button.label";

    private static final String PROCESS_DIALOG_ELAPSED_TIMER_NAME = "ElapsedTimer";
    private static final int ELAPSED_TIMER_PERIOD = 500;

    private final AutoUpdateFeedersModel model;
    private final ApiService service;
    private final CommandExecutor executor;
    private final Listener listener;

    private final JLabel overallProgressLabel;
    private final JProgressBar overallProgress;
    private final JLabel workspaceLabel;
    private final JLabel feederLabel;
    private final JLabel currentProgressLabel;
    private final JProgressBar currentProgress;
    private final JLabel elapsedTimeLabel;
    private final JButton cancelButton;

    private final java.util.Timer timer;

    private long startTime;

    private ScheduledItems scheduledItems;
    private Iterator<String> workspaceIterator;
    private String currentWorkspace;
    private Iterator<Item> itemIterator;

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");
    }

    private class ElapsedTimerTask extends TimerTask {

        public void run() {
            SwingTools.invokeRunnable(new Runnable() {
                public void run() {
                    onElapsedTimer();
                }
            }, log);
        }
    }

    public AutoUpdateFeedersForm(final JFrame _owner,
                                 final int _width,
                                 final int _height,
                                 final StringResource _stringResource,
                                 final AutoUpdateFeedersModel _model,
                                 final ApiService _service,
                                 final CommandExecutor _executor,
                                 final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        Assert.notNull(_service, "API service is null");
        this.service = _service;

        Assert.notNull(_executor, "Command executor is null");
        this.executor = _executor;

        setCaption(getString(CAPTION));

        this.overallProgressLabel = new JLabel(getString(OVERALL));
        getContentPanel().add(this.overallProgressLabel, createConstraint().maximumWidth().wrap().toString());

        this.overallProgress = new JProgressBar();
        getContentPanel().add(this.overallProgress, createConstraint().maximumWidth().progressBarHeight().wrap().toString());

        this.workspaceLabel = new JLabel(getString(WORKSPACE));
        getContentPanel().add(this.workspaceLabel, createConstraint().maximumWidth().wrap().toString());

        this.feederLabel = new JLabel(getString(ITEM));
        getContentPanel().add(this.feederLabel, createConstraint().maximumWidth().wrap().toString());

        this.currentProgressLabel = new JLabel(getString(PREPAIRING));
        getContentPanel().add(this.currentProgressLabel, createConstraint().maximumWidth().wrap().toString());

        this.currentProgress = new JProgressBar();
        getContentPanel().add(this.currentProgress, createConstraint().maximumWidth().progressBarHeight().wrap().toString());

        this.elapsedTimeLabel = new JLabel(" ");
        getContentPanel().add(this.elapsedTimeLabel, createConstraint().alignCenter().verticalGap().wrap().toString());

        this.cancelButton = new JButton(getString(CANCEL));
        this.cancelButton.addActionListener(this);
        getContentPanel().add(this.cancelButton, createConstraint().largeButton().alignCenter().verticalGap().toString());
        setDefaultButton(this.cancelButton);

        this.timer = new java.util.Timer(PROCESS_DIALOG_ELAPSED_TIMER_NAME, true);

        this.scheduledItems = this.model.getScheduleAdapter().getItemsForUpdate(System.currentTimeMillis());

        //updateAllScheduledItems();
    }

    public void complete(final LoadWorkspaceAndFeedersScript _script) {
        Assert.notNull(_script, "Load workspace script is null");
        this.itemIterator = this.scheduledItems.getItemsFromWorkspace(this.currentWorkspace).iterator();
    }

    public void fault(final LoadWorkspaceAndFeedersScript _script, final Throwable _cause) {
        Assert.notNull(_script, "Load workspace script is null");

        updateCurrentWorkspace();
    }

    private void updateAllScheduledItems() {
        startElapsedTimer();

        this.workspaceIterator = this.scheduledItems.getWorkspaces().iterator();

        updateCurrentWorkspace();
    }

    private void updateCurrentWorkspace() {

        if (this.workspaceIterator.hasNext()) {
            this.currentWorkspace = this.workspaceIterator.next();

            this.workspaceLabel.setText("");
            this.feederLabel.setText("");
            this.currentProgressLabel.setText(getString(PREPAIRING));

            this.executor.execute(this.service.loadWorkspaceAndFeeders(this.currentWorkspace, this), false);
        } else {
            stopElapsedTimer();

            this.model.approve();
            this.listener.submit(this.model);
        }
    }

    private void updateCurrentItem() {
        //To change body of created methods use File | Settings | File Templates.
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

    public interface Listener {
        void submit(AutoUpdateFeedersModel _model);
    }
}
