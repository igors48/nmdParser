package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.FeedersTasksModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 27.11.2010
 */
public class FeedersTasksForm extends AbstractForm implements ActionListener {

    private static final String FEEDERS_TASKS_FORM_CAPTION = "iui.feeders.tasks.form.caption";
    private static final String CREATE_SIMPLE_FEEDER_BUTTON_LABEL = "iui.feeders.tasks.form.create.simple.feeder.button.label";
    private static final String EDIT_SIMPLE_FEEDER_BUTTON_LABEL = "iui.feeders.tasks.form.edit.simple.feeder.button.label";
    private static final String DELETE_SIMPLE_FEEDER_BUTTON_LABEL = "iui.feeders.tasks.form.delete.simple.feeder.button.label";
    private static final String EDIT_FEEDER_EXTERNAL_BUTTON_LABEL = "iui.feeders.tasks.form.edit.feeder.external.button.label";
    private static final String RESET_FEEDER_BUTTON_LABEL = "iui.feeders.tasks.form.reset.feeder.button.label";
    private static final String BACK_TO_MAIN_TASKS_BUTTON_LABEL = "iui.feeders.tasks.form.back.to.main.tasks.button.label";

    private static final String CURRENT_WORKSPACE = "iui.feeders.tasks.form.form.current.workspace.label";

    private final FeedersTasksModel model;
    private final Listener listener;

    private JButton createSimpleFeederButton;
    private JButton editSimpleFeederButton;
    private JButton deleteSimpleFeederButton;
    private JButton editFeederExternalButton;
    private JButton resetFeedersButton;
    private JButton backButton;

    public FeedersTasksForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final FeedersTasksModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        setCaption(this.stringResource.getString(FEEDERS_TASKS_FORM_CAPTION));

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        getContentPanel().add(new JLabel(MessageFormat.format(getString(CURRENT_WORKSPACE), this.model.getWorkspace())), createConstraint().maximumWidth().wrap().toString());

        this.createSimpleFeederButton = new JButton(getString(CREATE_SIMPLE_FEEDER_BUTTON_LABEL));
        this.createSimpleFeederButton.addActionListener(this);
        getContentPanel().add(this.createSimpleFeederButton, createConstraint().largeButton().alignCenter().wrap().toString());
        setDefaultButton(this.createSimpleFeederButton);

        this.editSimpleFeederButton = new JButton(getString(EDIT_SIMPLE_FEEDER_BUTTON_LABEL));
        this.editSimpleFeederButton.addActionListener(this);
        getContentPanel().add(this.editSimpleFeederButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.deleteSimpleFeederButton = new JButton(getString(DELETE_SIMPLE_FEEDER_BUTTON_LABEL));
        this.deleteSimpleFeederButton.addActionListener(this);
        getContentPanel().add(this.deleteSimpleFeederButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.editFeederExternalButton = new JButton(getString(EDIT_FEEDER_EXTERNAL_BUTTON_LABEL));
        this.editFeederExternalButton.addActionListener(this);
        this.editFeederExternalButton.setEnabled(this.model.isEditExternalEnabled());
        getContentPanel().add(this.editFeederExternalButton, createConstraint().largeButton().alignCenter().gapY(30).wrap().toString());

        this.resetFeedersButton = new JButton(getString(RESET_FEEDER_BUTTON_LABEL));
        this.resetFeedersButton.addActionListener(this);
        getContentPanel().add(this.resetFeedersButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.backButton = new JButton(getString(BACK_TO_MAIN_TASKS_BUTTON_LABEL));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }


    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.createSimpleFeederButton) {
            submit(FeedersTasksModel.FeederTask.CREATE_SIMPLE_FEEDER);
        }

        if (_event.getSource() == this.editSimpleFeederButton) {
            submit(FeedersTasksModel.FeederTask.EDIT_SIMPLE_FEEDER);
        }

        if (_event.getSource() == this.deleteSimpleFeederButton) {
            submit(FeedersTasksModel.FeederTask.DELETE_SIMPLE_FEEDER);
        }

        if (_event.getSource() == this.editFeederExternalButton) {
            submit(FeedersTasksModel.FeederTask.EDIT_FEEDER_EXTERNAL);
        }

        if (_event.getSource() == this.resetFeedersButton) {
            submit(FeedersTasksModel.FeederTask.RESET_FEEDER);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    protected void onDiscard() {
        this.model.setTask(FeedersTasksModel.FeederTask.BACK);
        this.model.reject();

        this.listener.submit(this.model);
    }

    protected void onApprove() {
        submit(FeedersTasksModel.FeederTask.CREATE_SIMPLE_FEEDER);
    }

    private void submit(final FeedersTasksModel.FeederTask _result) {
        this.model.setTask(_result);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(FeedersTasksModel _model);
    }

}