package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.entity.Entity;
import app.iui.entity.EntityComparator;
import app.iui.flow.AbstractForm;
import app.iui.flow.Constraint;
import app.iui.flow.custom.CheckBoxJList;
import app.iui.flow.models.ChooseFeedersModel;
import net.miginfocom.swing.MigLayout;
import util.Assert;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ChooseFeedersForm extends AbstractForm implements ActionListener, ListSelectionListener, MouseListener {

    private static final String CAPTION = "iui.choose.feeders.form.caption";

    private static final String FEEDERS_LIST_LABEL = "iui.choose.feeders.form.feeders.list.label";
    private static final String BACK_BUTTON_LABEL = "iui.choose.feeders.form.back.button.label";
    private static final String ALL_BUTTON_LABEL = "iui.choose.feeders.form.select.all.button.label";
    private static final String NONE_BUTTON_LABEL = "iui.choose.feeders.form.select.none.button.label";
    private static final String SELECTED_LABEL = "iui.choose.feeders.form.select.selected.label";

    private final ChooseFeedersModel model;
    private final Listener listener;

    private final JButton nextButton;
    private final JButton backButton;
    private final JList feedersList;

    private JButton selectAllButton;
    private JButton selectNoneButton;
    private JLabel selected;
    private static final String SIMPLER_POSTFIX = " [s]";
    private static final String PARAMETER_POSTFIX = " [?]";

    public ChooseFeedersForm(final JFrame _owner,
                             final int _width,
                             final int _height,
                             final StringResource _stringResource,
                             final ChooseFeedersModel _model,
                             final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(MessageFormat.format(getString(CAPTION), this.model.getTaskName()));

        getContentPanel().setLayout(new MigLayout());

        getContentPanel().add(new JLabel(MessageFormat.format(getString(FEEDERS_LIST_LABEL), this.model.getWorkspace())), "wrap");

        this.feedersList = this.model.isMultiSelect() ? new CheckBoxJList() : new JList();
        this.feedersList.setOpaque(false);
        this.feedersList.setModel(createListModel());
        this.feedersList.addListSelectionListener(this);
        this.feedersList.addMouseListener(this);

        Constraint constraint = this.model.isMultiSelect() ? createConstraint().maximumWidth().smallerListHeight().wrap() : createConstraint().maximumWidth().listHeight().wrap();
        getContentPanel().add(new JScrollPane(this.feedersList), constraint.toString());

        if (this.model.isMultiSelect()) {
            this.selected = new JLabel("1");
            getContentPanel().add(this.selected, createConstraint().maximumWidth().wrap().toString());

            this.selectAllButton = new JButton(getString(ALL_BUTTON_LABEL));
            this.selectAllButton.addActionListener(this);
            getContentPanel().add(this.selectAllButton, createConstraint().split(3).mediumButton().verticalGap().toString());

            this.selectNoneButton = new JButton(getString(NONE_BUTTON_LABEL));
            this.selectNoneButton.addActionListener(this);
            getContentPanel().add(this.selectNoneButton, createConstraint().mediumButton().verticalGap().toString());
        }

        this.nextButton = new JButton(this.model.getNextTaskName());
        this.nextButton.addActionListener(this);
        getContentPanel().add(this.nextButton, createConstraint().largeButton().alignRight().verticalGap().toString());
        setDefaultButton(this.nextButton);

        this.backButton = new JButton(getString(BACK_BUTTON_LABEL));
        this.backButton.addActionListener(this);
        addFooterButton(this.backButton, createConstraint().mediumButton().toString());

        updateSelected();
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.nextButton) {
            onApprove();
        }

        if (_event.getSource() == this.backButton) {
            onDiscard();
        }

        if (_event.getSource() == this.selectAllButton) {
            selectAll();
        }

        if (_event.getSource() == this.selectNoneButton) {
            selectNone();
        }
    }

    public void valueChanged(final ListSelectionEvent _event) {
        Assert.notNull(_event, "Event is null");

        updateSelected();
    }

    protected void onApprove() {

        if (this.nextButton.isEnabled()) {
            List<Entity> selected = newArrayList();

            for (int current : this.feedersList.getSelectedIndices()) {
                selected.add(this.model.getFeeders().get(current));
            }

            this.model.setSelected(selected);
            this.model.approve();

            this.listener.submit(this.model);
        }
    }

    protected void onDiscard() {
        this.model.reject();

        this.listener.submit(this.model);
    }

    private void updateSelected() {
        int selected = this.feedersList.getSelectedIndices().length;

        if (this.selected != null) {
            this.selected.setText(MessageFormat.format(getString(SELECTED_LABEL), String.valueOf(selected), this.model.getFeeders().size()));
        }

        this.nextButton.setEnabled(selected > 0);
    }

    private DefaultListModel createListModel(/*final List<Entity> _entities*/) {
        DefaultListModel model = new DefaultListModel();

        Collections.sort(this.model.getFeeders(), new EntityComparator());

        for (Entity feeder : this.model.getFeeders()) {
            String name = feeder.getName();

            if (feeder.isSimpler()) {
                name += SIMPLER_POSTFIX;
            }

            if (feeder.isPlaceholdersUsed()) {
                name += PARAMETER_POSTFIX;
            }

            model.addElement(name);
        }

        return model;
    }

    public void selectAll() {
        this.feedersList.clearSelection();
        this.feedersList.setSelectionInterval(0, this.model.getFeeders().size() - 1);
    }

    public void selectNone() {
        this.feedersList.clearSelection();
    }

    public void mouseClicked(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getButton() == MouseEvent.BUTTON1 && _event.getClickCount() > 1) {
            onApprove();
        }
    }

    public void mousePressed(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void mouseReleased(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void mouseEntered(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public void mouseExited(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

    }

    public interface Listener {
        void submit(ChooseFeedersModel _model);
    }
}
