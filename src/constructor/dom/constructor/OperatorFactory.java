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

    public static final String GET_GROUP_ID = "getGroup";
    public static final String EXTRACT_TAG_ID = "extractTag";
    public static final String REMOVE_TAG_ID = "removeTag";
    public static final String SEQUENCE_TAG_ID = "sequence";
    public static final String SET_TAG_ID = "set";
    public static final String CONCAT_TAG_ID = "concat";
    public static final String XPATH_ID = "xPath";
    public static final String DEC_ID = "dec";
    public static final String LOAD_ID = "load";
    public static final String APPEND_ID = "append";
    public static final String WELD_ID = "weld";
    public static final String PACK_ID = "pack";
    public static final String FIRST_ONE_ID = "firstOne";
    public static final String FILTER_ID = "filter";

    private final ServiceManager serviceManager;

    public OperatorFactory(final ServiceManager _serviceManager) {
        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;
    }

    public VariableProcessorAdapter getAdapter(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        VariableProcessorAdapter adapter = null;

        if (_name.equals(GET_GROUP_ID)) {
            adapter = new GetGroupProcessorAdapter();
        }

        if (_name.equals(EXTRACT_TAG_ID)) {
            adapter = new ExtractTagProcessorAdapter();
        }

        if (_name.equals(REMOVE_TAG_ID)) {
            adapter = new RemoveTagProcessorAdapter();
        }

        if (_name.equals(SEQUENCE_TAG_ID)) {
            adapter = new SequenceProcessorAdapter();
        }

        if (_name.equals(SET_TAG_ID)) {
            adapter = new SetterProcessorAdapter();
        }

        if (_name.equals(CONCAT_TAG_ID)) {
            adapter = new ConcatProcessorAdapter();
        }

        if (_name.equals(XPATH_ID)) {
            adapter = new XPathProcessorAdapter();
        }

        if (_name.equals(DEC_ID)) {
            adapter = new DecrementProcessorAdapter();
        }

        if (_name.equals(LOAD_ID)) {
            adapter = new LoadProcessorAdapter(this.serviceManager);
        }

        if (_name.equals(APPEND_ID)) {
            adapter = new AppendProcessorAdapter();
        }

        if (_name.equals(WELD_ID)) {
            adapter = new WeldProcessorAdapter();
        }

        if (_name.equals(PACK_ID)) {
            adapter = new PackProcessorAdapter();
        }

        if (_name.equals(FIRST_ONE_ID)) {
            adapter = new FirstOneProcessorAdapter(this.serviceManager.getDebugConsole());
        }

        if (_name.equals(FILTER_ID)) {
            adapter = new FilterProcessorAdapter();
        }

        return adapter;
    }

    public ElementHandler getHandler(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        ElementHandler handler = null;

        if (_name.equals(GET_GROUP_ID)) {
            handler = new GetGroupProcessorElementHandler();
        }

        if (_name.equals(EXTRACT_TAG_ID)) {
            handler = new ExtractTagProcessorElementHandler();
        }

        if (_name.equals(REMOVE_TAG_ID)) {
            handler = new RemoveTagProcessorElementHandler();
        }

        if (_name.equals(SEQUENCE_TAG_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(SET_TAG_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(CONCAT_TAG_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(XPATH_ID)) {
            handler = new XPathProcessorElementHandler();
        }

        if (_name.equals(DEC_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(LOAD_ID)) {
            handler = new LoadProcessorElementHandler();
        }

        if (_name.equals(APPEND_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(WELD_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(PACK_ID)) {
            handler = new MappedElementHandler();
        }

        if (_name.equals(FIRST_ONE_ID)) {
            handler = new FirstOneProcessorElementHandler(this);
        }

        return handler;
    }
}
