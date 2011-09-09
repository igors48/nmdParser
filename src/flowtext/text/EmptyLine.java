package flowtext.text;

import flowtext.FlowTextObject;
import flowtext.FlowTextType;

/**
 * @author Igor Usenko
 *         Date: 25.08.2008
 */
public class EmptyLine implements FlowTextObject {

    public FlowTextType getType() {
        return FlowTextType.EMPTY_LINE;
    }

}
