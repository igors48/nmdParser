package constructor.objects.interpreter.core.standard;

import constructor.objects.interpreter.core.Fragment;
import constructor.objects.interpreter.core.Page;
import constructor.objects.interpreter.core.PageAnalyser;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import variables.Variables;

import java.util.ArrayList;
import java.util.List;

/**
 * Стандартный анализатор страницы. Разбивает ее на фрагменты.
 *
 * @author Igor Usenko
 *         Date: 03.06.2009
 */
public class StandardPageAnalyserEx implements PageAnalyser {

    private final ChainProcessorAdapter adapter;

    private final Log log;

    public StandardPageAnalyserEx(final ChainProcessorAdapter _adapter) {
        Assert.notNull(_adapter, "Adapter is null.");
        this.adapter = _adapter;

        this.log = LogFactory.getLog(getClass());
    }

    public List<Fragment> getFragments(final Page _page) {
        Assert.notNull(_page, "Page is null.");

        List<Fragment> result = new ArrayList<Fragment>();

        try {
            Variables variables = new Variables();

            variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, _page.getImage());
            variables.put(Variables.DEFAULT_URL_VARIABLE_NAME, _page.getUrl());

            ChainProcessor processor = new ChainProcessor(this.adapter);
            processor.process(variables);

            int size = variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);

            if (size != 0) {

                for (int index = 0; index < size; ++index) {
                    Fragment fragment = new Fragment(_page.getModification(), _page.getUrl(), variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, index), _page.getBase());
                    result.add(fragment);
                }
            }
        } catch (VariableProcessor.VariableProcessorException e) {
            this.log.error("Error while analyse page [ " + _page.getUrl() + "].", e);
        }

        return result;
    }
}
