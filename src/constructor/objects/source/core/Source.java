package constructor.objects.source.core;

import app.iui.flow.custom.SingleProcessInfo;
import constructor.objects.AdapterException;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;

/**
 * Класс обрабатывает внешний источник модификаций.
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class Source {

    private final SourceAdapter adapter;

    private final Log log;

    public Source(final SourceAdapter _adapter) {
        Assert.notNull(_adapter, "Source adapter is null.");
        this.adapter = _adapter;

        this.log = LogFactory.getLog(getClass());
    }

    public void process() throws SourceException {

        try {

            this.adapter.onStart();
            this.adapter.onProgress(new SingleProcessInfo("process.source"));

            // todo Ну вот оно как-то не здесь быть должно, а уровнем выше?...
            if (this.adapter.getUpdateStrategy().needsUpdate()) {
                ModificationList list = this.adapter.getList();
                List<Modification> modifications = this.adapter.getFetcher().getModifications();

                this.log.info("[ " + modifications.size() + " ] items fetched.");

                modifications = this.adapter.getProcessor().process(modifications);
                list = SourceUtils.mergeAndRemoveExpired(list, modifications, this.adapter.getStoreStrategy());

                this.adapter.storeList(list);
                this.adapter.setLastUpdateTimeToCurrent();

                this.adapter.onComplete();
            }
        } catch (ModificationFetcher.ModificationFetcherException e) { //recoverable
            this.adapter.onFault();
            throw new SourceException(e);
        } catch (ModificationProcessor.ModificationProcessorException e) { //fatal
            this.adapter.onFault();
            throw new SourceException(e);
        } catch (AdapterException e) {  //fatal
            this.adapter.onFault();
            throw new SourceException(e);
        }
    }

    public static class SourceException extends Exception {

        public SourceException() {
            super();
        }

        public SourceException(final String _s) {
            super(_s);
        }

        public SourceException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public SourceException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
