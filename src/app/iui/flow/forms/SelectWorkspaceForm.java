package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.ModelType;
import app.iui.flow.models.SelectWorkspaceModel;
import net.miginfocom.swing.MigLayout;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 04.12.2010
 */
public class SelectWorkspaceForm extends AbstractForm implements ActionListener, MouseListener {

    private static final String CAPTION = "iui.select.workspace.form.caption";
    private static final String LABEL = "iui.select.workspace.form.label";

    private static final String BACK = "iui.select.workspace.form.back.button.label";
    private static final String GOTO_WORKSPACE_TASKS_LABEL = "iui.select.workspace.form.goto.workspace.tasks.button.label";

    private final SelectWorkspaceModel model;
    private final Listener listener;

    private final JList list;
    private final JButton nextButton;

    private final JButton goToMainTasksButton;
    private final JButton goToWorkspaceTasksButton;

    public SelectWorkspaceForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final SelectWorkspaceModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(MessageFormat.format(getString(CAPTION), this.model.getTask()));

        getContentPanel().setLayout(new MigLayout());

        getContentPanel().add(new JLabel(getString(LABEL)), "wrap");

        this.list = new JList();
        //this.list.setOpaque(false);
        this.list.addMouseListener(this);

        setupList();
        getContentPanel().add(new JScrollPane(this.list), createConstraint().maximumWidth().listHeight().wrap().toString());

        this.nextButton = new JButton(this.model.getNextTaskLabel());
        this.nextButton.addActionListener(this);
        getContentPanel().add(this.nextButton, createConstraint().largeButton().alignRight().verticalGap().toString());
        setDefaultButton(this.nextButton);

        this.goToMainTasksButton = new JButton(getString(BACK));
        this.goToMainTasksButton.addActionListener(this);
        addFooterButton(this.goToMainTasksButton, createConstraint().mediumButton().toString());

        this.goToWorkspaceTasksButton = new JButton(getString(GOTO_WORKSPACE_TASKS_LABEL));
        this.goToWorkspaceTasksButton.addActionListener(this);
        /*addFooterButton(this.goToWorkspaceTasksButton, createConstraint().mediumButton().toString());*/

    }

    protected void onApprove() {
        this.model.approve();
        this.model.setSelected((String) this.list.getSelectedValue());

        this.listener.submit(this.model);
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    public void mouseClicked(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getButton() == MouseEvent.BUTTON1 && _event.getClickCount() > 1) {
            onApprove();
        }
    }

    public void mousePressed(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void mouseReleased(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void mouseEntered(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void mouseExited(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.nextButton) {
            onApprove();
        }

        if (_event.getSource() == this.goToMainTasksButton) {
            discard(ModelType.CHOOSE_MAIN_TASK);
        }

        if (_event.getSource() == this.goToWorkspaceTasksButton) {
            discard(ModelType.CHOOSE_WORKSPACE_TASK);
        }
    }

    private void discard(final ModelType _type) {
        this.model.setRejected(_type);
        onDiscard();
    }

    private void setupList() {
        DefaultListModel model = new DefaultListModel();

        for (String workspace : this.model.getWorkspaces()) {
            model.addElement(workspace);
        }

        this.list.setModel(model);

        if (!this.model.getWorkspaces().isEmpty()) {
            this.list.setSelectedIndex(0);
        }
    }

    public interface Listener {
        void submit(SelectWorkspaceModel _model);
    }
}
