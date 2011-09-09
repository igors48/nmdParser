package app.iui.flow;

import app.iui.StringResource;
import app.iui.flow.custom.GradientPanel;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import javax.swing.*;
import java.awt.*;

/**
 * @author Igor Usenko
 *         Date: 27.11.2010
 */
public abstract class AbstractForm extends JPanel {

    private static final int HEADER_HEIGHT = 50;
    private static final int FOOTER_HEIGHT = 60;

    protected final StringResource stringResource;

    private final Header header;
    private final JPanel content;
    private final Footer footer;
    protected final JFrame owner;

    protected final Log log;

    public AbstractForm(final JFrame _owner, final int _width, final int _height, final StringResource _stringResource) {
        super(new MigLayout("gap 2! 2!, insets 2 2 2 2", "", ""));

        this.log = LogFactory.getLog(getClass());

        Assert.notNull(_owner, "Owner is null");
        this.owner = _owner;

        Assert.greater(_width, 0, "Template width <= 0");
        Assert.greater(_height, 0, "Template height <= 0");
        setSize(_width, _height);

        Assert.notNull(_stringResource, "String resource is null");
        this.stringResource = _stringResource;

        add(this.header = createHeader(), "wrap, w " + getWidth() + "!, h " + HEADER_HEIGHT + "!");
        add(this.content = createContentPanel(), "wrap, w " + getWidth() + "!, h " + (getHeight() - FOOTER_HEIGHT - HEADER_HEIGHT) + "!");
        add(this.footer = createFooter(), "wrap, w " + getWidth() + "!, h " + FOOTER_HEIGHT + "!");
    }

    public void onEnterKey() {
        onApprove();
    }

    public void onEscapeKey() {
        onDiscard();
    }

    protected void onApprove() {

    }

    protected void onDiscard() {

    }

    protected Constraint createConstraint() {
        return new Constraint(getWidth());
    }

    protected void setDefaultButton(final JButton _button) {
        Assert.notNull(_button, "Button is null");

        this.owner.getRootPane().setDefaultButton(_button);
    }

    protected String getString(final String _resourceId) {
        Assert.isValidString(_resourceId, "Resource id is not valid");
        return this.stringResource.getString(_resourceId);
    }

    protected JPanel getContentPanel() {
        return this.content;
    }

    protected void setCaption(final String _caption) {
        Assert.isValidString(_caption, "Caption is not valid");
        this.header.setCaption(_caption);
    }

    protected void addFooterButton(final JButton _button, final String _constraints) {
        Assert.notNull(_button, "Button is null");
        Assert.isValidString(_constraints, "Constraints is not valid");

        this.footer.addButton(_button, _constraints);
    }

    protected void refreshContentPanel() {
        getContentPanel().validate();

        owner.repaint();
    }

    private JPanel createContentPanel() {
        return new GradientPanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY.brighter());
    }

    private Header createHeader() {
        return new Header();
    }

    private Footer createFooter() {
        return new Footer();
    }

    private class Header extends JPanel {

        private final JLabel label;

        public Header() {
            super(new MigLayout());

            this.label = new JLabel();
            this.label.setForeground(Color.WHITE);

            add(this.label);

            setBackground(Color.DARK_GRAY);

        }

        public void setCaption(final String _caption) {
            Assert.isValidString(_caption, "Caption is not valid");
            this.label.setText(_caption);
        }
    }

    private class Footer extends JPanel {

        public Footer() {
            super(new MigLayout());

            setBackground(Color.LIGHT_GRAY);
        }

        public void addButton(final JButton _button, final String _constraints) {
            Assert.notNull(_button, "Button is null");
            Assert.isValidString(_constraints, "Constraints is not valid");

            add(_button, _constraints);
        }
    }
}
