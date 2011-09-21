package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.ErrorModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ErrorForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.error.form.caption";
    private static final String ADDITIONAL_INFO_LABEL = "iui.error.form.additional.info.label";
    private static final String CLOSE_BUTTON_LABEL = "iui.error.form.close.button.label";
    private static final String NO_MESSAGE_TEXT = "iui.error.form.no.message.text";
    private static final String NO_STACKTRACE_TEXT = "iui.error.form.no.stack.trace.text";
    
    /*private static final String BACK_BUTTON_LABEL = "iui.error.form.back.button.label";*/

    private static final String CR_LF = "\r\n";

    private final ErrorModel model;
    private final Listener listener;
    private final JButton closeButton;

    /*private JButton backButton;*/

    public ErrorForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final ErrorModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(this.model.getMessage()), createConstraint().maximumWidth().wrap().toString());

        if (this.model.getCause() != null) {
            getContentPanel().add(new JLabel(getString(ADDITIONAL_INFO_LABEL)), createConstraint().maximumWidth().verticalGap().wrap().toString());

            String message = this.model.getCause().getMessage();

            StringBuffer text = new StringBuffer(message == null || message.isEmpty() ? getString(NO_MESSAGE_TEXT) : message);

            text.append(CR_LF);

            final StackTraceElement[] stackTraceElements = this.model.getCause().getStackTrace();

            if (stackTraceElements == null) {
                text.append(getString(NO_STACKTRACE_TEXT));
            } else {

                for (StackTraceElement element : stackTraceElements) {
                    text.append(CR_LF).append(element);
                }
            }
            
            JTextArea textArea = new JTextArea(text.toString());

            textArea.setEditable(false);

            getContentPanel().add(new JScrollPane(textArea), createConstraint().maximumWidth().listHeight().wrap().toString());
        }

        /*
        if (!this.model.isOnlyExit()) {
            this.backButton = new JButton(getString(BACK_BUTTON_LABEL));
            this.backButton.addActionListener(this);
            setDefaultButton(this.backButton);
            addFooterButton(this.backButton, createConstraint().mediumButton().toString());
        }
        */

        this.closeButton = new JButton(getString(CLOSE_BUTTON_LABEL));
        this.closeButton.addActionListener(this);
        addFooterButton(this.closeButton, createConstraint().mediumButton().toString());
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.closeButton) {
            this.model.approve();
            this.listener.submit(this.model);
        }

        /*
        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
        */
    }

    protected void onApprove() {
        onDiscard();
    }

    protected void onDiscard() {
        this.model.reject();
        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(ErrorModel _model);
    }
}
