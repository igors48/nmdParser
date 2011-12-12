package constructor.objects.interpreter.core.standard;

import constructor.objects.interpreter.core.Page;
import constructor.objects.interpreter.core.PageListAnalyser;
import constructor.objects.interpreter.core.PageListItem;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.chain.ChainProcessor;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import variables.Variables;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * —тандартный анализатор списка страниц
 *
 * @author Igor Usenko
 *         Date: 06.06.2009
 */
public class StandardPageListAnalyserEx implements PageListAnalyser {

    private final ChainProcessorAdapter adapter;

    private final Log log;

    public StandardPageListAnalyserEx(final ChainProcessorAdapter _adapter) {
        Assert.notNull(_adapter, "Adapter is null.");
        this.adapter = _adapter;

        this.log = LogFactory.getLog(getClass());
    }

    public List<PageListItem> getPageList(final Page _page) {
        Assert.notNull(_page, "Page is null.");

        List<PageListItem> result = newArrayList();

        try {
            Variables variables = new Variables();
            variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, _page.getImage());
            variables.put(Variables.DEFAULT_URL_VARIABLE_NAME, _page.getUrl());

            ChainProcessor processor = new ChainProcessor(this.adapter);
            processor.process(variables);
            int size = variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME);

            if (size == 0) {
                PageListItem item = new PageListItem(_page.getModification(), _page.getUrl());
                result.add(item);
            } else {

                for (int index = 0; index < size; ++index) {
                    PageListItem item = new PageListItem(_page.getModification(), variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, index));
                    result.add(item);
                }
            }
        } catch (VariableProcessor.VariableProcessorException e) {
            this.log.error("Error while analyse page [ " + _page.getUrl() + "].", e);
        }

        return result;
    }
}
