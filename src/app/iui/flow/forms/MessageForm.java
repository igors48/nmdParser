package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.MessageModel;
import net.miginfocom.swing.MigLayout;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 04.12.2010
 */
public class MessageForm extends AbstractForm implements ActionListener {

    private static final String CONTINUE = "iui.continue.button.label";

    private final MessageModel model;
    private final Listener listener;

    private final JButton continueButton;

    public MessageForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final MessageModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(this.model.getTitle());

        getContentPanel().setLayout(new MigLayout());

        JLabel label = new JLabel(model.getMessage());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        getContentPanel().add(label, createConstraint().maximumWidth().alignCenter().wrap().toString());

        this.continueButton = new JButton(getString(CONTINUE));
        this.continueButton.addActionListener(this);
        getContentPanel().add(this.continueButton, createConstraint().largeButton().alignCenter().wrap().toString());
        setDefaultButton(this.continueButton);
    }

    protected void onApprove() {
        this.listener.submit(this.model);
    }

    protected void onDiscard() {
        this.listener.submit(this.model);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.continueButton) {
            onApprove();
        }
    }

    public interface Listener {
        void submit(MessageModel _model);
    }
}
