package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.custom.ContextEditorTableModel;
import app.iui.flow.models.UpdateContextModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 15.12.2010
 */
public class UpdateContextForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.update.context.form.caption";
    private static final String LABEL = "iui.update.context.form.label";
    private static final String NEXT = "iui.update.context.form.next.button.label";
    private static final String BACK = "iui.update.context.form.back.button.label";

    private final UpdateContextModel model;
    private final Listener listener;

    private final JTable table;
    private final JButton nextButton;
    private final JButton backButton;

    public UpdateContextForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final UpdateContextModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(MessageFormat.format(getString(LABEL), this.model.getFeederName())), createConstraint().maximumWidth().wrap().toString());

        this.table = new JTable();
        this.table.setOpaque(false);
        setContextModel();
        getContentPanel().add(this.table.getTableHeader(), createConstraint().maximumWidth().wrap().toString());
        getContentPanel().add(this.table, createConstraint().maximumWidth().smallerListHeight().wrap().toString());

        this.nextButton = new JButton(getString(NEXT));
        this.nextButton.addActionListener(this);
        getContentPanel().add(this.nextButton, createConstraint().largeButton().verticalGap().alignRight().toString());
        setDefaultButton(this.nextButton);

        this.backButton = new JButton(getString(BACK));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    protected void onApprove() {
        this.model.approve();
        this.listener.submit(this.model);
    }

    protected void onDiscard() {
        this.model.reject();
        this.listener.submit(this.model);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.nextButton) {
            onApprove();
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    private void setContextModel() {
        this.table.setModel(new ContextEditorTableModel(this.model.getContext(), this.stringResource));
    }

    public interface Listener {
        void submit(UpdateContextModel _model);
    }
}
