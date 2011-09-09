package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.ChooseWorkspaceTaskModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 16.12.2010
 */
public class ChooseWorkspaceTaskForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.choose.workspace.task.form.caption";
    private static final String CREATE = "iui.choose.workspace.task.form.create.button.label";
    private static final String RENAME = "iui.choose.workspace.task.form.rename.button.label";
    private static final String DELETE = "iui.choose.workspace.task.form.delete.button.label";
    private static final String BACK = "iui.choose.workspace.task.form.back.button.label";

    private final ChooseWorkspaceTaskModel model;
    private final Listener listener;

    private final JButton createButton;
    private final JButton renameButton;
    private final JButton deleteButton;
    private final JButton backButton;

    public ChooseWorkspaceTaskForm(JFrame _owner, int _width, int _height, StringResource _stringResource, final ChooseWorkspaceTaskModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(), createConstraint().maximumWidth().wrap().toString());

        this.createButton = new JButton(getString(CREATE));
        this.createButton.addActionListener(this);
        getContentPanel().add(this.createButton, createConstraint().largeButton().gapY(50).alignCenter().wrap().toString());
        setDefaultButton(this.createButton);

        this.renameButton = new JButton(getString(RENAME));
        this.renameButton.addActionListener(this);
        getContentPanel().add(this.renameButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.deleteButton = new JButton(getString(DELETE));
        this.deleteButton.addActionListener(this);
        getContentPanel().add(this.deleteButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.backButton = new JButton(getString(BACK));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.createButton) {
            approve(ChooseWorkspaceTaskModel.Task.CREATE);
        }

        if (_event.getSource() == this.renameButton) {
            approve(ChooseWorkspaceTaskModel.Task.RENAME);
        }

        if (_event.getSource() == this.deleteButton) {
            approve(ChooseWorkspaceTaskModel.Task.DELETE);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    protected void onApprove() {
        approve(ChooseWorkspaceTaskModel.Task.CREATE);
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    private void approve(final ChooseWorkspaceTaskModel.Task _task) {
        this.model.setTask(_task);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(ChooseWorkspaceTaskModel _model);
    }

}
