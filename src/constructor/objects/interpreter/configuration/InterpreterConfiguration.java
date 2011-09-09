package constructor.objects.interpreter.configuration;

import constructor.dom.Blank;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурация интерпретатора
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public class InterpreterConfiguration implements Blank {

    private String id;

    private StandardChainProcessorAdapter pages;  // page list
    private StandardChainProcessorAdapter fragments; // page
    private FragmentAnalyserConfiguration fragment;

    public void setId(String _id) {
        Assert.isValidString(_id, "Interpreter id is not valid.");
        this.id = _id;
    }

    public String getId() {
        return this.id;
    }

    public List<UsedObject> getUsedObjects() {
        List<UsedObject> result = new ArrayList<UsedObject>();

        if (this.pages != null) {
            result.add(new UsedObject(this.pages.getId(), ObjectType.PROCESSOR));
        }

        if (this.fragments != null) {
            result.add(new UsedObject(this.fragments.getId(), ObjectType.PROCESSOR));
        }

        if (this.fragment != null) {
            result.addAll(this.fragment.getUsedObjects());
        }

        return result;
    }

    public void setPageListAnalyser(final StandardChainProcessorAdapter _chainProcessorAdapter) {
        Assert.notNull(_chainProcessorAdapter, "Pages analyser adapter is null.");
        this.pages = _chainProcessorAdapter;
    }

    public StandardChainProcessorAdapter getPageListAnalyser() {
        return this.pages;
    }

    public void setPageAnalyser(final StandardChainProcessorAdapter _chainProcessorAdapter) {
        Assert.notNull(_chainProcessorAdapter, "Fragments analyser adapter is null.");
        this.fragments = _chainProcessorAdapter;
    }

    public StandardChainProcessorAdapter getPageAnalyser() {
        return this.fragments;
    }

    public void setFragmentAnalyserConfiguration(final FragmentAnalyserConfiguration _fragment) {
        Assert.notNull(_fragment, "Fragment analyser adapter is null.");
        this.fragment = _fragment;
    }

    public FragmentAnalyserConfiguration getFragmentAnalyserConfiguration() {
        return this.fragment;
    }
}
