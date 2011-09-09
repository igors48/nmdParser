package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractFormWithValidation;
import app.iui.flow.models.ExternalToolsModel;
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
 *         Date: 28.11.2010
 */
public class ExternalToolsSettingsForm extends AbstractFormWithValidation implements ActionListener {

    private static final String CHANGE_EXTERNAL_TOOLS_SETTINGS_FORM_CAPTION = "iui.change.settings.form.caption";
    private static final String BACK_BUTTON_LABEL = "iui.change.settings.form.external.tools.back.button.label";

    private static final String EXTERNAL_TOOLS_LABEL = "iui.change.settings.form.external.tools.label";

    private static final String EXTERNAL_EDITOR_LABEL = "iui.change.settings.form.external.editor.label";
    private static final String ONE_EXTERNAL_EDTIOR_PER_FILE_LABEL = "iui.change.settings.form.one.external.editor.per.file.label";
    private static final String BROWSE_EXTERNAL_EDITOR_BUTTON_LABEL = "iui.change.settings.form.browse.external.editor.button.label";

    private static final String EXTERNAL_FB2_VIEWER_LABEL = "iui.change.settings.form.external.fb2.viewer.label";
    private static final String ONE_EXTERNAL_FB2_VIEWER_PER_FILE_LABEL = "iui.change.settings.form.one.external.fb2.viewer.per.file";
    private static final String BROWSE_EXTERNAL_FB2_VIEWER_BUTTON_LABEL = "iui.change.settings.form.browse.external.fb2.viewer.button.label";

    private static final String STORE_SETTINGS_BUTTON_LABEL = "iui.change.settings.form.store.settings.button.label";

    private static final String BROWSE_DIALOG_CAPTION = "iui.change.settings.browse.dialog.caption";

    private final ExternalToolsModel model;
    private final Listener listener;

    private JTextField externalEditorTextField;
    private JCheckBox oneExternalEditorPerFileCheckBox;
    private JButton browseExternalEditorButton;

    private JTextField externalFb2ViewerTextField;
    private JCheckBox oneExternalFb2ViewerPerFileCheckBox;
    private JButton browseExternalFb2ViewerButton;

    private JButton storeSettingsButton;

    private JButton backButton;

    public ExternalToolsSettingsForm(final JFrame _owner, final ValidatorFactory _validatorFactory, final int _width, final int _height, final StringResource _stringResource, final ExternalToolsModel _model, final Listener _listener) {
        super(_owner, _validatorFactory, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        loadModel();
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

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.backButton) {
            hideValidationError();
            this.listener.submit(this.model);
        }

        if (_event.getSource() == this.browseExternalFb2ViewerButton) {
            String candidate = ExternalToolsUtils.selectExistsExecutableFile(this, this.stringResource.getString(BROWSE_DIALOG_CAPTION));

            if (!candidate.isEmpty()) {
                this.externalFb2ViewerTextField.setText(candidate);
            }
        }

        if (_event.getSource() == this.browseExternalEditorButton) {
            String candidate = ExternalToolsUtils.selectExistsExecutableFile(this, this.stringResource.getString(BROWSE_DIALOG_CAPTION));

            if (!candidate.isEmpty()) {
                this.externalEditorTextField.setText(candidate);
            }
        }

        if (_event.getSource() == this.storeSettingsButton) {
            onApprove();
        }
    }

    protected void addValidators(final Map<JTextField, Validators> _validators) {
        Assert.notNull(_validators, "Validators is null");

        Validators fb2ViewerValidators = new Validators();
        fb2ViewerValidators.addValidator(this.validatorFactory.fileIsExecutable());
        _validators.put(this.externalFb2ViewerTextField, fb2ViewerValidators);

        Validators editorPathValidators = new Validators();
        editorPathValidators.addValidator(this.validatorFactory.fileIsExecutable());
        _validators.put(this.externalEditorTextField, editorPathValidators);
    }

    protected void setupControls() {
        setCaption(getString(CHANGE_EXTERNAL_TOOLS_SETTINGS_FORM_CAPTION));

        getContentPanel().setLayout(new MigLayout());

        getContentPanel().add(new JLabel(getString(EXTERNAL_TOOLS_LABEL)), "wrap");

        getContentPanel().add(new JLabel(getString(EXTERNAL_EDITOR_LABEL)), "wrap");

        this.externalEditorTextField = new JTextField();
        getContentPanel().add(this.externalEditorTextField, "w 412!");

        this.browseExternalEditorButton = new JButton(getString(BROWSE_EXTERNAL_EDITOR_BUTTON_LABEL));
        this.browseExternalEditorButton.addActionListener(this);
        getContentPanel().add(this.browseExternalEditorButton, "wrap");

        this.oneExternalEditorPerFileCheckBox = new JCheckBox(getString(ONE_EXTERNAL_EDTIOR_PER_FILE_LABEL));
        this.oneExternalEditorPerFileCheckBox.setOpaque(false);
        getContentPanel().add(this.oneExternalEditorPerFileCheckBox, "wrap");

        getContentPanel().add(new JLabel(getString(EXTERNAL_FB2_VIEWER_LABEL)), "wrap, gapy 20");

        this.externalFb2ViewerTextField = new JTextField();
        getContentPanel().add(this.externalFb2ViewerTextField, "w 412!");

        this.browseExternalFb2ViewerButton = new JButton(getString(BROWSE_EXTERNAL_FB2_VIEWER_BUTTON_LABEL));
        this.browseExternalFb2ViewerButton.addActionListener(this);
        getContentPanel().add(this.browseExternalFb2ViewerButton, "wrap");

        this.oneExternalFb2ViewerPerFileCheckBox = new JCheckBox(getString(ONE_EXTERNAL_FB2_VIEWER_PER_FILE_LABEL));
        this.oneExternalFb2ViewerPerFileCheckBox.setOpaque(false);
        getContentPanel().add(this.oneExternalFb2ViewerPerFileCheckBox, "wrap");

        this.storeSettingsButton = new JButton(getString(STORE_SETTINGS_BUTTON_LABEL));
        this.storeSettingsButton.addActionListener(this);
        getContentPanel().add(this.storeSettingsButton, createConstraint().largeButton().alignRight().verticalGap().toString() + ", span 2");
        setDefaultButton(this.storeSettingsButton);

        this.backButton = new JButton(getString(BACK_BUTTON_LABEL));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    private void loadModel() {
        this.externalEditorTextField.setText(this.model.getExternalEditor());
        this.oneExternalEditorPerFileCheckBox.setSelected(this.model.isOneExternalEditorPerFile());
        this.externalFb2ViewerTextField.setText(this.model.getExternalFb2Viewer());
        this.oneExternalFb2ViewerPerFileCheckBox.setSelected(this.model.isOneExternalFb2ViewerPerFile());
    }

    private void updateModel() {
        this.model.setExternalEditor(this.externalEditorTextField.getText());
        this.model.setOneExternalEditorPerFile(this.oneExternalEditorPerFileCheckBox.isSelected());

        this.model.setExternalFb2Viewer(this.externalFb2ViewerTextField.getText());
        this.model.setOneExternalFb2ViewerPerFile(this.oneExternalFb2ViewerPerFileCheckBox.isSelected());
    }

    public interface Listener {
        void submit(ExternalToolsModel _model);
    }
}
