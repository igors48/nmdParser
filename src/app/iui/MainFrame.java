package app.iui;

import app.VersionInfo;
import app.iui.flow.AbstractForm;
import app.iui.flow.FlowController;
import app.iui.flow.FormFactory;
import app.iui.flow.Model;
import app.iui.flow.forms.*;
import app.iui.flow.models.*;
import util.Assert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

//TODO tranisition effect?

/**
 * @author Igor Usenko
 *         Date: 22.11.2010
 */
public class MainFrame extends JFrame implements
        WindowListener,
        MainTasksForm.Listener,
        FeedersTasksForm.Listener,
        NotImplementedForm.Listener,
        SelectWorkspaceForm.Listener,
        MessageForm.Listener,
        ErrorForm.Listener,
        ChooseFetchModeForm.Listener,
        ChooseFeedersForm.Listener,
        UpdateFeederForm.Listener,
        UpdateFeedersForm.Listener,
        LoadingWorkspaceForm.Listener,
        UpdatedFilesForm.Listener,
        ForcedUpdatePeriodForm.Listener,
        UpdateContextForm.Listener,
        ChooseWorkspaceTaskForm.Listener,
        EditWorkspaceNameForm.Listener,
        DeleteWorkspaceConfirmationForm.Listener,
        SimpleFeederForm.Listener,
        DeleteSimpleFeederConfirmationForm.Listener,
        ChooseFeederDebugActionForm.Listener,
        ChangeSettingsForm.Listener,
        ExternalToolsSettingsForm.Listener,
        ExternalPostprocessingSettingsForm.Listener,
        GoogleReaderChooseTaskForm.Listener,
        GoogleReaderManageProfilesForm.Listener {

    private static final String TITLE_KEY = "iui.main.frame.title";

    private static final String EXIT_TITLE_KEY = "iui.main.frame.exit.title";
    private static final String EXIT_CONFIRM_KEY = "iui.main.frame.exit.confirm";

    private static final String KEY_EVENT_VK_ESCAPE = "KeyEvent.VK_ESCAPE";
    private static final String KEY_EVENT_VK_ENTER = "KeyEvent.VK_ENTER";

    private static final int MAIN_FORM_WIDTH = 502;
    private static final int MAIN_FORM_HEIGHT = 516;

    private static final int SCREEN_PLACEMENT_LIMIT = 150;

    private final FlowController flowController;
    private final FormFactory formFactory;
    private final StringResource stringResource;
    private final GuiSettings guiSettings;
    private final Listener listener;

    private AbstractForm current;

    public MainFrame(final FlowController _flowController, final FormFactory _formFactory, final StringResource _stringResource, final VersionInfo _versionInfo, final GuiSettings _guiSettings, final Listener _listener) {
        super();

        Assert.notNull(_flowController, "Flow controller is null");
        this.flowController = _flowController;

        Assert.notNull(_formFactory, "Form factory is null");
        this.formFactory = _formFactory;
        this.formFactory.setOwner(this);

        Assert.notNull(_stringResource, "String resource is null");
        this.stringResource = _stringResource;

        Assert.notNull(_guiSettings, "GUI settings is null");
        this.guiSettings = _guiSettings;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setTitle(this.stringResource.getString(TITLE_KEY) + " [ " + _versionInfo.getVersionAndBuild() + " ]");

        setFormBounds();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    public void touch() {
        Model formRequest = this.flowController.handle();
        this.current = this.formFactory.create(formRequest, this);

        add(this.current);
    }

    public void showUnexpectedError(final Throwable _cause) {
        replaceCurrentForm(new ErrorModel("<html><h2>Unexpected error</h2></html>", _cause, true));
    }

    public void submit(final MainTasksModel _model) {
        Assert.notNull(_model, "Main task is null");

        if (_model.getMainTask() == MainTasksModel.MainTask.EXIT) {
            confrimExit();
        } else {
            replaceCurrentForm(this.flowController.handle(_model));
        }
    }

    public void submit(final FeedersTasksModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final NotImplementedModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ExternalToolsModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final MessageModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final SelectWorkspaceModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ErrorModel _model) {
        Assert.notNull(_model, "Model is null");

        if (_model.isApproved()) {
            this.listener.onNormalExit();
        } else {
            replaceCurrentForm(this.flowController.handle(_model));
        }
    }

    public void sumbit(final ChooseFetchModeModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ChooseFeedersModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final UpdateFeederModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final LoadingWorkspaceModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final UpdatedFilesModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final UpdateFeedersModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ForcedUpdatePeriodModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final UpdateContextModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ChooseWorkspaceTaskModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final EditWorkspaceNameModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final DeleteWorkspaceConfirmationModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final SimpleFeederModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final DeleteSimpleFeederConfirmationModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ChooseFeederDebugActionModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ChangeSettingsModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final ExternalPostprocessorModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(final GoogleReaderChooseTaskModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void submit(GoogleReaderManageProfilesModel _model) {
        Assert.notNull(_model, "Model is null");

        replaceCurrentForm(this.flowController.handle(_model));
    }

    public void windowClosing(final WindowEvent _event) {
        confrimExit();
    }

    public void windowOpened(final WindowEvent _event) {
        // empty
    }

    public void windowClosed(final WindowEvent _event) {
        // empty
    }

    public void windowIconified(final WindowEvent _event) {
        // empty
    }

    public void windowDeiconified(final WindowEvent _event) {
        // empty
    }

    public void windowActivated(final WindowEvent _event) {
        // empty
    }

    public void windowDeactivated(final WindowEvent _event) {
        // empty
    }

    protected JRootPane createRootPane() {
        JRootPane result = new JRootPane();

        result.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), KEY_EVENT_VK_ESCAPE);
        result.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), KEY_EVENT_VK_ENTER);
        result.getActionMap().put(KEY_EVENT_VK_ESCAPE, new EscapeKeyAction());
        result.getActionMap().put(KEY_EVENT_VK_ENTER, new EnterKeyAction());

        return result;
    }

    private void setFormBounds() {
        int x = this.guiSettings.getX();
        int y = this.guiSettings.getY();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (screenSize.getWidth() - SCREEN_PLACEMENT_LIMIT < x || screenSize.getHeight() - SCREEN_PLACEMENT_LIMIT < y) {
            x = SCREEN_PLACEMENT_LIMIT;
            y = SCREEN_PLACEMENT_LIMIT;
        }

        setBounds(x, y, MAIN_FORM_WIDTH, MAIN_FORM_HEIGHT);

        getContentPane().setPreferredSize(new Dimension(MAIN_FORM_WIDTH, MAIN_FORM_HEIGHT));
        pack();

        setResizable(false);
    }

    private void replaceCurrentForm(final Model _model) {
        remove(this.current);

        this.current = formFactory.create(_model, this);

        add(this.current);
        validate();
    }

    private void confrimExit() {
        int response = JOptionPane.showConfirmDialog(this, this.stringResource.getString(EXIT_CONFIRM_KEY), this.stringResource.getString(EXIT_TITLE_KEY), JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            this.guiSettings.setX(this.getX());
            this.guiSettings.setY(this.getY());
            this.guiSettings.setWidth(this.getWidth());
            this.guiSettings.setHeight(this.getHeight());

            this.listener.onNormalExit();
        }
    }

    private class EnterKeyAction extends AbstractAction {

        public void actionPerformed(final ActionEvent _event) {
            Assert.notNull(_event, "Event is null");

            if (current != null) {
                current.onEnterKey();
            }
        }
    }

    private class EscapeKeyAction extends AbstractAction {

        public void actionPerformed(final ActionEvent _event) {
            Assert.notNull(_event, "Event is null");

            if (current != null) {
                current.onEscapeKey();
            }
        }
    }

    public interface Listener {
        void onNormalExit();
    }
}
