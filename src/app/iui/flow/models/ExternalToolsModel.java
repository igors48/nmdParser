package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 01.12.2010
 */
public class ExternalToolsModel extends Model {

    private String externalEditor;
    private boolean oneExternalEditorPerFile;
    private String externalFb2Viewer;
    private boolean oneExternalFb2ViewerPerFile;

    public ExternalToolsModel(final String _externalEditor, final boolean _oneExternalEditorPerFile, final String _externalFb2Viewer, final boolean _oneExternalFb2ViewerPerFile) {
        super(ModelType.EXTERNAL_TOOLS_SETTINGS);

        setExternalEditor(_externalEditor);
        setOneExternalEditorPerFile(_oneExternalEditorPerFile);
        setExternalFb2Viewer(_externalFb2Viewer);
        setOneExternalFb2ViewerPerFile(_oneExternalFb2ViewerPerFile);
    }

    public void setExternalEditor(final String _externalEditor) {
        Assert.notNull(_externalEditor, "External editor is null");
        this.externalEditor = _externalEditor;
    }

    public void setExternalFb2Viewer(final String _externalFb2Viewer) {
        Assert.notNull(_externalFb2Viewer, "External FB2 viewer is null");
        this.externalFb2Viewer = _externalFb2Viewer;
    }

    public void setOneExternalEditorPerFile(final boolean _oneExternalEditorPerFile) {
        this.oneExternalEditorPerFile = _oneExternalEditorPerFile;
    }

    public void setOneExternalFb2ViewerPerFile(final boolean _oneExternalFb2ViewerPerFile) {
        this.oneExternalFb2ViewerPerFile = _oneExternalFb2ViewerPerFile;
    }

    public String getExternalEditor() {
        return this.externalEditor;
    }

    public String getExternalFb2Viewer() {
        return this.externalFb2Viewer;
    }

    public boolean isOneExternalEditorPerFile() {
        return this.oneExternalEditorPerFile;
    }

    public boolean isOneExternalFb2ViewerPerFile() {
        return this.oneExternalFb2ViewerPerFile;
    }
}