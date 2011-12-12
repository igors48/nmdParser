package app.metadata;

import app.iui.entity.Entity;
import app.templater.PlaceHolderInfo;
import constructor.dom.ObjectType;
import constructor.dom.UsedObject;
import util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static util.CollectionUtils.*;

/**
 * Утилиты для работы с метаданными
 *
 * @author Igor Usenko
 *         Date: 24.04.2010
 */
public final class ObjectMetaDataTools {
    private static final String OUTPUT_POSTFIX = ".output";

    public static Entity getEntityFromSimpler(final String _simplerId, final List<ObjectMetaData> _metaDatas) {
        Assert.isValidString(_simplerId, "Simpler id is not valid");
        Assert.notNull(_metaDatas, "Meta data list is null");

        Entity result = null;

        ObjectMetaData simpler = findMetaData(_simplerId, ObjectType.SIMPLER, _metaDatas);

        if (simpler != null) {
            result = new Entity(_simplerId, simpler, simpler.getSourceFile(), getPlaceHolders(Arrays.asList(simpler)));
        }

        return result;
    }

    public static Entity getEntityFromOutput(final String _outputId, final List<ObjectMetaData> _metaDatas) {
        Assert.isValidString(_outputId, "Output id is not valid");
        Assert.notNull(_metaDatas, "Meta data list is null");

        Entity result = null;

        List<ObjectMetaData> objects = newArrayList();

        ObjectMetaData output = findMetaData(_outputId, ObjectType.OUTPUT, _metaDatas);

        ObjectMetaData channel = null;
        ObjectMetaData source = null;
        ObjectMetaData interpreter = null;

        if (output != null) {
            objects.add(output);
            objects.addAll(getDepsMetaDatas(output, _metaDatas));

            channel = findChannel(output, _metaDatas);
        }

        if (channel != null) {
            objects.add(channel);
            objects.addAll(getDepsMetaDatas(channel, _metaDatas));

            source = findSource(channel, _metaDatas);
            interpreter = findInterpreter(channel, _metaDatas);
        }

        if (source != null) {
            objects.add(source);
            objects.addAll(getDepsMetaDatas(source, _metaDatas));
        }

        if (interpreter != null) {
            objects.add(interpreter);
            objects.addAll(getDepsMetaDatas(interpreter, _metaDatas));
        }

        if ((output != null) && (channel != null) && (source != null)) {
            result = new Entity(cutOutputPostfix(_outputId), source, channel, output, getSources(objects), getPlaceHolders(objects));
        }

        return result;
    }

    public static ObjectMetaData findMetaData(final String _id, final ObjectType _type, final List<ObjectMetaData> _metaDatas) {
        Assert.isValidString(_id, "Id is not valid");
        Assert.notNull(_type, "Object type is null");
        Assert.notNull(_metaDatas, "Meta data list is null");

        ObjectMetaData result = null;

        for (ObjectMetaData current : _metaDatas) {

            if (current.getId().equals(_id) && current.getType() == _type) {
                result = current;
            }
        }

        return result;
    }

    private static ObjectMetaData findChannel(final ObjectMetaData _output, final List<ObjectMetaData> _metaDatas) {
        Assert.notNull(_output, "Parent is null");
        Assert.notNull(_metaDatas, "Meta data list is null");
        Assert.isTrue(_output.getType() == ObjectType.OUTPUT, "Parent object is not of type OUTPUT");

        return getFirstCandidate(_metaDatas, findDependencies(ObjectType.CHANNEL, _output.getDependencies()));
    }

    private static ObjectMetaData findSource(final ObjectMetaData _channel, final List<ObjectMetaData> _metaDatas) {
        Assert.notNull(_channel, "Parent is null");
        Assert.notNull(_metaDatas, "Meta data list is null");
        Assert.isTrue(_channel.getType() == ObjectType.CHANNEL, "Parent object is not of type CHANNEL");

        return getFirstCandidate(_metaDatas, findDependencies(ObjectType.SOURCE, _channel.getDependencies()));
    }

    private static ObjectMetaData findInterpreter(final ObjectMetaData _channel, final List<ObjectMetaData> _metaDatas) {
        Assert.notNull(_channel, "Parent is null");
        Assert.notNull(_metaDatas, "Meta data list is null");
        Assert.isTrue(_channel.getType() == ObjectType.CHANNEL, "Parent object is not of type CHANNEL");

        return getFirstCandidate(_metaDatas, findDependencies(ObjectType.INTERPRETER, _channel.getDependencies()));
    }

    private static List<String> getSources(final List<ObjectMetaData> _metaDatas) {
        Set<String> result = newHashSet();

        for (ObjectMetaData current : _metaDatas) {
            result.add(current.getSourceFile());
        }

        return Arrays.asList(result.toArray(new String[result.size()]));
    }

    private static Map<String, String> getPlaceHolders(final List<ObjectMetaData> _metaDatas) {
        Map<String, String> result = newHashMap();

        for (ObjectMetaData current : _metaDatas) {
            List<PlaceHolderInfo> placeHolders = current.getPlaceholders();

            for (PlaceHolderInfo placeHolder : placeHolders) {
                result.put(placeHolder.getName(), placeHolder.getDefValue());
            }
        }

        return result;
    }

    private static List<ObjectMetaData> getDepsMetaDatas(final ObjectMetaData _object, final List<ObjectMetaData> _metaDatas) {
        List<ObjectMetaData> result = newArrayList();

        for (UsedObject current : _object.getDependencies()) {
            ObjectMetaData metaData = findUsedObjectMetaData(current, _metaDatas);

            if (metaData != null) {
                result.add(metaData);
            }
        }

        return result;
    }

    private static ObjectMetaData findUsedObjectMetaData(final UsedObject _object, final List<ObjectMetaData> _metaDatas) {
        return findMetaData(_object.getId(), _object.getType(), _metaDatas);
    }

    private static List<UsedObject> findDependencies(final ObjectType _type, final List<UsedObject> _dependencies) {
        Assert.notNull(_type, "Object type is null");
        Assert.notNull(_dependencies, "Meta data list is null");

        List<UsedObject> result = newArrayList();

        for (UsedObject current : _dependencies) {

            if (current.getType() == _type) {
                result.add(current);
            }
        }

        return result;
    }

    private static ObjectMetaData getFirstCandidate(final List<ObjectMetaData> _metaDatas, final List<UsedObject> _channels) {
        ObjectMetaData result = null;

        if (!_channels.isEmpty()) {
            UsedObject candidate = _channels.get(0);

            result = findMetaData(candidate.getId(), candidate.getType(), _metaDatas);
        }

        return result;
    }

    private static String cutOutputPostfix(final String _outputId) {
        String result = _outputId;

        if (_outputId.endsWith(OUTPUT_POSTFIX)) {
            int index = _outputId.lastIndexOf(OUTPUT_POSTFIX);
            result = _outputId.substring(0, index);
        }

        return result;
    }

    private ObjectMetaDataTools() {
        // empty
    }
}
