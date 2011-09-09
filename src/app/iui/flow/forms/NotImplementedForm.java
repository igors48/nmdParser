package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.NotImplementedModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 28.11.2010
 */
public class NotImplementedForm extends AbstractForm implements ActionListener {

    private static final String NOT_IMPLEMENTED_FORM_CAPTION = "iui.not.implemented.form.caption";
    private static final String BACK_BUTTON_LABEL = "iui.not.implemented.form.back.button.label";

    private final NotImplementedModel model;
    private final Listener listener;

    private final JButton backButton;

    public NotImplementedForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final NotImplementedModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(NOT_IMPLEMENTED_FORM_CAPTION));

        this.backButton = new JButton(getString(BACK_BUTTON_LABEL));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    protected void onApprove() {
        onDiscard();
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    public interface Listener {
        void submit(NotImplementedModel _model);
    }
}
