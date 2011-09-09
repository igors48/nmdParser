package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.ChooseFeederDebugActionModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Igor Usenko
 *         Date: 23.12.2010
 */
public class ChooseFeederDebugActionForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.choose.feeder.debug.action.from.caption";
    private static final String LABEL = "iui.choose.feeder.debug.action.from.label";
    private static final String TEST = "iui.choose.feeder.debug.action.from.test.button.label";
    private static final String TASKS = "iui.choose.feeder.debug.action.from.feeders.task.button.label";
    private static final String BACK = "iui.choose.feeder.debug.action.from.back.button.label";

    private final ChooseFeederDebugActionModel model;
    private final Listener listener;

    private JButton test;
    private JButton tasks;
    private JButton back;

    public ChooseFeederDebugActionForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final ChooseFeederDebugActionModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(getString(LABEL)), createConstraint().maximumWidth().wrap().toString());
        this.test = new JButton(getString(TEST));
        this.test.addActionListener(this);
        getContentPanel().add(this.test, createConstraint().largeButton().alignCenter().wrap().toString());
        setDefaultButton(this.test);

        this.tasks = new JButton(getString(TASKS));
        this.tasks.addActionListener(this);
        getContentPanel().add(this.tasks, createConstraint().largeButton().alignCenter().wrap().toString());

        this.back = new JButton(getString(BACK));
        this.back.addActionListener(this);
        addFooterButton(this.back, createConstraint().mediumButton().alignCenter().wrap().toString());
    }

    protected void onApprove() {
        approve(ChooseFeederDebugActionModel.Action.TEST);
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.test) {
            approve(ChooseFeederDebugActionModel.Action.TEST);
        }

        if (_event.getSource() == this.tasks) {
            approve(ChooseFeederDebugActionModel.Action.RETURN);
        }

        if (_event.getSource() == this.back) {
            onDiscard();
        }
    }

    private void approve(final ChooseFeederDebugActionModel.Action _action) {
        this.model.setAction(_action);
        this.model.approve();

        this.listener.submit(this.model);
    }

    public interface Listener {
        void submit(ChooseFeederDebugActionModel _model);
    }
}
