package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractFormWithValidation;
import app.iui.flow.models.ExternalPostprocessorModel;
import app.iui.tools.ExternalToolsUtils;
import app.iui.validator.ValidationError;
import app.iui.validator.ValidatorFactory;
import app.iui.validator.Validators;
import net.miginfocom.swing.MigLayout;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 16.04.2011
 */
public class ExternalPostprocessingSettingsForm extends AbstractFormWithValidation implements ActionListener {

    private static final String FORM_CAPTION = "iui.change.settings.ext.post.form.caption";
    private static final String LABEL = "iui.change.settings.ext.post.form.label";

    private static final String EXTERNAL_POSTPROCESSING_ENABLED_LABEL = "iui.change.settings.ext.post.enabled.label";

    private static final String EXTERNAL_POSTPROCESSING_PATH_LABEL = "iui.change.settings.ext.post.path.label";
    private static final String EXTERNAL_POSTPROCESSING_BROWSE_LABEL = "iui.change.settings.ext.post.browse.label";
    private static final String EXTERNAL_POSTPROCESSING_PATTERN_LABEL = "iui.change.settings.ext.post.pattern.label";

    private static final String EXTERNAL_POSTPROCESSING_LEGEND_TEXT_01 = "iui.change.settings.ext.post.legend.01";
    private static final String EXTERNAL_POSTPROCESSING_LEGEND_TEXT_02 = "iui.change.settings.ext.post.legend.02";
    private static final String EXTERNAL_POSTPROCESSING_LEGEND_TEXT_03 = "iui.change.settings.ext.post.legend.03";
    private static final String EXTERNAL_POSTPROCESSING_LEGEND_TEXT_04 = "iui.change.settings.ext.post.legend.04";

    private static final String EXTERNAL_POSTPROCESSING_TIMEOUT_LABEL = "iui.change.settings.ext.post.timeout.label";

    private static final String STORE_SETTINGS_BUTTON_LABEL = "iui.change.settings.form.store.settings.button.label";
    private static final String BACK_BUTTON_LABEL = "iui.change.settings.form.external.tools.back.button.label";

    private static final String BROWSE_DIALOG_CAPTION = "iui.change.settings.browse.dialog.caption";

    private final ExternalPostprocessorModel model;
    private final Listener listener;

    private JCheckBox enableExternalPostprocessingCheckBox;

    private JTextField pathTextField;
    private JButton browseButton;
    private JTextField patternTextField;
    private JTextField timeoutTextField;

    private JButton storeSettingsButton;
    private JButton backButton;

    public ExternalPostprocessingSettingsForm(final JFrame _owner, final ValidatorFactory _validatorFactory, final int _width, final int _height, final StringResource _stringResource, final ExternalPostprocessorModel _model, final Listener _listener) {
        super(_owner, _validatorFactory, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        loadModel();
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.backButton) {
            hideValidationError();
            this.listener.submit(this.model);
        }

        if (_event.getSource() == this.browseButton) {
            String candidate = ExternalToolsUtils.selectExistsExecutableFile(this, this.stringResource.getString(BROWSE_DIALOG_CAPTION));

            if (!candidate.isEmpty()) {
                this.pathTextField.setText(candidate);
            }
        }

        if (_event.getSource() == this.storeSettingsButton) {
            onApprove();
        }
    }

    protected void onDiscard() {
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
            updateModel();
            this.listener.submit(this.model);
        }
    }

    private void loadModel() {
        this.enableExternalPostprocessingCheckBox.setSelected(this.model.isExternalPostprocessingEnabled());
        this.pathTextField.setText(this.model.getPathToExternalPostprocessor());
        this.patternTextField.setText(this.model.getCommandLinePattern());
        this.timeoutTextField.setText(String.valueOf(this.model.getExternalPostprocessingTimeout()));
    }

    private void updateModel() {
        this.model.setExternalPostprocessingEnabled(this.enableExternalPostprocessingCheckBox.isSelected());
        this.model.setPathToExternalPostprocessor(this.pathTextField.getText());
        this.model.setCommandLinePattern(this.patternTextField.getText());
        this.model.setExternalPostprocessingTimeout(Long.valueOf(this.timeoutTextField.getText()));
    }

    protected void addValidators(final Map<JTextField, Validators> _validators) {
        Assert.notNull(_validators, "Validators is null");

        Validators timeoutValidators = new Validators();
        timeoutValidators.addValidator(this.validatorFactory.notEmpty());
        timeoutValidators.addValidator(this.validatorFactory.integerInRange(-1, 300000));
        _validators.put(this.timeoutTextField, timeoutValidators);

        Validators pathValidators = new Validators();
        pathValidators.addValidator(this.validatorFactory.fileIsExecutable());
        _validators.put(this.pathTextField, pathValidators);
    }

    protected void setupControls() {
        setCaption(getString(FORM_CAPTION));

        getContentPanel().setLayout(new MigLayout());

        getContentPanel().add(new JLabel(getString(LABEL)), "wrap");

        this.enableExternalPostprocessingCheckBox = new JCheckBox(getString(EXTERNAL_POSTPROCESSING_ENABLED_LABEL));
        this.enableExternalPostprocessingCheckBox.setOpaque(false);
        getContentPanel().add(this.enableExternalPostprocessingCheckBox, createConstraint().maximumWidth().verticalGap().wrap().toString());

        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_PATH_LABEL)), createConstraint().verticalGap().wrap().toString());

        this.pathTextField = new JTextField();
        getContentPanel().add(this.pathTextField, createConstraint().width(412).split(2).toString());

        this.browseButton = new JButton(getString(EXTERNAL_POSTPROCESSING_BROWSE_LABEL));
        this.browseButton.addActionListener(this);
        getContentPanel().add(this.browseButton, "wrap");

        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_PATTERN_LABEL)), createConstraint().maximumWidth().wrap().toString());

        this.patternTextField = new JTextField();
        getContentPanel().add(this.patternTextField, createConstraint().width(412).wrap().toString());

        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_LEGEND_TEXT_01)), createConstraint().maximumWidth().wrap().toString());
        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_LEGEND_TEXT_02)), createConstraint().maximumWidth().wrap().toString());
        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_LEGEND_TEXT_03)), createConstraint().maximumWidth().wrap().toString());
        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_LEGEND_TEXT_04)), createConstraint().maximumWidth().wrap().toString());

        getContentPanel().add(new JLabel(getString(EXTERNAL_POSTPROCESSING_TIMEOUT_LABEL)), createConstraint().maximumWidth().verticalGap().wrap().toString());

        this.timeoutTextField = new JTextField();
        getContentPanel().add(this.timeoutTextField, createConstraint().width(412).wrap().toString());

        this.storeSettingsButton = new JButton(getString(STORE_SETTINGS_BUTTON_LABEL));
        this.storeSettingsButton.addActionListener(this);
        getContentPanel().add(this.storeSettingsButton, createConstraint().largeButton().alignRight().verticalGap().toString() + ", span 2");
        setDefaultButton(this.storeSettingsButton);

        this.backButton = new JButton(getString(BACK_BUTTON_LABEL));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    public interface Listener {
        void submit(ExternalPostprocessorModel _model);
    }
}
