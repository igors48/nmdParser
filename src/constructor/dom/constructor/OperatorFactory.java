package constructor.dom.constructor;

import app.workingarea.ServiceManager;
import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.append.adapter.AppendProcessorAdapter;
import constructor.objects.processor.chain.adapter.FirstOneProcessorAdapter;
import constructor.objects.processor.chain.element.FirstOneProcessorElementHandler;
import constructor.objects.processor.concat.adapter.ConcatProcessorAdapter;
import constructor.objects.processor.decrement.adapter.DecrementProcessorAdapter;
import constructor.objects.processor.extract_tag.adapter.ExtractTagProcessorAdapter;
import constructor.objects.processor.extract_tag.element.ExtractTagProcessorElementHandler;
import constructor.objects.processor.filter.adapter.FilterProcessorAdapter;
import constructor.objects.processor.get_group.adapter.GetGroupProcessorAdapter;
import constructor.objects.processor.get_group.element.GetGroupProcessorElementHandler;
import constructor.objects.processor.load.adapter.LoadProcessorAdapter;
import constructor.objects.processor.load.element.LoadProcessorElementHandler;
import constructor.objects.processor.pack.adapter.PackProcessorAdapter;
import constructor.objects.processor.remove_tag.adapter.RemoveTagProcessorAdapter;
import constructor.objects.processor.remove_tag.element.RemoveTagProcessorElementHandler;
import constructor.objects.processor.sequencer.adapter.SequenceProcessorAdapter;
import constructor.objects.processor.setter.adapter.SetterProcessorAdapter;
import constructor.objects.processor.weld.adapter.WeldProcessorAdapter;
import constructor.objects.processor.xpath.adapter.XPathProcessorAdapter;
import constructor.objects.processor.xpath.element.XPathProcessorElementHandler;
import util.Assert;

/**
 * ������� ��������� � ��������� ������ ��� �������� ����������
 *
 * @author Igor Usenko
 *         Date: 27.06.2010
 */
public class OperatorFactory {

    public static final String GET_GROUP = "getGroup";
    public static final String EXTRACT_TAG = "extractTag";
    public static final String REMOVE_TAG = "removeTag";
    public static final String SEQUENCE_TAG = "sequence";
    public static final String SET_TAG = "set";
    public static final String CONCAT_TAG = "concat";
    public static final String XPATH = "xPath";
    public static final String DEC = "dec";
    public static final String LOAD = "load";
    public static final String APPEND = "append";
    public static final String WELD = "weld";
    public static final String PACK = "pack";
    public static final String FIRST_ONE = "firstOne";
    public static final String FILTER = "filter";

    private final ServiceManager serviceManager;

    public OperatorFactory(final ServiceManager _serviceManager) {
        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;
    }

    public VariableProcessorAdapter getAdapter(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        VariableProcessorAdapter adapter = null;

        if (_name.equals(GET_GROUP)) {
            adapter = new GetGroupProcessorAdapter();
        }

        if (_name.equals(EXTRACT_TAG)) {
            adapter = new ExtractTagProcessorAdapter();
        }

        if (_name.equals(REMOVE_TAG)) {
            adapter = new RemoveTagProcessorAdapter();
        }

        if (_name.equals(SEQUENCE_TAG)) {
            adapter = new SequenceProcessorAdapter();
        }

        if (_name.equals(SET_TAG)) {
            adapter = new SetterProcessorAdapter();
        }

        if (_name.equals(CONCAT_TAG)) {
            adapter = new ConcatProcessorAdapter();
        }

        if (_name.equals(XPATH)) {
            adapter = new XPathProcessorAdapter();
        }

        if (_name.equals(DEC)) {
            adapter = new DecrementProcessorAdapter();
        }

        if (_name.equals(LOAD)) {
            adapter = new LoadProcessorAdapter(this.serviceManager);
        }

        if (_name.equals(APPEND)) {
            adapter = new AppendProcessorAdapter();
        }

        if (_name.equals(WELD)) {
            adapter = new WeldProcessorAdapter();
        }

        if (_name.equals(PACK)) {
            adapter = new PackProcessorAdapter();
        }

        if (_name.equals(FIRST_ONE)) {
            adapter = new FirstOneProcessorAdapter(this.serviceManager.getDebugConsole());
        }

        if (_name.equals(FILTER)) {
            adapter = new FilterProcessorAdapter();
        }

        return adapter;
    }

    public ElementHandler getHandler(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        ElementHandler handler = null;

        if (_name.equals(GET_GROUP)) {
            handler = new GetGroupProcessorElementHandler();
        }

        if (_name.equals(EXTRACT_TAG)) {
            handler = new ExtractTagProcessorElementHandler();
        }

        if (_name.equals(REMOVE_TAG)) {
            handler = new RemoveTagProcessorElementHandler();
        }

        if (_name.equals(SEQUENCE_TAG)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(SET_TAG)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(CONCAT_TAG)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(XPATH)) {
            handler = new XPathProcessorElementHandler();
        }

        if (_name.equals(DEC)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(LOAD)) {
            handler = new LoadProcessorElementHandler();
        }

        if (_name.equals(APPEND)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(WELD)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(PACK)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(FIRST_ONE)) {
            handler = new FirstOneProcessorElementHandler(this);
        }

        if (_name.equals(FILTER)) {
            handler = new MappedElementHandler();
        }

        return handler;
    }
}
