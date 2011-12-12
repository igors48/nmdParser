package app.iui.flow.forms;

import app.iui.ApiService;
import app.iui.StringResource;
import app.iui.command.CommandExecutor;
import app.iui.command.ViewFilesExternalCommand;
import app.iui.flow.AbstractForm;
import app.iui.flow.ModelType;
import app.iui.flow.models.UpdatedFilesModel;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 11.12.2010
 */
public class UpdatedFilesForm extends AbstractForm implements ActionListener, ViewFilesExternalCommand.Listener, MouseListener {

    private static final String HEADER = "iui.updated.files.form.caption";
    private static final String LABEL = "iui.updated.files.form.label";
    private static final String VIEW = "iui.updated.files.form.view.button.label";

    private static final String MAIN = "iui.updated.files.form.main.button.label";
    private static final String SELECT_WORKSPACE = "iui.updated.files.form.select.workspace.button.label";
    private static final String SELECT_FEEDERS = "iui.updated.files.form.select.feeders.button.label";
    private static final String EDIT_FEEDER = "iui.updated.files.form.edit.feeder.button.label";

    private static final String NOT_EXISTS_FILE_PREFIX = "[?] ";

    private final UpdatedFilesModel model;
    private final CommandExecutor executor;
    private final ApiService service;
    private final Listener listener;

    private final JList filesList;
    private final JButton viewButton;

    private final JButton mainTasksButton;

    private JButton selectWorkspaceButton;
    private JButton selectFeedersButton;
    private JButton editFeederButton;

    public UpdatedFilesForm(final JFrame _owner,
                            final int _width,
                            final int _height,
                            final StringResource _stringResource,
                            final UpdatedFilesModel _model,
                            final ApiService _service,
                            final CommandExecutor _executor,
                            final Listener _listener) {
        super(_owner, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_service, "API service is null");
        this.service = _service;

        Assert.notNull(_executor, "Executor is null");
        this.executor = _executor;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(HEADER));

        getContentPanel().add(new JLabel(getString(LABEL)), createConstraint().maximumWidth().wrap().toString());

        this.filesList = new JList();
        //this.filesList.setOpaque(false);
        this.filesList.setModel(createListModel());
        this.filesList.addMouseListener(this);

        if (!this.model.getFiles().isEmpty()) {
            this.filesList.setSelectedIndex(0);
        }

        getContentPanel().add(new JScrollPane(this.filesList), createConstraint().maximumWidth().listHeight().wrap().toString());

        this.viewButton = new JButton(getString(VIEW));
        this.viewButton.setEnabled(this.model.isViewExternalEnabled());
        this.viewButton.addActionListener(this);
        getContentPanel().add(this.viewButton, createConstraint().largeButton().verticalGap().alignRight().toString());
        setDefaultButton(this.viewButton);

        this.mainTasksButton = new JButton(getString(MAIN));
        this.mainTasksButton.addActionListener(this);
        addFooterButton(this.mainTasksButton, createConstraint().mediumButton().toString());

        if (this.model.isDebugMode()) {
            this.editFeederButton = new JButton(getString(EDIT_FEEDER));
            this.editFeederButton.addActionListener(this);
            addFooterButton(this.editFeederButton, createConstraint().mediumButton().toString());
        } else {
            /*
            this.selectWorkspaceButton = new JButton(getString(SELECT_WORKSPACE));
            this.selectWorkspaceButton.addActionListener(this);
            addFooterButton(this.selectWorkspaceButton, createConstraint().mediumButton().toString());
            */
            /*
            this.selectFeedersButton = new JButton(getString(SELECT_FEEDERS));
            this.selectFeedersButton.addActionListener(this);
            addFooterButton(this.selectFeedersButton, createConstraint().mediumButton().toString());
            */
        }
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.viewButton) {
            viewSelectedFiles();
        }

        if (_event.getSource() == this.mainTasksButton) {
            submit(ModelType.CHOOSE_MAIN_TASK);
        }

        if (_event.getSource() == this.selectWorkspaceButton) {
            submit(ModelType.SELECT_WORKSPACE);
        }

        if (_event.getSource() == this.selectFeedersButton) {
            submit(ModelType.CHOOSE_FEEDERS);
        }

        if (_event.getSource() == this.editFeederButton) {
            submit(ModelType.SIMPLE_FEEDER);
        }
    }

    public void mouseClicked(final MouseEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getButton() == MouseEvent.BUTTON1 && _event.getClickCount() > 1) {
            viewSelectedFiles();
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

    private void viewSelectedFiles() {
        List<String> selected = newArrayList();

        for (Object current : this.filesList.getSelectedValues()) {
            String candidate = (String) current;

            if (candidateCanBeViewed(candidate)) {
                selected.add(candidate);
            }
        }

        this.executor.execute(this.service.viewFilesExternal(selected, this), true);
    }

    public void onComplete(final ViewFilesExternalCommand _command) {
        Assert.notNull(_command, "Command is null");
    }

    public void onFault(final ViewFilesExternalCommand _command, final Throwable _cause) {
        Assert.notNull(_command, "Command is null");
    }

    protected void onApprove() {
        viewSelectedFiles();
    }

    protected void onDiscard() {

        if (this.model.isDebugMode()) {
            submit(ModelType.SIMPLE_FEEDER);
        } else {
            submit(ModelType.CHOOSE_FEEDERS);
        }
    }

    private void submit(ModelType _type) {
        this.model.setReturnModel(_type);
        this.model.approve();

        this.listener.submit(this.model);
    }

    private DefaultListModel createListModel() {
        DefaultListModel model = new DefaultListModel();

        for (String fileName : this.model.getFiles()) {
            model.addElement(createElement(fileName));
        }

        return model;
    }

    private String createElement(final String _fileName) {
        File file = new File(_fileName);

        return file.exists() ? _fileName : NOT_EXISTS_FILE_PREFIX + _fileName;
    }

    private boolean candidateCanBeViewed(final String _candidate) {
        return !_candidate.startsWith(NOT_EXISTS_FILE_PREFIX);
    }

    public interface Listener {
        void submit(UpdatedFilesModel _model);
    }
}
