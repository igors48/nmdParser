package constructor.dom.constructor;

import app.workingarea.ServiceManager;
import constructor.dom.Blank;
import constructor.dom.ComponentFactory;
import constructor.dom.ElementHandler;
import constructor.dom.StandardTypeNames;
import constructor.objects.channel.configuration.ChannelConfiguration;
import constructor.objects.channel.element.ChannelElementHandler;
import constructor.objects.dateparser.configuration.DateParserConfiguration;
import constructor.objects.dateparser.element.DateParserElementHandler;
import constructor.objects.interpreter.configuration.InterpreterConfiguration;
import constructor.objects.interpreter.element.InterpreterElementHandler;
import constructor.objects.output.configuration.OutputConfiguration;
import constructor.objects.output.element.OutputElementHandler;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import constructor.objects.processor.chain.element.ChainProcessorElementHandler;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.simpler.element.SimplerElementHandler;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import constructor.objects.snippet.element.SnippetElementHandler;
import constructor.objects.source.configuration.SourceConfiguration;
import constructor.objects.source.element.SourceElementHandler;
import constructor.objects.storage.local.configuration.LocalStorageConfiguration;
import constructor.objects.storage.local.element.LocalStorageElementHandler;
import util.Assert;

/**
 * Фабрика топовых компонент системы : source, channel,
 * interpreter, processor, storage, output and dateparser
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class StandardComponentFactory implements ComponentFactory {

    private final ServiceManager serviceManager;
    private final OperatorFactory operatorFactory;

    public StandardComponentFactory(final ServiceManager _serviceManager) {
        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;

        this.operatorFactory = new OperatorFactory(this.serviceManager);
    }

    public Blank getBlank(final String _id) {
        Assert.isValidString(_id, "Id not valid.");

        Blank result = null;

        if (_id.equals(StandardTypeNames.PROCESSOR_ELEMENT_NAME)) {
            result = new StandardChainProcessorAdapter(this.serviceManager.getDebugConsole());
        }

        if (_id.equals(StandardTypeNames.SOURCE_ELEMENT_NAME)) {
            result = new SourceConfiguration();
        }

        if (_id.equals(StandardTypeNames.INTERPRETER_ELEMENT_NAME)) {
            result = new InterpreterConfiguration();
        }

        if (_id.equals(StandardTypeNames.CHANNEL_ELEMENT_NAME)) {
            result = new ChannelConfiguration();
        }

        if (_id.equals(StandardTypeNames.OUTPUT_ELEMENT_NAME)) {
            result = new OutputConfiguration();
        }

        if (_id.equals(StandardTypeNames.STORAGE_ELEMENT_NAME)) {
            result = new LocalStorageConfiguration();
        }

        if (_id.equals(StandardTypeNames.DATEPARSER_ELEMENT_NAME)) {
            result = new DateParserConfiguration();
        }

        if (_id.equals(StandardTypeNames.SNIPPET_ELEMENT_NAME)) {
            result = new SnippetConfiguration();
        }

        if (_id.equals(StandardTypeNames.SIMPLER_ELEMENT_NAME)) {
            result = new SimplerConfiguration();
        }

        return result;
    }

    public ElementHandler getHandler(final String _id) {
        Assert.isValidString(_id, "Id not valid.");

        ElementHandler result = null;

        if (_id.equals(StandardTypeNames.PROCESSOR_ELEMENT_NAME)) {
            result = new ChainProcessorElementHandler(this.operatorFactory);
        }

        if (_id.equals(StandardTypeNames.SOURCE_ELEMENT_NAME)) {
            result = new SourceElementHandler();
        }

        if (_id.equals(StandardTypeNames.INTERPRETER_ELEMENT_NAME)) {
            result = new InterpreterElementHandler();
        }

        if (_id.equals(StandardTypeNames.CHANNEL_ELEMENT_NAME)) {
            result = new ChannelElementHandler();
        }

        if (_id.equals(StandardTypeNames.OUTPUT_ELEMENT_NAME)) {
            result = new OutputElementHandler();
        }

        if (_id.equals(StandardTypeNames.STORAGE_ELEMENT_NAME)) {
            result = new LocalStorageElementHandler();
        }

        if (_id.equals(StandardTypeNames.DATEPARSER_ELEMENT_NAME)) {
            result = new DateParserElementHandler();
        }

        if (_id.equals(StandardTypeNames.SNIPPET_ELEMENT_NAME)) {
            result = new SnippetElementHandler();
        }

        if (_id.equals(StandardTypeNames.SIMPLER_ELEMENT_NAME)) {
            result = new SimplerElementHandler();
        }

        return result;
    }
}
