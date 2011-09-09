package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractFormWithValidation;
import app.iui.flow.models.EditWorkspaceNameModel;
import app.iui.validator.ValidationError;
import app.iui.validator.ValidatorFactory;
import app.iui.validator.Validators;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 16.12.2010
 */
public class EditWorkspaceNameForm extends AbstractFormWithValidation implements ActionListener {

    private static final String CAPTION = "iui.edit.workspace.name.form.caption";
    private static final String BACK = "iui.edit.workspace.name.form.back.button.label";

    private final EditWorkspaceNameModel model;
    private final Listener listener;

    private JLabel label;
    private JTextField edit;
    private JButton next;
    private JButton back;

    public EditWorkspaceNameForm(final JFrame _owner, final ValidatorFactory _validatorFactory, final int _width, final int _height, final StringResource _stringResource, final EditWorkspaceNameModel _model, final Listener _listener) {
        super(_owner, _validatorFactory, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setModel();
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.next) {
            onApprove();
        }

        if (_event.getSource() == this.back) {
            onDiscard();
        }
    }

    protected void onApprove() {
        ValidationError validationError = validation();

        if (validationError != null) {
            showValidationError(validationError);
        } else {
            hideValidationError();

            this.model.approve();
            this.model.setName(this.edit.getText());
            this.listener.submit(this.model);
        }
    }

    protected void onDiscard() {
        hideValidationError();

        this.model.reject();
        this.listener.submit(this.model);
    }

    protected void addValidators(final Map<JTextField, Validators> _validators) {
        Assert.notNull(_validators, "Validators is null");

        Validators validators = new Validators();
        validators.addValidator(this.validatorFactory.notEmpty());
        validators.addValidator(this.validatorFactory.latinSymbolsAndDigits());
        _validators.put(this.edit, validators);
    }

    protected void setupControls() {
        setCaption(getString(CAPTION));

        this.label = new JLabel();
        getContentPanel().add(this.label, createConstraint().maximumWidth().wrap().toString());

        this.edit = new JTextField();
        getContentPanel().add(this.edit, createConstraint().maximumWidth().wrap().toString());

        this.next = new JButton();
        getContentPanel().add(this.next, createConstraint().largeButton().verticalGap().alignRight().wrap().toString());
        setDefaultButton(this.next);
        this.next.addActionListener(this);

        this.back = new JButton(getString(BACK));
        addFooterButton(this.back, createConstraint().mediumButton().toString());
        this.back.addActionListener(this);
    }

    private void setModel() {
        this.label.setText(this.model.getLabel());
        this.edit.setText(this.model.getName());
        this.next.setText(this.model.getNext());
    }

    public interface Listener {
        void submit(EditWorkspaceNameModel _model);
    }
}
