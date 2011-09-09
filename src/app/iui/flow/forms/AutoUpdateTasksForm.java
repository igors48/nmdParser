package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.AutoUpdateTasksModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 08.05.2011
 */
public class AutoUpdateTasksForm extends AbstractForm implements ActionListener {

    private final String CAPTION = "iui.auto.update.tasks.form.caption";
    private final String START_BUTTON_LABEL = "iui.auto.update.tasks.form.start.button.label";
    private final String EDIT_BUTTON_LABEL = "iui.auto.update.tasks.form.edit.button.label";
    private final String BACK_BUTTON_LABEL = "iui.auto.update.tasks.form.back.button.label";

    private final AutoUpdateTasksModel model;
    private final Listener listener;

    private JButton startButton;
    private JButton editButton;
    private JButton backButton;

    public AutoUpdateTasksForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final AutoUpdateTasksModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(), createConstraint().maximumWidth().wrap().toString());

        this.startButton = new JButton(getString(START_BUTTON_LABEL));
        this.startButton.addActionListener(this);
        getContentPanel().add(this.startButton, createConstraint().alignCenter().largeButton().gapY(30).wrap().toString());
        setDefaultButton(this.startButton);

        this.editButton = new JButton(getString(EDIT_BUTTON_LABEL));
        this.editButton.addActionListener(this);
        getContentPanel().add(this.editButton, createConstraint().alignCenter().largeButton().wrap().toString());

        this.backButton = new JButton(getString(BACK_BUTTON_LABEL));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().wrap().toString());
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.startButton) {
            submit(AutoUpdateTasksModel.AutoUpdateTask.START);
        }

        if (_event.getSource() == this.editButton) {
            submit(AutoUpdateTasksModel.AutoUpdateTask.EDIT);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    protected void onApprove() {
        submit(AutoUpdateTasksModel.AutoUpdateTask.START);
    }

    private void submit(final AutoUpdateTasksModel.AutoUpdateTask _result) {
        this.model.setTask(_result);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(AutoUpdateTasksModel _model);
    }
}
