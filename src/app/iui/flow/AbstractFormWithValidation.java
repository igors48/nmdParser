package app.iui.flow;

import app.iui.StringResource;
import app.iui.validator.ValidationError;
import app.iui.validator.ValidatorFactory;
import app.iui.validator.Validators;
import util.Assert;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 29.11.2010
 */
public abstract class AbstractFormWithValidation extends AbstractForm {

    protected final ValidatorFactory validatorFactory;

    protected final Map<JTextField, Validators> validators;

    protected JDialog popup;
    protected JLabel popupLabel;
    protected JTextField errorField;
    protected Color originalTextFieldBackgroundColor;

    public AbstractFormWithValidation(final JFrame _owner, final ValidatorFactory _validatorFactory, final int _width, final int _height, final StringResource _stringResource) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_validatorFactory, "Validator factory is null");
        this.validatorFactory = _validatorFactory;

        createPopup();
        setupControls();
        this.validators = setupValidators();
    }

    protected void showValidationError(final ValidationError _error) {
        Assert.notNull(_error, "Validation error is null");

        if (this.popup.isVisible()) {
            hideValidationError();
        }

        this.errorField = _error.getTarget();
        colorAndFocusErrorTextField();
        showPopup(_error);
    }

    protected void hideValidationError() {

        if (this.popup.isVisible()) {
            restoreErrorTextFieldColor();
            hidePopup();
            this.errorField = null;
        }
    }

    protected ValidationError validation() {
        ValidationError result = null;

        for (JTextField victim : this.validators.keySet()) {
            String message = this.validators.get(victim).valid(victim.getText());

            if (!message.isEmpty()) {
                result = new ValidationError(victim, message);
            }
        }

        return result;
    }

    protected abstract void addValidators(Map<JTextField, Validators> _validators);

    protected abstract void setupControls();

    private void createPopup() {
        this.popup = new JDialog(this.owner);
        this.popup.getContentPane().setLayout(new FlowLayout());
        this.popup.setUndecorated(true);
        this.popup.getContentPane().setBackground(new Color(243, 255, 159));
        this.popupLabel = new JLabel();
        this.popup.getContentPane().add(this.popupLabel);
        this.popup.setFocusableWindowState(false);
    }

    private void colorAndFocusErrorTextField() {

        if (this.errorField != null) {
            this.originalTextFieldBackgroundColor = this.errorField.getBackground();
            this.errorField.setBackground(Color.PINK);
            this.errorField.requestFocus();
        }
    }

    private void restoreErrorTextFieldColor() {

        if (this.errorField != null) {
            this.errorField.setBackground(this.originalTextFieldBackgroundColor == null ? Color.WHITE : this.originalTextFieldBackgroundColor);
        }
    }

    private void showPopup(final ValidationError _error) {
        Assert.notNull(_error, "Validation error is null");

        this.popupLabel.setText(_error.getMessage());
        this.popup.setSize(0, 0);
        this.popup.setLocationRelativeTo(_error.getTarget() == null ? this : _error.getTarget());

        Point point = this.popup.getLocation();
        Dimension cDim = _error.getTarget() == null ? this.getSize() : _error.getTarget().getSize();

        this.popup.setLocation(point.x - (int) cDim.getWidth() / 2 + 10, point.y + (int) cDim.getHeight() / 2);

        this.popup.pack();
        this.popup.setVisible(true);
    }

    private void hidePopup() {
        this.popup.setVisible(false);
    }

    private Map<JTextField, Validators> setupValidators() {
        Map<JTextField, Validators> result = new HashMap<JTextField, Validators>();

        addValidators(result);

        return result;
    }
}
