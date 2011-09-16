package constructor.objects.processor.load;

import constructor.objects.processor.AbstractVariableProcessor;
import debug.Snapshot;
import debug.snapshot.ProcessorSnapshot;
import http.BatchLoader;
import http.Data;
import http.Result;
import http.data.DataUtil;
import html.HttpData;
import util.Assert;
import variables.Variables;

/**
 * ��������� html ��� �������� � ����������
 *
 * @author Igor Usenko
 *         Date: 04.08.2009
 */
public class LoadProcessor extends AbstractVariableProcessor {

    public static final String LOAD_PROCESSOR_NAME = "load";
    public static final String URL_PARAMETER_NAME = "url";
    public static final String REFERER_PARAMETER_NAME = "referer";

    private final String url;
    private final String referer;
    private final BatchLoader loader;

    public LoadProcessor(final String _in, final String _url, final String _referer, final String _out, final BatchLoader _loader) {
        super(_in, _out);

        Assert.notNull(_url, "Url is not valid");
        this.url = _url;

        Assert.notNull(_referer, "Referer is not valid");
        this.referer = _referer;

        Assert.notNull(_loader, "Loader is null");
        this.loader = _loader;
    }

    public void process(Variables _variables) throws VariableProcessorException {
        Assert.notNull(_variables, "Variables is null.");

        String inputValue = this.url;

        if (inputValue.isEmpty()) {
            inputValue = _variables.get(this.input);

            if (inputValue == null) {
                throw new VariableProcessorException("Can`t find variable [ " + this.input + " ].");
            }
        }

        String referer = "";

        if (!this.referer.isEmpty()) {
            referer = _variables.get(this.referer);
            referer = referer == null ? "" : referer;
        }

        /*
        List<String> pages = new ArrayList<String>();
        pages.add(inputValue);
        */

        //����� ����� ��������� ����� ���� �� ������
        /*
        Map<String, HttpData> map = loader.loadUrls(pages, 0);
        HttpData data = map.get(inputValue);
        */
        HttpData data = loader.loadUrlWithReferer(inputValue, referer);

        if (data.getResult() == Result.OK) {
            _variables.put(this.output, getString(data.getData()));
        } else {
            _variables.put(this.output, "");
        }
    }

    public Snapshot getSnapshot() {
        ProcessorSnapshot result = new ProcessorSnapshot(LOAD_PROCESSOR_NAME);

        result.addParameter(Variables.DEFAULT_INPUT_VARIABLE_NAME, this.input);
        result.addParameter(URL_PARAMETER_NAME, this.url);
        result.addParameter(REFERER_PARAMETER_NAME, this.referer);
        result.addParameter(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, this.output);

        return result;
    }

    private String getString(final Data _data) {
        return _data == null ? "" : DataUtil.getDataImage(_data);
    }
}
