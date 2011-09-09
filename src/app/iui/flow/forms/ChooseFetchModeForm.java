package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractForm;
import app.iui.flow.models.ChooseFetchModeModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ChooseFetchModeForm extends AbstractForm implements ActionListener {

    private static final String CAPTION = "iui.choose.fetch.mode.form.caption";

    private static final String CURRENT_WORKSPACE = "iui.choose.fetch.mode.form.current.workspace.label";

    private static final String FETCH_SINGLE = "iui.choose.fetch.mode.form.fetch.single.button.label";
    private static final String FETCH_MULIPILE = "iui.choose.fetch.mode.fetch.multiple.button.label";
    private static final String FETCH_FORCED = "iui.choose.fetch.mode.fetch.forced.button.label";
    private static final String BACK = "iui.choose.fetch.mode.fetch.back.button.label";

    private final ChooseFetchModeModel model;
    private final Listener listener;

    private final JButton fetchSingleButton;
    private final JButton fetchMultipleButton;
    private final JButton fetchForcedButton;

    private final JButton backButton;

    public ChooseFetchModeForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource, final ChooseFetchModeModel _model, final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(CAPTION));

        getContentPanel().add(new JLabel(MessageFormat.format(getString(CURRENT_WORKSPACE), this.model.getWorkspace())), createConstraint().maximumWidth().wrap().toString());

        this.fetchSingleButton = new JButton(getString(FETCH_SINGLE));
        this.fetchSingleButton.addActionListener(this);
        getContentPanel().add(this.fetchSingleButton, createConstraint().largeButton().alignCenter().wrap().gapY(30).toString());
        setDefaultButton(this.fetchSingleButton);

        this.fetchMultipleButton = new JButton(getString(FETCH_MULIPILE));
        this.fetchMultipleButton.addActionListener(this);
        getContentPanel().add(this.fetchMultipleButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.fetchForcedButton = new JButton(getString(FETCH_FORCED));
        this.fetchForcedButton.addActionListener(this);
        getContentPanel().add(this.fetchForcedButton, createConstraint().largeButton().alignCenter().wrap().toString());

        this.backButton = new JButton(getString(BACK));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());
    }

    protected void onApprove() {
        this.model.approve();

        this.listener.sumbit(this.model);
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.sumbit(this.model);
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.fetchSingleButton) {
            submit(ChooseFetchModeModel.FetchMode.STANDARD);
        }

        if (_event.getSource() == this.fetchMultipleButton) {
            submit(ChooseFetchModeModel.FetchMode.FORCED);
        }

        if (_event.getSource() == this.fetchForcedButton) {
            submit(ChooseFetchModeModel.FetchMode.PARAMETRIZED);
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }
    }

    private void submit(final ChooseFetchModeModel.FetchMode _result) {
        this.model.setMode(_result);
        onApprove();
    }

    public interface Listener {
        void sumbit(ChooseFetchModeModel _model);
    }
}
