package converter;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import constructor.objects.output.core.ForEachPostProcessor;
import constructor.objects.storage.Storage;
import flowtext.Document;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 05.02.2009
 */
public class ConverterContext implements Controller {
    private final Document document;
    private final String branch;
    private final String name;
    private final String tempDirectory;
    private final List<Storage> storages;
    private final boolean linksAsFootnotes;
    private final boolean removeExists;
    private final boolean resolveImageLinks;
    private final ForEachPostProcessor forEachPostProcessor;
    private final Controller controller;

    public ConverterContext(Document _document, String _branch, String _name, String _tempDirectory, List<Storage> _storages, boolean _linksAsFootnotes, boolean _removeExists, boolean _resolveImageLinks, ForEachPostProcessor _forEachPostProcessor, final Controller _controller) {
        Assert.notNull(_document, "Document is null");
        Assert.notNull(_branch, "Branch is null");
        Assert.isValidString(_name, "Name is not valid");
        Assert.notNull(_storages, "Storages is null");
        Assert.isValidString(_tempDirectory, "Temp directory is not valid");
        Assert.greater(_storages.size(), 0, "Storages count <= 0");
        Assert.notNull(_forEachPostProcessor, "For each postprocessor is null");
        Assert.notNull(_controller, "Controller is null");

        this.document = _document;
        this.branch = _branch;
        this.name = _name;
        this.tempDirectory = _tempDirectory;
        this.storages = _storages;
        this.linksAsFootnotes = _linksAsFootnotes;
        this.removeExists = _removeExists;
        this.resolveImageLinks = _resolveImageLinks;
        this.forEachPostProcessor = _forEachPostProcessor;
        this.controller = _controller;
    }

    public String getBranch() {
        return this.branch;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getName() {
        return this.name;
    }

    public List<Storage> getStorages() {
        return this.storages;
    }

    public String getTempDirectory() {
        return this.tempDirectory;
    }

    public String getTempFile() {
        return this.tempDirectory + this.name;
    }

    public boolean isLinksAsFootnotes() {
        return this.linksAsFootnotes;
    }

    public boolean isRemoveExists() {
        return this.removeExists;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }

    public ForEachPostProcessor getForEachPostProcessor() {
        return this.forEachPostProcessor;
    }

    public void onStart() {
        this.controller.onStart();
    }

    public void onProgress(final SingleProcessInfo _info) {
        Assert.notNull(_info, "Process info is null");

        this.controller.onProgress(_info);
    }

    public void onComplete() {
        this.controller.onComplete();
    }

    public void onFault() {
        this.controller.onFault();
    }

    public void onCancel() {
        this.controller.onCancel();
    }

    public boolean isCancelled() {
        return this.controller.isCancelled();
    }
}
