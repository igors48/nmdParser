package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.GoogleReaderChooseTaskModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.02.2012
 */
public class GoogleReaderChooseTaskForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.google.reader.tasks.form.caption";

    private static final String GET_NEWS_BUTTON_TITLE = "iui.google.reader.tasks.form.get.news.label";
    private static final String MANAGE_PROFILES_BUTTON_TITLE = "iui.google.reader.tasks.form.manage.profiles.label";

    private static final String BACK_BUTTON_TITLE = "iui.google.reader.tasks.form.back.button.label";

    private final GoogleReaderChooseTaskModel model;
    private final Listener listener;

    private JButton getNewsButton;
    private JButton manageProfilesButton;

    private JButton backButton;

    public GoogleReaderChooseTaskForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final GoogleReaderChooseTaskModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(this.stringResource.getString(CAPTION));

        getContentPanel().add(new JLabel(), createConstraint().maximumWidth().wrap().toString());

        this.getNewsButton = new JButton(getString(GET_NEWS_BUTTON_TITLE));
        this.getNewsButton.addActionListener(this);
        getContentPanel().add(this.getNewsButton, createConstraint().alignCenter().largeButton().gapY(30).wrap().toString());
        setDefaultButton(this.getNewsButton);

        this.manageProfilesButton = new JButton(getString(MANAGE_PROFILES_BUTTON_TITLE));
        this.manageProfilesButton.addActionListener(this);
        getContentPanel().add(this.manageProfilesButton, createConstraint().largeButton().alignCenter().gapY(20).wrap().toString());

        this.backButton = new JButton(getString(BACK_BUTTON_TITLE));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());

    }

    @Override
    public void actionPerformed(ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.getNewsButton) {
            submit(GoogleReaderChooseTaskModel.Task.GET_NEWS);
        }

        if (_event.getSource() == this.manageProfilesButton) {
            submit(GoogleReaderChooseTaskModel.Task.MANAGE_PROFILES);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    protected void onDiscard() {
        this.model.setTask(GoogleReaderChooseTaskModel.Task.BACK);
        this.model.reject();

        this.listener.submit(this.model);
    }

    protected void onApprove() {
        submit(GoogleReaderChooseTaskModel.Task.GET_NEWS);
    }

    private void submit(final GoogleReaderChooseTaskModel.Task _result) {
        this.model.setTask(_result);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(GoogleReaderChooseTaskModel _model);
    }

}
