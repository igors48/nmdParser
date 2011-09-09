package constructor.objects.channel.core;

import constructor.objects.interpreter.core.data.InterpreterData;
import util.Assert;

import java.util.List;

/**
 * Контейнерный класс хранящий данные канала : заголовок и
 * данные полученные от интерпретаторов. Все это является результатом
 * обработки модификации
 *
 * @author Igor Usenko
 *         Date: 21.12.2008
 */
public class ChannelData {

    private final ChannelDataHeader header;
    private final List<InterpreterData> data;

    public ChannelData(final ChannelDataHeader _header, final List<InterpreterData> _data) {
        Assert.notNull(_header, "Header is null");
        this.header = _header;

        Assert.notNull(_data, "Interpreter data is null");
        this.data = _data;
    }

    public ChannelDataHeader getHeader() {
        return this.header;
    }

    public List<InterpreterData> getData() {
        return this.data;
    }

    public String getFirstName() {
        return this.header.getFirstName();
    }

    public String getLastName() {
        return this.header.getLastName();
    }

    public String getTitle() {
        return this.header.getTitle();
    }

    public String getSourceUrl() {
        return this.header.getSourceUrl();
    }

    public boolean isEmpty() {
        boolean result = true;

        if (!this.data.isEmpty()) {

            for (InterpreterData current : this.data) {

                if (!current.isEmpty()) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
