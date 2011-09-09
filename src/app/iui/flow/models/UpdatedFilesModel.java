package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 11.12.2010
 */
public class UpdatedFilesModel extends Model {

    private final boolean viewExternalEnabled;
    private final boolean debugMode;

    private final List<String> files;

    private ModelType returnModel;

    public UpdatedFilesModel(final boolean _debugMode, final List<String> _files, final boolean _viewExternalEnabled) {
        super(ModelType.UPDATED_FILES);

        this.viewExternalEnabled = _viewExternalEnabled;

        this.debugMode = _debugMode;

        Assert.notNull(_files, "Files is null");
        this.files = _files;
    }

    public boolean isViewExternalEnabled() {
        return this.viewExternalEnabled;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public List<String> getFiles() {
        return this.files;
    }

    public ModelType getReturnModel() {
        return this.returnModel;
    }

    public void setReturnModel(final ModelType _model) {
        Assert.notNull(_model, "Return code is null");

        this.returnModel = _model;
    }
}
