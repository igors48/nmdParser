package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.DeleteWorkspaceConfirmationModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 20.12.2010
 */
public class DeleteWorkspaceConfirmationForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.delete.workspace.confirmation.form.caption";
    private static final String LABEL = "iui.delete.workspace.confirmation.form.label";
    private static final String YES = "iui.delete.workspace.confirmation.yes.button.label";
    private static final String NO = "iui.delete.workspace.confirmation.no.button.label";

    private final DeleteWorkspaceConfirmationModel model;
    private final Listener listener;

    private final JButton yes;
    private final JButton no;

    public DeleteWorkspaceConfirmationForm(JFrame _owner, int _width, int _height, StringResource _stringResource, final DeleteWorkspaceConfirmationModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        JLabel label = new JLabel(MessageFormat.format(getString(LABEL), this.model.getWorkspace()));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        getContentPanel().add(label, createConstraint().maximumWidth().wrap().toString());

        this.yes = new JButton(getString(YES));
        getContentPanel().add(this.yes, createConstraint().largeButton().alignCenter().wrap().toString());
        setDefaultButton(this.yes);
        this.yes.addActionListener(this);

        this.no = new JButton(getString(NO));
        addFooterButton(this.no, createConstraint().mediumButton().alignCenter().wrap().toString());
        this.no.addActionListener(this);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.yes) {
            onApprove();
        }

        if (_event.getSource() == this.no) {
            onDiscard();
        }
    }

    protected void onApprove() {
        this.model.approve();

        this.listener.submit(this.model);
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(DeleteWorkspaceConfirmationModel _model);
    }
}
