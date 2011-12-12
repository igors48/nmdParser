package work.unit.metadata;

import app.iui.entity.Entity;
import app.metadata.ObjectMetaData;
import app.metadata.ObjectMetaDataTools;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import junit.framework.TestCase;
import work.unit.dom.SampleObject01;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 30.05.2010
 */
public class ParseEntityFromOutputTest extends TestCase {

    public ParseEntityFromOutputTest(final String _s) {
        super(_s);
    }

    // ���� ������ �������� ��� - ������������ null

    public void testNotExists() {
        List<ObjectMetaData> datas = newArrayList();

        Entity result = ObjectMetaDataTools.getEntityFromOutput("not.exists", datas);

        assertNull(result);
    }

    // �� ������ ����� - ������������ null

    public void testNotChannelExists() {
        List<ObjectMetaData> datas = newArrayList();

        datas.add(new ObjectMetaData("output", ObjectType.OUTPUT, new SampleObject01()));

        Entity result = ObjectMetaDataTools.getEntityFromOutput("output", datas);

        assertNull(result);
    }

    // �� ������ �������� - ������������ null

    public void testNotSourceExists() {
        List<ObjectMetaData> datas = newArrayList();

        ObjectMetaData output = new ObjectMetaData("output", ObjectType.OUTPUT, new SampleObject01());
        output.addDependency(new UsedObject("channel", ObjectType.CHANNEL));
        datas.add(output);

        datas.add(new ObjectMetaData("channel", ObjectType.CHANNEL, new SampleObject01()));

        Entity result = ObjectMetaDataTools.getEntityFromOutput("output", datas);

        assertNull(result);
    }

    // ��� �������

    public void testAllOk() {
        List<ObjectMetaData> datas = newArrayList();

        ObjectMetaData output = new ObjectMetaData("output", ObjectType.OUTPUT, new SampleObject01());
        output.addDependency(new UsedObject("channel", ObjectType.CHANNEL));
        datas.add(output);

        ObjectMetaData channel = new ObjectMetaData("channel", ObjectType.CHANNEL, new SampleObject01());
        channel.addDependency(new UsedObject("source", ObjectType.SOURCE));
        datas.add(channel);

        ObjectMetaData source = new ObjectMetaData("source", ObjectType.SOURCE, new SampleObject01());
        datas.add(source);

        Entity result = ObjectMetaDataTools.getEntityFromOutput("output", datas);

        assertNotNull(result);
        assertEquals(output.getId(), result.getOutputId());
        assertEquals(channel.getId(), result.getChannelId());
        assertEquals(source.getId(), result.getSourceId());
    }

    // ������ ����������

    public void testCollectSources() {
        List<ObjectMetaData> datas = newArrayList();

        ObjectMetaData output = new ObjectMetaData("output", ObjectType.OUTPUT, new SampleObject01());
        output.addDependency(new UsedObject("channel", ObjectType.CHANNEL));
        output.setSourceFile("output.source");
        datas.add(output);

        ObjectMetaData channel = new ObjectMetaData("channel", ObjectType.CHANNEL, new SampleObject01());
        channel.addDependency(new UsedObject("source", ObjectType.SOURCE));
        channel.setSourceFile("channel.source");
        datas.add(channel);

        ObjectMetaData source = new ObjectMetaData("source", ObjectType.SOURCE, new SampleObject01());
        source.setSourceFile("source.source");
        datas.add(source);

        Entity result = ObjectMetaDataTools.getEntityFromOutput("output", datas);

        assertNotNull(result);

        assertEquals(3, result.getSourceFiles().size());

        assertTrue(result.getSourceFiles().contains("output.source"));
        assertTrue(result.getSourceFiles().contains("channel.source"));
        assertTrue(result.getSourceFiles().contains("source.source"));
    }
}
