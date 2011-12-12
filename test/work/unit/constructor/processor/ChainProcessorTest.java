package work.unit.constructor.processor;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import debug.snapshot.TopLevelObjectSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.Map;

import static util.CollectionUtils.newHashMap;
import static work.testutil.ConstructorTestUtils.createConstructorFactory;

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
        Map<String, String> streams = newHashMap();
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
        Map<String, String> streams = newHashMap();
        streams.put("processor", "<processor></processor>");

        ConstructorFactory constructorFactory = createConstructorFactory(streams);
        Constructor constructor = constructorFactory.getConstructor();

        StandardChainProcessorAdapter chainProcessorAdapter = (StandardChainProcessorAdapter) constructor.create("processor", ObjectType.PROCESSOR);

        ChainProcessor processor = new ChainProcessor(chainProcessorAdapter);

        TopLevelObjectSnapshot snapshot = (TopLevelObjectSnapshot) processor.getSnapshot();

        assertEquals("processor", snapshot.getName());
    }

}
