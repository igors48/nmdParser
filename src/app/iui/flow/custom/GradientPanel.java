package app.iui.flow.custom;

import net.miginfocom.swing.MigLayout;
import util.Assert;

import javax.swing.*;
import java.awt.*;

/**
 * @author Igor Usenko
 *         Date: 22.11.2010
 */
public class GradientPanel extends JPanel {

    private final Color up;
    private final Color down;

    public GradientPanel(final Color _up, final Color _down) {
        super(new MigLayout());

        Assert.notNull(_up, "Up color is null");
        this.up = _up;

        Assert.notNull(_down, "Down color is null");
        this.down = _down;
    }

    protected void paintComponent(final Graphics _graphics) {
        {
            if (!isOpaque()) {
                super.paintComponent(_graphics);
                return;
            }

            Graphics2D graphics2D = (Graphics2D) _graphics;

            int width = getWidth();
            int height = getHeight();

            GradientPaint gradientPaint = new GradientPaint(0, 0, this.up, 0, height, this.down);

            graphics2D.setPaint(gradientPaint);
            graphics2D.fillRect(0, 0, width, height);

            setOpaque(false);
            super.paintComponent(_graphics);
            setOpaque(true);
        }
    }
}
