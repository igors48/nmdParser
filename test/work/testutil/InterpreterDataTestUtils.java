package work.testutil;

import constructor.objects.interpreter.core.data.InterpreterData;
import util.Assert;
import dated.item.atdc.AtdcItem;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public final class InterpreterDataTestUtils {
    
    public static boolean interpreterDataEquals(InterpreterData _first, InterpreterData _second){
        Assert.notNull(_first);
        Assert.notNull(_second);

        boolean result = true;

        if (!_first.getId().equals(_second.getId())){
            result = false;
        }

        if (_first.getItems().size() != _second.getItems().size()){
            result = false;
        }

        for (int index = 0; index < _first.getItems().size(); ++index){
            AtdcItem firstItem = (AtdcItem) _first.getItems().get(index);
            AtdcItem secondItem = (AtdcItem) _second.getItems().get(index);

            if (!AtdcTestUtils.atdcItemEquals(firstItem, secondItem)){
                result = false;
                break;
            }
        }

        return result;
    }

    private InterpreterDataTestUtils() {
        // empty
    }
}
