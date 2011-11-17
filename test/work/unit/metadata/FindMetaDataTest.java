package work.unit.metadata;

import app.metadata.ObjectMetaData;
import app.metadata.ObjectMetaDataTools;
import constructor.dom.ObjectType;
import junit.framework.TestCase;
import work.unit.dom.SampleObject01;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 30.05.2010
 */
public class FindMetaDataTest extends TestCase {

    public FindMetaDataTest(final String _s) {
        super(_s);
    }

    // список пустой - ничего и не найдет

    public void testEmptyList() {
        List<ObjectMetaData> datas = new ArrayList<ObjectMetaData>();

        ObjectMetaData data = ObjectMetaDataTools.findMetaData("someone", ObjectType.OUTPUT, datas);

        assertNull(data);
    }

    // в списке нет нужного объекта - результат null

    public void testNotExists() {
        List<ObjectMetaData> datas = new ArrayList<ObjectMetaData>();
        datas.add(new ObjectMetaData("exists", ObjectType.OUTPUT, new SampleObject01()));

        ObjectMetaData data = ObjectMetaDataTools.findMetaData("not.exists", ObjectType.OUTPUT, datas);

        assertNull(data);
    }

    // в списке помимо прочих есть нужный объект - возвращается именно он

    public void testFound() {
        List<ObjectMetaData> datas = new ArrayList<ObjectMetaData>();
        datas.add(new ObjectMetaData("exists", ObjectType.OUTPUT, new SampleObject01()));
        datas.add(new ObjectMetaData("another.one", ObjectType.OUTPUT, new SampleObject01()));
        datas.add(new ObjectMetaData("another.two", ObjectType.OUTPUT, new SampleObject01()));

        ObjectMetaData data = ObjectMetaDataTools.findMetaData("exists", ObjectType.OUTPUT, datas);

        assertNotNull(data);
        assertEquals("exists", data.getId());
        assertEquals(ObjectType.OUTPUT, data.getType());
    }
}
