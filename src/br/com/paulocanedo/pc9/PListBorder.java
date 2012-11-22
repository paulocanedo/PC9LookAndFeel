/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.paulocanedo.pc9;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author paulocanedo
 */
public class PListBorder extends AbstractBorder {

    private int verticalRoundRect = 3;
    private int horizontalRoundRect = 4;
    private Insets insets = new Insets(verticalRoundRect, 1, verticalRoundRect, 1);

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(getColor());
        g2.drawLine(x, y + verticalRoundRect, x, y + height - verticalRoundRect);
        g2.drawLine(x + width - 1, y + verticalRoundRect, x + width - 1, y + height - verticalRoundRect);

        BufferedImage image = new BufferedImage(width, verticalRoundRect, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D imgGraphics = (Graphics2D) image.getGraphics();
        imgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GeneralPath path = new GeneralPath();
        path.moveTo(x, y + verticalRoundRect);
        path.curveTo(x, y + verticalRoundRect, x, y, x + horizontalRoundRect, y);
        path.lineTo(x + width - horizontalRoundRect, y);
        path.curveTo(x + width - horizontalRoundRect, y, x + width, y, x + width, y + verticalRoundRect);
        path.lineTo(x, y + verticalRoundRect);
        path.closePath();

        imgGraphics.setColor(getColor());
        imgGraphics.fill(path);

        g2.drawImage(image, x, y, null);
        g2.rotate(Math.toRadians(180));
        g2.drawImage(image, -width, -height, null);

        g2.dispose();
    }

    private Color getColor() {
        return UIManager.getColor("List.selectionBackground").darker().darker();
    }
}
