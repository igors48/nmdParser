package constructor.objects.interpreter.core.standard;

import constructor.objects.interpreter.core.Fragment;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import util.Assert;
import variables.VariableIterator;
import variables.Variables;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Утилиты для стандартного анализатора фрагмента
 *
 * @author Igor Usenko
 *         Date: 24.10.2009
 */
public final class StandardFragmentAnalyserUtils {

    public static String callProcessorForString(final Fragment _fragment, final ChainProcessorAdapter _adapter) throws VariableProcessor.VariableProcessorException {
        Assert.notNull(_fragment, "Fragment is null");

        List<String> result = callProcessorForList(_fragment, _adapter);

        return result.isEmpty() ? "" : result.get(0);
    }

    public static List<String> callProcessorForList(final Fragment _fragment, final ChainProcessorAdapter _adapter) throws VariableProcessor.VariableProcessorException {
        Assert.notNull(_fragment, "Fragment is null");

        List<String> result = newArrayList();

        if (_adapter != null) {
            ChainProcessor processor = new ChainProcessor(_adapter);

            Variables variables = new Variables();

            variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, _fragment.getImage());
            variables.put(Variables.DEFAULT_URL_VARIABLE_NAME, _fragment.getUrl());

            processor.process(variables);

            VariableIterator iterator = variables.getIterator(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);

            while (iterator.hasNext()) {
                result.add((String) iterator.next());
            }
        }

        return result;
    }

    private StandardFragmentAnalyserUtils() {
        // empty
    }
}
