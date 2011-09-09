package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractFormWithValidation;
import app.iui.flow.models.ForcedUpdatePeriodModel;
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
 *         Date: 14.12.2010
 */
public class ForcedUpdatePeriodForm extends AbstractFormWithValidation implements ActionListener {

    private static final String CAPTION = "iui.forced.update.form.caption";
    private static final String LABEL = "iui.forced.update.form.label";
    private static final String NEXT = "iui.forced.update.form.next.button.label";
    private static final String BACK = "iui.forced.update.form.back.button.label";

    private final ForcedUpdatePeriodModel model;
    private final Listener listener;

    private JTextField periodField;
    private JButton nextButton;
    private JButton backButton;

    public ForcedUpdatePeriodForm(final JFrame _owner,
                                  final ValidatorFactory _validatorFactory,
                                  final int _width,
                                  final int _height,
                                  final StringResource _stringResource,
                                  final ForcedUpdatePeriodModel _model,
                                  final Listener _listener) {
        super(_owner, _validatorFactory, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));
    }

    protected void onDiscard() {
        hideValidationError();

        this.model.reject();
        this.listener.submit(this.model);
    }

    protected void onApprove() {
        ValidationError validationError = validation();

        if (validationError != null) {
            showValidationError(validationError);
        } else {
            hideValidationError();

            this.model.approve();
            this.model.setDays(Integer.valueOf(this.periodField.getText()));
            this.listener.submit(this.model);
        }
    }

    protected void addValidators(final Map<JTextField, Validators> _validators) {
        Assert.notNull(_validators, "Validators is null");

        Validators validators = new Validators();
        validators.addValidator(this.validatorFactory.notEmpty());
        validators.addValidator(this.validatorFactory.integerInRange(0, 365));
        _validators.put(this.periodField, validators);
    }

    protected void setupControls() {
        getContentPanel().add(new JLabel(getString(LABEL)), createConstraint().maximumWidth().wrap().toString());

        this.periodField = new JTextField("0");
        getContentPanel().add(this.periodField, createConstraint().maximumWidth().wrap().toString());

        this.nextButton = new JButton(getString(NEXT));
        this.nextButton.addActionListener(this);
        getContentPanel().add(this.nextButton, createConstraint().largeButton().alignCenter().verticalGap().toString());
        setDefaultButton(this.nextButton);

        this.backButton = new JButton(getString(BACK));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().alignCenter().toString());
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

    public interface Listener {
        void submit(ForcedUpdatePeriodModel _model);
    }
}
