package work.unit.constructor.processor;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import junit.framework.TestCase;
import variables.Variables;

import java.util.Map;

import static util.CollectionUtils.newHashMap;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

/**
 * @author Igor Usenko
 *         Date: 27.06.2010
 */
public class FirstOneProcessorTest extends TestCase {

    public FirstOneProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws Constructor.ConstructorException, ConfigurationException, VariableProcessor.VariableProcessorException {
        Map<String, String> streams = newHashMap();
        streams.put("processor", "<processor><firstOne out=\"sample\"><set value=\"test\" out=\"testvar\"/><set value=\"test2\" out=\"testvar2\"/></firstOne></processor>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        StandardChainProcessorAdapter chainProcessorAdapter = (StandardChainProcessorAdapter) constructor.create("processor", ObjectType.PROCESSOR);

        ChainProcessor processor = new ChainProcessor(chainProcessorAdapter);

        Variables variables = new Variables();

        processor.process(variables);

        assertEquals("test", variables.get("testvar"));
        assertEquals("test2", variables.get("testvar2"));
        assertEquals(null, variables.get("sample"));
    }

    // тест - выполнение завершается на первом операторе изменившем выходную переменную 

    public void testBreakExecution() throws Constructor.ConstructorException, ConfigurationException, VariableProcessor.VariableProcessorException {
        Map<String, String> streams = newHashMap();
        streams.put("processor", "<processor><firstOne out=\"sample\"><set value=\"test\" out=\"testvar\"/><set value=\"test2\" out=\"sample\"/><set value=\"test3\" out=\"testvar3\"/></firstOne></processor>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        StandardChainProcessorAdapter chainProcessorAdapter = (StandardChainProcessorAdapter) constructor.create("processor", ObjectType.PROCESSOR);

        ChainProcessor processor = new ChainProcessor(chainProcessorAdapter);

        Variables variables = new Variables();

        processor.process(variables);

        assertEquals("test", variables.get("testvar"));
        assertEquals("test2", variables.get("sample"));
        assertEquals(null, variables.get("testvar3"));
    }
}
