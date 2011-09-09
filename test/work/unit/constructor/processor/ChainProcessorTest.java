package work.unit.constructor.processor;

import junit.framework.TestCase;
import constructor.dom.*;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.ConfigurationException;

import java.util.Map;
import java.util.HashMap;

import static work.testutil.ConstructorTestUtils.createConstructorFactory;
import variables.Variables;
import debug.snapshot.TopLevelObjectSnapshot;

/**
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class ChainProcessorTest extends TestCase {

    public ChainProcessorTest(String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() throws Constructor.ConstructorException, ConfigurationException, VariableProcessor.VariableProcessorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("processor", "<processor><getGroup in=\"inp\" out=\"sample\"><occurrence>0</occurrence><pattern>abc(.+?)def</pattern></getGroup><getGroup in=\"sample\" out=\"out\"><occurrence>0</occurrence><pattern>z(.+?)z</pattern></getGroup></processor>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        StandardChainProcessorAdapter chainProcessorAdapter = (StandardChainProcessorAdapter) constructor.create("processor", ObjectType.PROCESSOR);

        ChainProcessor processor = new ChainProcessor(chainProcessorAdapter);

        Variables variables = new Variables();
        variables.put("inp", "abczokzdef");

        processor.process(variables);

        assertEquals("ok", variables.get("out"));
    }

    // тест снапшота
    public void testSnapshot() throws Constructor.ConstructorException {
        Map<String, String> streams = new HashMap<String, String>();
        streams.put("processor", "<processor></processor>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        StandardChainProcessorAdapter chainProcessorAdapter = (StandardChainProcessorAdapter) constructor.create("processor", ObjectType.PROCESSOR);

        ChainProcessor processor = new ChainProcessor(chainProcessorAdapter);

        TopLevelObjectSnapshot snapshot = (TopLevelObjectSnapshot) processor.getSnapshot();

        assertEquals("processor", snapshot.getName());
    }

}
