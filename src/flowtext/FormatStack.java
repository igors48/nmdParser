package flowtext;

import html.parser.tag.format.TextFormat;
import util.Assert;

import java.util.Stack;

/**
 * @author Igor Usenko
 *         Date: 19.09.2008
 */
public class FormatStack {

    private final Stack<TextFormat> stack;

    public FormatStack() {
        this.stack = new Stack<TextFormat>();
    }

    public void push(TextFormat _format) {
        Assert.notNull(_format);

        this.stack.push(_format);
    }

    public TextFormat pop() {
        TextFormat result = TextFormat.SIMPLE;

        if (!this.stack.empty()) {
            result = this.stack.pop();
        }

        return result;
    }
}
