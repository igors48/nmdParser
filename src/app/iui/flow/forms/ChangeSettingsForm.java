package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.ChangeSettingsModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 16.04.2011
 */
public class ChangeSettingsForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.change.settings.form.caption";
    private static final String TOOLS = "iui.change.settings.form.tools.button.label";
    private static final String POSTPROCESSING = "iui.change.settings.form.postprocessing.button.label";
    private static final String BACK = "iui.change.settings.form.back.button.label";

    private final ChangeSettingsModel model;
    private final Listener listener;

    private final JButton externalToolsButton;
    private final JButton externalPostprocessingButton;
    private final JButton backButton;

    public ChangeSettingsForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final ChangeSettingsModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(), createConstraint().maximumWidth().wrap().toString());

        this.externalToolsButton = new JButton(getString(TOOLS));
        this.externalToolsButton.addActionListener(this);
        getContentPanel().add(this.externalToolsButton, createConstraint().largeButton().gapY(50).alignCenter().wrap().toString());
        setDefaultButton(this.externalToolsButton);

        this.externalPostprocessingButton = new JButton(getString(POSTPROCESSING));
        this.externalPostprocessingButton.addActionListener(this);
        getContentPanel().add(this.externalPostprocessingButton, createConstraint().largeButton().verticalGap().alignCenter().wrap().toString());

        this.backButton = new JButton(getString(BACK));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.externalToolsButton) {
            approve(ChangeSettingsModel.Settings.TOOLS);
        }

        if (_event.getSource() == this.externalPostprocessingButton) {
            approve(ChangeSettingsModel.Settings.POSTPROCESSING);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    protected void onApprove() {
        approve(ChangeSettingsModel.Settings.TOOLS);
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    private void approve(final ChangeSettingsModel.Settings _settings) {
        this.model.setSettings(_settings);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(ChangeSettingsModel _model);
    }
}
