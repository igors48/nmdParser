package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.GoogleReaderManageProfilesModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.02.2012
 */
public class GoogleReaderManageProfilesForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.google.reader.manage.profiles.form.caption";

    private static final String ADD_PROFILE_BUTTON_TITLE = "iui.google.reader.manage.profiles.form.add.button.label";
    private static final String EDIT_PROFILE_BUTTON_TITLE = "iui.google.reader.manage.profiles.form.edit.button.label";
    private static final String DELETE_PROFILE_BUTTON_TITLE = "iui.google.reader.manage.profiles.form.delete.button.label";

    private static final String BACK_BUTTON_TITLE = "iui.google.reader.manage.profiles.form.back.button.label";

    private final GoogleReaderManageProfilesModel model;
    private final Listener listener;

    private JButton addProfileButton;
    private JButton editProfileButton;
    private JButton deleteProfileButton;

    private JButton backButton;

    public GoogleReaderManageProfilesForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final GoogleReaderManageProfilesModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(this.stringResource.getString(CAPTION));

        getContentPanel().add(new JLabel(), createConstraint().maximumWidth().wrap().toString());

        this.addProfileButton = new JButton(getString(ADD_PROFILE_BUTTON_TITLE));
        this.addProfileButton.addActionListener(this);
        getContentPanel().add(this.addProfileButton, createConstraint().alignCenter().largeButton().gapY(30).wrap().toString());

        this.editProfileButton = new JButton(getString(EDIT_PROFILE_BUTTON_TITLE));
        this.editProfileButton.addActionListener(this);
        getContentPanel().add(this.editProfileButton, createConstraint().alignCenter().largeButton().gapY(30).wrap().toString());
        setDefaultButton(this.editProfileButton);

        this.deleteProfileButton = new JButton(getString(DELETE_PROFILE_BUTTON_TITLE));
        this.deleteProfileButton.addActionListener(this);
        getContentPanel().add(this.deleteProfileButton, createConstraint().alignCenter().largeButton().gapY(30).wrap().toString());

        this.backButton = new JButton(getString(BACK_BUTTON_TITLE));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    @Override
    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event);

        if (_event.getSource() == this.addProfileButton) {
            submit(GoogleReaderManageProfilesModel.Task.ADD_PROFILE);
        }

        if (_event.getSource() == this.editProfileButton) {
            submit(GoogleReaderManageProfilesModel.Task.EDIT_PROFILE);
        }

        if (_event.getSource() == this.deleteProfileButton) {
            submit(GoogleReaderManageProfilesModel.Task.DELETE_PROFILE);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    protected void onDiscard() {
        this.model.setTask(GoogleReaderManageProfilesModel.Task.BACK);
        this.model.reject();

        this.listener.submit(this.model);
    }

    protected void onApprove() {
        submit(GoogleReaderManageProfilesModel.Task.EDIT_PROFILE);
    }

    private void submit(final GoogleReaderManageProfilesModel.Task _result) {
        this.model.setTask(_result);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(GoogleReaderManageProfilesModel _model);
    }

}
