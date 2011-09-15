package constructor.objects.interpreter.core.standard;

import constructor.objects.AdapterException;
import constructor.objects.interpreter.core.InterpreterAdapter;
import constructor.objects.interpreter.core.InterpreterEx;
import constructor.objects.interpreter.core.InterpreterException;
import constructor.objects.interpreter.core.data.InterpreterData;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import dated.item.atdc.Author;
import dated.item.atdc.HtmlContent;
import dated.item.modification.Modification;
import util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ������� ������������� - ������ ����� ���������� �� �����������
 *
 * @author Igor Usenko
 *         Date: 11.04.2009
 */
public class SimpleInterpreterEx implements InterpreterEx {

    private final InterpreterAdapter adapter;

    public SimpleInterpreterEx(InterpreterAdapter _adapter) {
        Assert.notNull(_adapter, "Interpreter adapter is null.");
        this.adapter = _adapter;
    }

    public InterpreterData process() throws InterpreterException {

        try {
            Modification modification = this.adapter.getModification();

            String nick = "";
            String info = "";
            String avatar = "";
            Author author = new Author(nick, info, avatar);

            String title = modification.getTitle();

            String content = modification.getDescription() == null || modification.getDescription().isEmpty() ? "no content" : modification.getDescription();

            HtmlContent htmlContent = new HtmlContent(modification.getUrl(), content, modification.getUrl());

            Date date = modification.getDate();

            AtdcItem item = new AtdcItem(author, title, date, htmlContent);

            List<DatedItem> items = new ArrayList<DatedItem>();
            items.add(item);

            return new InterpreterData(this.adapter.getId(), items);
        } catch (AdapterException e) {
            throw new InterpreterException(e);
        }
    }

}
