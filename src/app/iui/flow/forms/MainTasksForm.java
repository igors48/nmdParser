package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.MainTasksModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 27.11.2010
 */
public class MainTasksForm extends AbstractForm implements ActionListener {

    private static final String MAIN_TASKS_FORM_CAPTION = "iui.main.tasks.form.caption";
    private static final String UPDATE_FEEDERS_BUTTON_LABEL = "iui.main.tasks.form.update.feeders.button.label";
    private static final String GOOGLE_READER_BUTTON_LABEL = "iui.main.tasks.form.google.reader.button.label";
    private static final String MANAGE_FEEDERS_BUTTON_LABEL = "iui.main.tasks.form.manage.feeders.button.label";
    private static final String MANAGE_WORKSPACES_BUTTON_LABEL = "iui.main.tasks.form.manage.workspaces.button.label";
    private static final String CHANGE_SETTINGS_BUTTON_LABEL = "iui.main.tasks.form.change.settings.button.label";
    private static final String EXIT_BUTTON_LABEL = "iui.main.tasks.form.exit.button.label";

    private final MainTasksModel model;
    private final Listener listener;

    private JButton updateFeedersButton;
    private JButton googleReaderButton;
    private JButton manageFeedersButton;
    private JButton manageWorkspacesButton;
    private JButton changeSettingsButton;
    private JButton exitButton;

    public MainTasksForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final MainTasksModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(MAIN_TASKS_FORM_CAPTION));

        getContentPanel().add(new JLabel(), createConstraint().maximumWidth().wrap().toString());

        this.updateFeedersButton = new JButton(getString(UPDATE_FEEDERS_BUTTON_LABEL));
        this.updateFeedersButton.addActionListener(this);
        getContentPanel().add(this.updateFeedersButton, createConstraint().alignCenter().largeButton().gapY(30).wrap().toString());
        setDefaultButton(this.updateFeedersButton);

        this.googleReaderButton = new JButton(getString(GOOGLE_READER_BUTTON_LABEL));
        this.googleReaderButton.addActionListener(this);
        getContentPanel().add(this.googleReaderButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.manageFeedersButton = new JButton(getString(MANAGE_FEEDERS_BUTTON_LABEL));
        this.manageFeedersButton.addActionListener(this);
        getContentPanel().add(this.manageFeedersButton, createConstraint().largeButton().gapY(20).alignCenter().wrap().toString());

        this.manageWorkspacesButton = new JButton(getString(MANAGE_WORKSPACES_BUTTON_LABEL));
        this.manageWorkspacesButton.addActionListener(this);
        getContentPanel().add(this.manageWorkspacesButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.changeSettingsButton = new JButton(getString(CHANGE_SETTINGS_BUTTON_LABEL));
        this.changeSettingsButton.addActionListener(this);
        getContentPanel().add(this.changeSettingsButton, createConstraint().largeButton().gapY(20).alignCenter().wrap().toString());

        this.exitButton = new JButton(getString(EXIT_BUTTON_LABEL));
        this.exitButton.addActionListener(this);
        addFooterButton(this.exitButton, createConstraint().mediumButton().wrap().toString());
    }

    protected void onDiscard() {
        this.model.setMainTask(MainTasksModel.MainTask.EXIT);
        this.model.reject();

        this.listener.submit(this.model);
    }

    protected void onApprove() {
        submit(MainTasksModel.MainTask.UPDATE_FEEDERS);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.updateFeedersButton) {
            submit(MainTasksModel.MainTask.UPDATE_FEEDERS);
        }

        if (_event.getSource() == this.googleReaderButton) {
            submit(MainTasksModel.MainTask.GOOGLE_READER);
        }

        if (_event.getSource() == this.manageFeedersButton) {
            submit(MainTasksModel.MainTask.MANAGE_FEEDERS);
        }

        if (_event.getSource() == this.manageWorkspacesButton) {
            submit(MainTasksModel.MainTask.MANAGE_WORKSPACES);
        }

        if (_event.getSource() == this.changeSettingsButton) {
            submit(MainTasksModel.MainTask.CHANGE_SETTINGS);
        }

        if (_event.getSource() == this.exitButton) {
            onDiscard();
        }
    }

    private void submit(final MainTasksModel.MainTask _result) {
        this.model.setMainTask(_result);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(MainTasksModel _mainTaskModel);
    }


}
